package top.nintha.json;

import java.util.HashMap;
import java.util.stream.Collectors;

public class JsonObject extends HashMap<String, JsonValue> implements JsonValue {
    @Override
    public String toJson() {
        return this.entrySet().stream().map(entry -> {
            return String.format("\"%s\":%s", entry.getKey(), entry.getValue().toJson());
        }).collect(Collectors.joining(",", "[", "]"));
    }
}
