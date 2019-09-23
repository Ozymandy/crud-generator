package co.ihnatsen.classes;

import co.ihnatsen.Context;
import com.sun.codemodel.JType;

public abstract class CrudStructure extends CreatedClass {

    protected CrudStructure(final String name,
                            final Annotation classAnnotation) {
        super(name, classAnnotation);
    }

    protected CrudStructure(final String name) {
        super(name);
    }

    @Override
    public JType init(Context context) {
        return null;
    }

    public abstract String read();

    public abstract String readAll();

    public abstract String create();

    public abstract String update();

    public abstract String delete();

    public abstract String className();
}
