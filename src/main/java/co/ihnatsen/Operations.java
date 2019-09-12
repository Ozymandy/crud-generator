package co.ihnatsen;

import java.util.List;

import static java.util.Arrays.asList;

public enum Operations {
    CREATE("create%s"),
    READ("get%s"),
    READ_ALL("getAll"),
    UPDATE("update%s"),
    DELETE("delete%s");

    String methodName;

    Operations(String methodName) {
        this.methodName = methodName;
    }

    public String getMethod() {
        return methodName;
    }

    static List<Operations> all() {
        return asList(CREATE, READ, READ_ALL, UPDATE, DELETE);
    }
}
