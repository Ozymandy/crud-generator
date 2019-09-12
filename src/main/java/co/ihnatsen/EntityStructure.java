package co.ihnatsen;

import com.sun.codemodel.JDefinedClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class EntityStructure {

    private String entityName;
    private Class<?> idClass;
    private Class<?> entityClass;
    private Map<String, Class<?>> entityFields = new HashMap<>();
    private JDefinedClass repository;
    private JDefinedClass service;
    private JDefinedClass controller;
    private JDefinedClass dto;

    public JDefinedClass getRepository() {
        return repository;
    }

    public void setRepository(JDefinedClass repository) {
        this.repository = repository;
    }

    public JDefinedClass getService() {
        return service;
    }

    public void setService(JDefinedClass service) {
        this.service = service;
    }

    public JDefinedClass getController() {
        return controller;
    }

    public void setController(JDefinedClass controller) {
        this.controller = controller;
    }

    public JDefinedClass getDto() {
        return dto;
    }

    public void setDto(JDefinedClass dto) {
        this.dto = dto;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Class<?> getIdClass() {
        return idClass;
    }

    public void setIdClass(Class<?> idClass) {
        this.idClass = idClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Map<String, Class<?>> getEntityFields() {
        return entityFields;
    }

    public void setEntityFields(Map<String, Class<?>> entityFields) {
        this.entityFields = entityFields;
    }

    public void addField(String name, Class<?> type) {
        entityFields.put(name, type);
    }

 /*   public enum Operations {
        CREATE("create%s", "save"),
        READ("get%s", "findById"),
        READ_ALL("getAll", "findAll"),
        UPDATE("update%s", "save"),
        DELETE("delete%s", "deleteById");

        String methodPattern;
        String repositoryMethod;

        Operations(String methodPattern, String repositoryMethod) {
            this.methodPattern = methodPattern;
            this.repositoryMethod = repositoryMethod;
        }

        public String getMethod() {
            return methodPattern;
        }

        public String getMethodPattern() {
            return methodPattern;
        }

        static List<Operations> all() {
            return asList(CREATE, READ, READ_ALL, UPDATE, DELETE);
        }
    }*/

}
