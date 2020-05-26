package top.nintha.json.util;

public interface Peekable<T> {
    /**
     * get an element, but not consume it.
     */
    T peek();
}
