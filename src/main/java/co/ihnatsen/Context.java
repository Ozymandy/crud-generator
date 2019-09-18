package co.ihnatsen;

import co.ihnatsen.classes.ClassStructure;
import co.ihnatsen.classes.ClassStructure2;
import co.ihnatsen.utils.ClassUtils;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Context {

    private HashMap<ClassStructure2, JType> classPool;
    private JCodeModel codeModel;

    public void run() throws IOException, ClassNotFoundException {
        classPool = new HashMap<>();
        codeModel = new JCodeModel();
        ClassUtils.getEntityMetaData();
        File file = new File("./target/classes");
        file.mkdirs();
        codeModel.build(file);
    }

    public JType initType(ClassStructure2 structure) {
        //TODO: implement
        return classPool.computeIfAbsent(structure, (classS) -> null);
    }
}
