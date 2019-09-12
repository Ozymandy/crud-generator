package co.ihnatsen.classes;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

import java.util.List;
import java.util.Map;

public abstract class ClassStructure {

    private String wildcard;
    private List<JClass> classAnnotations;
    private List<JClass> implementClasses;
    private String finalPackage;
    private Map<JClass, String> invocations;
    public void init(final JCodeModel cm) {
        JClass j;
    }
}

