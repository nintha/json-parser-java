package top.nintha.json.util;

public interface CharPeekable {
    /**
     * get an element, but not consume it.
     */
    char peek();

    boolean hasNext();
}
