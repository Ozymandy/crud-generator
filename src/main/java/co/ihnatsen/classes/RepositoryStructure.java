package co.ihnatsen.classes;

import static java.lang.String.format;

public class RepositoryStructure extends CrudStructure {

    private final String entityName;
    private ClassStructure2 implement;

    public RepositoryStructure(String entityName) {
        super("%sRepository", null);
        this.entityName = entityName;
    }
    @Override
    public String read() {
        return "findById";
    }

    @Override
    public String readAll() {
        return "findAll";
    }

    @Override
    public String create() {
        return "save";
    }

    @Override
    public String update() {
        return "save";
    }

    @Override
    public String delete() {
        return "deleteById";
    }

    @Override
    public String className() {
        return format(name, entityName);
    }
}
