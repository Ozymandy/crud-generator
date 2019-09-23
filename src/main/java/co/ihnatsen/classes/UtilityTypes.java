package co.ihnatsen.classes;

public enum UtilityTypes {
    LIST(new RefClass("java.util.List")),
    PAGE(new RefClass("org.springframework.data.domain.Page")),
    PAGEABLE(new RefClass("org.springframework.data.domain.Pageable")),
    JPA_REPOSITORY(new RefClass("org.springframework.data.jpa.repository.JpaRepository")),
    PAGING_REPOSITORY(new RefClass("org.springframework.data.jpa.repository.PagingAndSortingRepository")),
    AUTOWIRED_ANNOTATION(new RefClass("org.springframework.beans.factory.annotation.Autowired")),
    SERVICE_ANNOTATION(new RefClass("org.springframework.stereotype.Service")),
    REST_CONTROLLER_ANNOTATION(new RefClass("org.springframework.web.bind.annotation.RestController")),
    DATA_ANNOTATION(new RefClass("lombok.Data")),
    JSON_PROPERTY_ANNOTATION(new RefClass("com.fasterxml.jackson.annotation.JsonProperty")),
    REQUEST_PARAM_ANNOTATION(new RefClass("org.springframework.web.bind.annotation.RequestParam")),
    ALL_ARGS_CONSTRUCTOR_ANNOTATION(new RefClass("lombok.AllArgsConstructor")),
    GET_MAPPING(new RefClass("org.springframework.web.bind.annotation.GetMapping")),
    POST_MAPPING(new RefClass("org.springframework.web.bind.annotation.PostMapping")),
    DELETE_MAPPING(new RefClass("org.springframework.web.bind.annotation.DeleteMapping")),
    PUT_MAPPING(new RefClass("org.springframework.web.bind.annotation.PutMapping")),
    REQUEST_BODY(new RefClass("org.springframework.web.bind.annotation.RequestBody"));

    private RefClass refClass;

    UtilityTypes(RefClass refClass) {
        this.refClass = refClass;
    }
}
