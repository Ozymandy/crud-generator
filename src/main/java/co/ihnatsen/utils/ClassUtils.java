package co.ihnatsen.utils;

import co.ihnatsen.EntityStructure;

import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class ClassUtils {

    public static Set<EntityStructure> getEntityMetaData() throws IOException, ClassNotFoundException {
        return getClasses("co.tula.entity")
                .stream()
                .map(clazz -> {
                    EntityStructure entity = new EntityStructure();
                    entity.setEntityName(clazz.getSimpleName());
                    entity.setEntityClass(clazz);
                    Class<?> idClass = Stream.of(clazz.getDeclaredFields())
                            .filter(
                                    field-> stream(field.getAnnotations())
                                            .filter(annotation -> annotation.annotationType().equals(Id.class))
                                            .findFirst()
                                            .isPresent()
                            )
                            .map(field -> field.getType())
                            .findFirst()
                            .orElse((Class)Long.class);
                    entity.setIdClass(idClass);
                    Stream.of(clazz.getDeclaredFields()).forEach(field -> entity.addField(field.getName(),
                            field.getType()));
                    return entity;
                })
                .collect(Collectors.toSet());
    }

    private static List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0,
                        file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
