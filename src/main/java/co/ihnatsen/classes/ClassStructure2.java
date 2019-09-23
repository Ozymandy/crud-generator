package co.ihnatsen.classes;

import co.ihnatsen.Context;
import com.sun.codemodel.JType;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassStructure2 {

    protected final List<ClassStructure2> narrowTypes;

    protected ClassStructure2() {
        this.narrowTypes = new ArrayList<>();
    }

    public abstract JType init(Context context);

    public ClassStructure2 narrow(ClassStructure2 type) {
        narrowTypes.add(type);
        return this;
    }
}
