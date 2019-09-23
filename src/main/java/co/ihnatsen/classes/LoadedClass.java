package co.ihnatsen.classes;

import co.ihnatsen.Context;
import com.sun.codemodel.JType;

public class LoadedClass extends ClassStructure2 {
    private final Class<?> clazz;

    public LoadedClass(final Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public JType init(Context context) {
        return context.initType(this);
    }
}
