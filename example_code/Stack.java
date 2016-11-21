/*
 * Stack.java
 * 
 * Computer Science E-22, Harvard University
 */

/**
 * A generic interface that defines a simple ADT for a stack of
 * objects of a particular type.
 */
public interface Stack<T> {
    /** 
     * adds the specified item to the top of the stack.  Returns false
     * if the list is full, and true otherwise.
     */
    boolean push(T item);

    /** 
     * removes the item at the top of the stack and returns a
     * reference to the removed object.  Returns null is the stack is
     * empty.
     */
    T pop();

    /** 
     * returns a reference to the item at the top of the stack without
     * removing it. Returns null is the stack is empty.
     */
    T peek();

    /** returns true if the stack is empty, and false otherwise */
    boolean isEmpty();

    /** returns true if the stack is full, and false otherwise */
    boolean isFull();
}
