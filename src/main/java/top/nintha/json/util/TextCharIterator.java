package top.nintha.json.util;

import java.util.Iterator;

public class TextCharIterator implements Peekable<Character>, Iterator<Character> {
    private final char[] chars;
    private int readIndex = 0;

    public TextCharIterator(String text) {
        this.chars = text.toCharArray();
    }

    @Override
    public boolean hasNext() {
        return readIndex < chars.length;
    }

    @Override
    public Character next() {
        return hasNext() ? chars[readIndex++] : null;
    }

    @Override
    public Character peek() {
        return chars[readIndex];
    }
}
