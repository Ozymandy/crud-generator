package co.ihnatsen.classes;

import co.ihnatsen.Annotation;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

import java.util.List;
import java.util.Map;

public abstract class ClassStructure {

    private List<Annotation> classAnnotations;
    private List<JClass> implementClasses;
    private String finalPackage;
    private Map<JClass, String> invocations;
    public void init(final JCodeModel cm) {
        JClass j;
    }

    public abstract String className();
}

