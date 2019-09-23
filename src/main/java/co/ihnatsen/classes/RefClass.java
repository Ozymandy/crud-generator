package co.ihnatsen.classes;

import co.ihnatsen.Context;
import com.sun.codemodel.JType;

public class RefClass extends ClassStructure2 {

    private final String ref;

    public RefClass(String ref) {
        this.ref = ref;
    }

    @Override
    public JType init(Context context) {
        return context.initType(this);
    }
}
