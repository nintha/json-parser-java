package top.nintha.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JUnitPlatform.class)
class ParserTest {
    @Test
    void parseText() {
        String text = "1234567890qwertyuiopasdfghjklzxcvbnm\r\n";
        Parser parser = new Parser("\"" + text + "\"");
        JsonValue value = parser.parse();
        Assertions.assertTrue(value instanceof JsonString);
        JsonString str = (JsonString) value;
        Assertions.assertEquals(text, str.toString());
    }

    @Test
    void parseBooleanTrue() {
        Parser parser = new Parser("true");
        JsonValue value = parser.parse();
        Assertions.assertTrue(value instanceof JsonBoolean);
        JsonBoolean b = (JsonBoolean) value;
        Assertions.assertTrue(b.getValue());
    }

    @Test
    void parseBooleanFalse() {
        Parser parser = new Parser("false");
        JsonValue value = parser.parse();
        Assertions.assertTrue(value instanceof JsonBoolean);
        JsonBoolean b = (JsonBoolean) value;
        Assertions.assertFalse(b.getValue());
    }

    @Test
    void parseNull() {
        Parser parser = new Parser("null");
        JsonValue value = parser.parse();
        Assertions.assertTrue(value instanceof JsonNull);
        JsonNull b = (JsonNull) value;
        Assertions.assertTrue(b.isNull());
    }

    @Test
    void parseNumber() {
        List<Double> list = List.of(0.0, 123456.0, 99.99, -87.0, -1.23);
        for (Double expect : list) {
            Parser parser = new Parser(String.valueOf(expect));
            JsonValue value = parser.parse();
            Assertions.assertEquals(expect, value.asNumber());
        }
    }

    @Test
    void parseArray() {
        String json = "[true, false, null, {}, 0, [], \"hello\"]";
        Parser parser = new Parser(json);
        JsonArray array = parser.parse().asJsonArray();
        Assertions.assertTrue(array.get(0).asBoolean());
        Assertions.assertFalse(array.get(1).asBoolean());
        Assertions.assertTrue(array.get(2).isNull());
        Assertions.assertTrue(array.get(3).asJsonObject().isEmpty());
        Assertions.assertEquals(0.0, array.get(4).asNumber());
        Assertions.assertTrue(array.get(5).asJsonArray().isEmpty());
        Assertions.assertEquals("hello", array.get(6).asString());
    }

    @Test
    void parseObject() {
        String json = "{\"true\": true, \"false\": false, \"null\": null, \"object\": {}, \"number\": 0, \"array\": [], \"string\": \"hello\"}";
        Parser parser = new Parser(json);
        JsonObject object = parser.parse().asJsonObject();
        Assertions.assertTrue(object.get("true").asBoolean());
        Assertions.assertFalse(object.get("false").asBoolean());
        Assertions.assertTrue(object.get("null").isNull());
        Assertions.assertTrue(object.get("object").asJsonObject().isEmpty());
        Assertions.assertEquals(0.0, object.get("number").asNumber());
        Assertions.assertTrue(object.get("array").asJsonArray().isEmpty());
        Assertions.assertEquals("hello", object.get("string").asString());

//        System.out.println(object.toJson());
    }
}