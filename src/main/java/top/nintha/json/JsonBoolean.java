package top.nintha.json;

public class JsonBoolean implements JsonValue {
    private boolean value = false;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
