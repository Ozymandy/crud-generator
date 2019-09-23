package co.ihnatsen.classes;

import co.ihnatsen.Context;
import com.sun.codemodel.JType;

public abstract class CreatedClass extends ClassStructure2 {

    protected final String name;
    protected Annotation classAnnotation;

    protected CreatedClass(String name, Annotation classAnnotation) {
        super();
        this.name = name;
        this.classAnnotation = classAnnotation;
    }

    protected CreatedClass(String name) {
        this.name = name;
    }

    @Override
    public JType init(Context context) {
        return null;
    }
}
