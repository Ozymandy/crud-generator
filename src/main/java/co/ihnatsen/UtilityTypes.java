package co.ihnatsen;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

import java.util.EnumMap;

public enum UtilityTypes {
    LIST("java.util.List"),
    PAGE("org.springframework.data.domain.Page"),
    PAGEABLE("org.springframework.data.domain.Pageable"),
    JPA_REPOSITORY("org.springframework.data.jpa.repository.JpaRepository"),
    PAGING_REPOSITORY("org.springframework.data.jpa.repository.PagingAndSortingRepository"),
    AUTOWIRED_ANNOTATION("org.springframework.beans.factory.annotation.Autowired"),
    SERVICE_ANNOTATION("org.springframework.stereotype.Service"),
    REST_CONTROLLER_ANNOTATION("org.springframework.web.bind.annotation.RestController"),
    DATA_ANNOTATION("lombok.Data"),
    JSON_PROPERTY_ANNOTATION("com.fasterxml.jackson.annotation.JsonProperty"),
    REQUEST_PARAM_ANNOTATION("org.springframework.web.bind.annotation.RequestParam"),
    ALL_ARGS_CONSTRUCTOR_ANNOTATION("lombok.AllArgsConstructor"),
    GET_MAPPING("org.springframework.web.bind.annotation.GetMapping"),
    POST_MAPPING("org.springframework.web.bind.annotation.PostMapping"),
    DELETE_MAPPING("org.springframework.web.bind.annotation.DeleteMapping"),
    PUT_MAPPING("org.springframework.web.bind.annotation.PutMapping"),
    REQUEST_BODY("org.springframework.web.bind.annotation.RequestBody");

    UtilityTypes(String typePackage) {
        this.typePackage = typePackage;
    }

    private String typePackage;


    private static final EnumMap<UtilityTypes, JClass> utilityTypes = new EnumMap<UtilityTypes, JClass>(UtilityTypes.class);

    public static JClass getUtilityType(JCodeModel cm, UtilityTypes type) {
        return utilityTypes.computeIfAbsent(type, type1 -> cm.ref(type1.typePackage));
    }
}
