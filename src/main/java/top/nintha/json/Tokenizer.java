package top.nintha.json;

import java.io.IOException;
import java.io.PushbackReader;

public class Tokenizer {

    private final PushbackReader reader;

    public Tokenizer(PushbackReader reader) {
        this.reader = reader;
    }

    public Token next() throws IOException {
        int v;
        while ((v = reader.read()) != -1) {
            char c = (char) v;
            if (Character.isWhitespace(c)) {
                continue;
            }
            return charToToken(c);
        }
        return null;
    }

    private Token charToToken(char c) throws IOException {
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

    private String readText(char first) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        int v;
        while ((v = reader.read()) != -1) {
            char c = (char) v;
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

    private double readNum(char first) throws IOException {
        StringBuilder sb = new StringBuilder(String.valueOf(first));
        boolean point = false;
        int v;
        while ((v = reader.read()) != -1) {
            char c = (char) v;
            // number
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
            // point
            else if (c == '.') {
                if (point) {
                    reader.unread(c);
                    return Double.parseDouble(sb.toString());
                } else {
                    point = true;
                    sb.append(c);
                }
            }
            // other char
            else {
                reader.unread(c);
                return Double.parseDouble(sb.toString());
            }
        }
        return Double.parseDouble(sb.toString());
    }

    private Token readSymbol(char first) throws IOException {
        StringBuilder sb = new StringBuilder(String.valueOf(first));
        int v;
        while ((v = reader.read()) != -1) {
            char c = (char) v;
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else {
                reader.unread(c);
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


    public static void main(String[] args) {
        Token token1 = new Token.Colon();
        Token token2 = new Token.Text("username");

//        System.out.println(colon.getClass().getName());
    }


}
