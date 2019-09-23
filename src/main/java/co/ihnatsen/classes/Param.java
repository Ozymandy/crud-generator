package co.ihnatsen.classes;

public class Param {
    private String name;
    private ClassStructure2 type;
    private Annotation annotation;

    public Param(String name, ClassStructure2 type, Annotation annotation) {
        this.name = name;
        this.type = type;
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public ClassStructure2 getType() {
        return type;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
