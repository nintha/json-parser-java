package top.nintha.json;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class JsonArray extends ArrayList<JsonValue> implements JsonValue {

    @Override
    public String toJson() {
        return this
                .stream()
                .map(JsonValue::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
