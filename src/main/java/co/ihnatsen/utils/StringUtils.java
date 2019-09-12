package co.ihnatsen.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {

    public static String fieldName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    public static String generateURI(String entityName) {
        return "/" + entityName.toLowerCase();
    }

    public static String fieldToJsonName(String field) {
        return Stream.of(field.split("(?=\\p{Upper})")).map(str -> str.toLowerCase()).collect(Collectors.joining("_"));
    }
}
