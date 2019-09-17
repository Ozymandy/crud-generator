package co.ihnatsen;

import java.util.HashMap;
import java.util.Map;

public class Annotation {

    private static final String DEFAULT_ANNOTATION_VALUE_KEY = "value";

    private UtilityTypes annotationType;
    private Map<String, String> values;

    public Annotation(UtilityTypes type) {
        this.annotationType = type;
        values = new HashMap<>();
    }

    public Annotation withValue(String value) {
        this.values.put(DEFAULT_ANNOTATION_VALUE_KEY, value);
        return this;
    }

    public Annotation withValue(String key, String value) {
        this.values.put(key, value);
        return this;
    }

    public Map<String, String> values() {
        return values;
    }

    public UtilityTypes getType() {
        return annotationType;
    }
}
