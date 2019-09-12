package co.ihnatsen;

import co.ihnatsen.utils.ClassUtils;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JVar;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static co.ihnatsen.Operations.CREATE;
import static co.ihnatsen.Operations.DELETE;
import static co.ihnatsen.Operations.READ;
import static co.ihnatsen.Operations.READ_ALL;
import static co.ihnatsen.Operations.UPDATE;
import static co.ihnatsen.UtilityTypes.ALL_ARGS_CONSTRUCTOR_ANNOTATION;
import static co.ihnatsen.UtilityTypes.AUTOWIRED_ANNOTATION;
import static co.ihnatsen.UtilityTypes.DATA_ANNOTATION;
import static co.ihnatsen.UtilityTypes.DELETE_MAPPING;
import static co.ihnatsen.UtilityTypes.GET_MAPPING;
import static co.ihnatsen.UtilityTypes.JPA_REPOSITORY;
import static co.ihnatsen.UtilityTypes.JSON_PROPERTY_ANNOTATION;
import static co.ihnatsen.UtilityTypes.LIST;
import static co.ihnatsen.UtilityTypes.PAGE;
import static co.ihnatsen.UtilityTypes.PAGEABLE;
import static co.ihnatsen.UtilityTypes.PAGING_REPOSITORY;
import static co.ihnatsen.UtilityTypes.POST_MAPPING;
import static co.ihnatsen.UtilityTypes.PUT_MAPPING;
import static co.ihnatsen.UtilityTypes.REQUEST_BODY;
import static co.ihnatsen.UtilityTypes.REQUEST_PARAM_ANNOTATION;
import static co.ihnatsen.UtilityTypes.REST_CONTROLLER_ANNOTATION;
import static co.ihnatsen.UtilityTypes.SERVICE_ANNOTATION;
import static co.ihnatsen.UtilityTypes.getUtilityType;
import static co.ihnatsen.utils.StringUtils.fieldName;
import static co.ihnatsen.utils.StringUtils.fieldToJsonName;
import static co.ihnatsen.utils.StringUtils.generateURI;
import static com.sun.codemodel.JExpr.invoke;
import static java.lang.String.format;

public class Main {

    private static final String REPOSITORY = "%sRepository";
    private static final String SERVICE = "%sService";
    private static final String DTO = "%sDto";
    private static final String CONTROLLER = "%sController";

    public static void main(String[] args) throws Exception {
        JCodeModel cm = new JCodeModel();
        boolean paging = true;
        List<EntityStructure> entityStructureList = ClassUtils.getEntityMetaData().stream()
                .map(entity -> generateRepositories(cm, entity, paging))
                .peek(entity -> generateServices(cm, entity, paging))
                .peek(entity -> entity.setDto(generateDto(cm, entity)))
                .peek(entity -> generateControllers(cm, entity, paging))
                .collect(Collectors.toList());
        File file = new File("./target/classes");
        file.mkdirs();
        cm.build(file);
    }

    private static EntityStructure generateRepositories(JCodeModel cm,
                                                        EntityStructure entity,
                                                        boolean paging) {
        JPackage jp = cm._package("repositories");
        try {
            JDefinedClass defClass = jp._class(0, format(REPOSITORY,
                    entity.getEntityName()));
            JClass repositoryClass = getUtilityType(cm, paging ? PAGING_REPOSITORY : JPA_REPOSITORY)
                    .narrow(entity.getEntityClass(), entity.getIdClass());
            defClass._implements(repositoryClass);
            entity.setRepository(defClass);
        } catch (JClassAlreadyExistsException e) {
            System.out.println("Class exists");
            entity.setRepository(cm._getClass(entity.getEntityName()));
        }
        return entity;
    }

    private static void generateServices(JCodeModel cm, EntityStructure entity, boolean paging) {
        //TODO: choose packages
        JPackage jp = cm._package("services");
        try {
            JDefinedClass defClass = jp._class(JMod.PUBLIC, format(SERVICE,
                    entity.getEntityName()));
            defClass.annotate(getUtilityType(cm, SERVICE_ANNOTATION));
            JFieldVar repository = defClass.field(JMod.PRIVATE, entity.getRepository(),
                    fieldName(entity.getRepository().name()));
            repository.annotate(getUtilityType(cm, AUTOWIRED_ANNOTATION));

            JMethod getAll = defClass.method(JMod.PUBLIC, getUtilityType(cm,
                    paging ? PAGE : LIST).narrow(entity.getEntityClass()), READ_ALL.getMethod());
            JExpression invokeGetAllRepository =
                    invoke(repository, "findAll").arg(getAll.param(getUtilityType(cm,
                            PAGEABLE), "pageable"));
            getAll.body()._return(invokeGetAllRepository);

            JMethod get = defClass.method(JMod.PUBLIC, entity.getEntityClass(),
                    format(READ.getMethod(), entity.getEntityName()));
            JExpression invokeFindById =
                    invoke(repository, "findById").arg(get.param(entity.getIdClass(), "id"));
            get.body()._return(invokeFindById);

            JMethod create = defClass.method(JMod.PUBLIC, void.class,
                    format(CREATE.getMethod(), entity.getEntityName()));
            create.param(entity.getEntityClass(), entity.getEntityName());
            create.body().invoke(repository, "save").arg(create.param(entity.getEntityClass(),
                    fieldName(entity.getEntityName())));

            JMethod update = defClass.method(JMod.PUBLIC, void.class,
                    format(UPDATE.getMethod(), entity.getEntityName()));
            update.body().invoke(repository, "save").arg(update.param(entity.getEntityClass(),
                    entity.getEntityName()));
            JMethod delete = defClass.method(JMod.PUBLIC, void.class,
                    format(DELETE.getMethod(), entity.getEntityName()));
            delete.body().invoke(repository, "deleteById").arg(delete.param(entity.getIdClass(),
                    entity.getEntityName()));
            entity.setService(defClass);
        } catch (JClassAlreadyExistsException e) {
            System.out.println("Class exists");
            entity.setService(cm._getClass(entity.getEntityName()));
        }

    }

    private static void generateControllers(JCodeModel cm, EntityStructure entity,
                                            boolean paging) {
        JPackage jp = cm._package("controllers");
        try {
            JDefinedClass defClass = jp._class(JMod.PUBLIC, format(CONTROLLER, entity.getEntityName()));
            defClass.annotate(getUtilityType(cm, REST_CONTROLLER_ANNOTATION)).param("value",
                    generateURI(entity.getEntityName()));

            JMethod getAll = defClass.method(JMod.PUBLIC, getUtilityType(cm, paging ? PAGE : LIST).narrow(entity.getEntityClass()),
                    READ_ALL.getMethod());
            getAll.annotate(getUtilityType(cm, GET_MAPPING));
            JFieldVar service = defClass.field(JMod.PRIVATE, entity.getService(),
                    fieldName(entity.getService().name()));
            service.annotate(getUtilityType(cm, AUTOWIRED_ANNOTATION));
            JExpression invokeGetAllService = invoke(service, "getAll").arg(getAll.param(getUtilityType(cm,
                    PAGEABLE), "pageable"));
            getAll.body()._return(invokeGetAllService);

            JMethod get = defClass.method(JMod.PUBLIC, entity.getEntityClass(), format(READ.getMethod(),
                    entity.getEntityName()));
            get.annotate(getUtilityType(cm, GET_MAPPING));
            JVar id = get.param(entity.getIdClass(), entity.getEntityName());
            id.annotate(getUtilityType(cm, REQUEST_PARAM_ANNOTATION)).param("value", "id");
            get.body().decl(cm._ref(entity.getEntityClass()), fieldName(entity.getEntityName()))
                    .init(invoke(service, "getTestEntity").arg(id));
            get.body()._return(JExpr._new(entity.getDto()));
            JExpression entityInstance = JExpr._new(cm.ref(entity.getEntityName()));

            JMethod create = defClass.method(JMod.PUBLIC, void.class, format(CREATE.getMethod(),
                    entity.getEntityName()));
            create.annotate(getUtilityType(cm, POST_MAPPING));
            JVar createBody = create.param(entity.getDto(), entity.getEntityName());
            createBody.annotate(getUtilityType(cm, REQUEST_BODY));
            create.body().invoke(service, "createTestEntity").arg(entityInstance);

            JMethod update = defClass.method(JMod.PUBLIC, void.class, format(UPDATE.getMethod(),
                    entity.getEntityName()));
            update.annotate(getUtilityType(cm, PUT_MAPPING));
            JVar updateBody = update.param(entity.getDto(), entity.getEntityName());
            updateBody.annotate(getUtilityType(cm, REQUEST_BODY));
            update.body().invoke(service, "updateTestEntity").arg(entityInstance);

            JMethod delete = defClass.method(JMod.PUBLIC, void.class, format(DELETE.getMethod(),
                    entity.getEntityName()));
            delete.annotate(getUtilityType(cm, DELETE_MAPPING));
            JVar deleteId = delete.param(entity.getIdClass(), entity.getEntityName());
            deleteId.annotate(getUtilityType(cm, REQUEST_PARAM_ANNOTATION)).param("value", "id");
            JExpression invokeDelete = invoke(service, "deleteTestEntity").arg(deleteId);
            delete.body()._return(invokeDelete);
        } catch (JClassAlreadyExistsException e) {
            System.out.println("Class exists");
        }
    }

    private static JDefinedClass generateDto(JCodeModel cm, EntityStructure entity) {
        JPackage jp = cm._package("dto");
        try {
            JDefinedClass defClass = jp._class(JMod.PUBLIC, format(DTO, entity.getEntityName()));
            defClass.annotate(getUtilityType(cm, DATA_ANNOTATION));
            defClass.annotate(getUtilityType(cm, ALL_ARGS_CONSTRUCTOR_ANNOTATION));
            entity.getEntityFields().entrySet().forEach(field -> {
                defClass
                        .field(
                                JMod.PRIVATE,
                                serializeType(field.getValue()),
                                fieldName(field.getKey()))
                        .annotate(getUtilityType(cm, JSON_PROPERTY_ANNOTATION))
                        .param("value", fieldToJsonName(field.getKey()));
            });
            return defClass;
        } catch (JClassAlreadyExistsException e) {
            System.out.println("Class exists");
            return cm._getClass(format(DTO, entity.getEntityName()));
        }
    }

    private static Class<?> serializeType(Class<?> clazz) {
        return Serializable.class.isAssignableFrom(clazz) ? clazz : String.class;
    }
}
