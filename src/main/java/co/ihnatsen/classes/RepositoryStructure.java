package co.ihnatsen.classes;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class RepositoryStructure extends CrudStructure {

    private final String entityName;
    private ClassStructure2 implement;
    private List<ClassStructure2> narrowed;

    public RepositoryStructure(String entityName) {
        super("%sRepository");
        narrowed = new ArrayList<>();
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

    @Override
    public void narrow(ClassStructure2 type) {
        narrowed.add(type);
    }
}
