package top.nintha.json;

import top.nintha.json.util.TextCharIterator;

public class Tokenizer {

    private final TextCharIterator iterator;

    public Tokenizer(String text) {
        this.iterator = new TextCharIterator(text);
    }

    public Token next() {
        while (iterator.hasNext()) {
            char c = iterator.next();
            if (Character.isWhitespace(c)) {
                continue;
            }
            return charToToken(c);
        }
        return null;
    }

    private Token charToToken(char c) {
        switch (c) {
            case ',':
                return new Token.Comma();
            case ':':
                return new Token.Colon();
            case '[':
                return new Token.BracketOn();
            case ']':
                return new Token.BracketOff();
            case '{':
                return new Token.BraceOn();
            case '}':
                return new Token.BraceOff();
            case '"':
                return new Token.Text(readText(c));
            case '-':
                return new Token.Num(readNum(c));
            default:
                if (c >= '0' && c <= '9') {
                    return new Token.Num(readNum(c));
                }

                if (c >= 'a' && c <= 'z') {
                    return readSymbol(c);
                }

                throw new RuntimeException("Invalid character: " + c);
        }
    }

    private String readText(char first) {
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        while (iterator.hasNext()) {
            char c = iterator.next();
            if (c == first && !escape) {
                return sb.toString();
            }

            if (c == '\\') {
                if (escape) {
                    sb.append(c);
                }
                escape = !escape;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private double readNum(char first) {
        StringBuilder sb = new StringBuilder(String.valueOf(first));
        boolean point = false;
        while (iterator.hasNext()) {
            char c = iterator.peek();
            // number
            if (c >= '0' && c <= '9') {
                sb.append(c);
                iterator.next();
            }
            // point
            else if (c == '.') {
                if (point) {
                    return Double.parseDouble(sb.toString());
                } else {
                    point = true;
                    sb.append(c);
                    iterator.next();
                }
            }
            // other char
            else {
                return Double.parseDouble(sb.toString());
            }
        }
        return Double.parseDouble(sb.toString());
    }

    private Token readSymbol(char first) {
        StringBuilder sb = new StringBuilder(String.valueOf(first));
        while (iterator.hasNext()) {
            char c = iterator.peek();
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
                iterator.next();
            } else {
                break;
            }
        }
        String label = sb.toString();
        switch (label) {
            case "true":
                return new Token.Bool(true);
            case "false":
                return new Token.Bool(false);
            case "null":
                return new Token.Null();
            default:
                throw new RuntimeException("Invalid label: " + label);
        }
    }
}
