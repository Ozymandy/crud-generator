package co.ihnatsen.classes;

import co.ihnatsen.Context;
import com.sun.codemodel.JType;

public class RefStructure implements ClassStructure2 {

    private final String ref;

    public RefStructure(String ref) {
        this.ref = ref;
    }

    @Override
    public JType init(Context context) {
        return context.initType(this);
    }
}
