package co.ihnatsen.classes;

public enum UtilityTypes {
    LIST(new RefStructure("java.util.List")),
    PAGE(new RefStructure("org.springframework.data.domain.Page")),
    PAGEABLE(new RefStructure("org.springframework.data.domain.Pageable")),
    JPA_REPOSITORY(new RefStructure("org.springframework.data.jpa.repository.JpaRepository")),
    PAGING_REPOSITORY(new RefStructure("org.springframework.data.jpa.repository.PagingAndSortingRepository")),
    AUTOWIRED_ANNOTATION(new RefStructure("org.springframework.beans.factory.annotation.Autowired")),
    SERVICE_ANNOTATION(new RefStructure("org.springframework.stereotype.Service")),
    REST_CONTROLLER_ANNOTATION(new RefStructure("org.springframework.web.bind.annotation.RestController")),
    DATA_ANNOTATION(new RefStructure("lombok.Data")),
    JSON_PROPERTY_ANNOTATION(new RefStructure("com.fasterxml.jackson.annotation.JsonProperty")),
    REQUEST_PARAM_ANNOTATION(new RefStructure("org.springframework.web.bind.annotation.RequestParam")),
    ALL_ARGS_CONSTRUCTOR_ANNOTATION(new RefStructure("lombok.AllArgsConstructor")),
    GET_MAPPING(new RefStructure("org.springframework.web.bind.annotation.GetMapping")),
    POST_MAPPING(new RefStructure("org.springframework.web.bind.annotation.PostMapping")),
    DELETE_MAPPING(new RefStructure("org.springframework.web.bind.annotation.DeleteMapping")),
    PUT_MAPPING(new RefStructure("org.springframework.web.bind.annotation.PutMapping")),
    REQUEST_BODY(new RefStructure("org.springframework.web.bind.annotation.RequestBody"));

    private RefStructure refStructure;

    UtilityTypes(RefStructure refStructure) {
        this.refStructure = refStructure;
    }
}
