# JSON Parser

Java版本JSON解析工具

参考资料： https://github.com/ltoddy/parser-tutorial

## Usage

```
void main(String[] args) {
    String json = "{\"true\": true, \"false\": false, \"null\": null, \"object\": {}, \"number\": 0, \"array\": [], \"string\": \"\\u6211\\u662F\\u5730\\u7403\\uD83C\\uDF0D\"}";
    Parser parser = new Parser(json);
    JsonObject object = parser.parse().asJsonObject();
    System.out.println(object); // {number=0.0, null=null, string=我是地球🌍, array=[], true=true, false=false, object={}}
}
```

## Feature
- [x] 支持unicode字符(包括emoji)
