/*
 * ArrayStack.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A generic class that implements our Stack interface using an array.
 */
public class ArrayStack<T> implements Stack<T> {
    private T[] items;          // the items on the stack
    private int top;            // the index of the top item
    
    /**
     * Constructs an ArrayStack object with the specified maximum size
     * for a stack that is initially empty.
     */
    public ArrayStack(int maxSize) {
        items = (T[])new Object[maxSize];
        top = -1;
    }
    
    /** 
     * push - adds the specified item to the top of the stack.
     * Returns false if the stack is full, and true otherwise.
     */
    public boolean push(T item) {
        if (isFull())
            return false;
        top++;
        items[top] = item;
        return true;
    }
    
    /** 
     * pop - removes the item at the top of the stack and returns a
     * reference to the removed object.  Returns null if the stack is
     * empty.
     */
    public T pop() {
        if (isEmpty())
            return null;
        T removed = items[top];
        items[top] = null;
        top--;
        return removed;
    }
    
    /** 
     * peek - returns a reference to the item at the top of the stack
     * without removing it. Returns null is the stack is empty.
     */
    public T peek() {
        if (isEmpty())
            return null;
        return items[top];
    }
    
    /** 
     * isEmpty - returns true if the stack is empty, and false otherwise
     */
    public boolean isEmpty() {
        return (top == -1);
    }
    
    /**
     * isFull - returns true if the stack is full, and false otherwise 
     */
    public boolean isFull() {
        return (top == items.length - 1);
    }
    
    /**
     * toString - converts the stack into a String of the form 
     * {top, one-below-top, two-below-top, ...}
     */
    public String toString() {
        String str = "{";
        
        for (int i = top; i >= 0; i--) {
            str = str + items[i];
            if (i > 0)
                str = str + ", ";
        }
        
        str = str + "}";
        return str;
    }
}
