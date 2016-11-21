/*
 * LLStack.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A generic class that implements our Stack interface using a linked list.
 */
public class LLStack<T> implements Stack<T> {
    // Inner class for a node.  We use an inner class so that the LLStack
    // methods can access the instance variables of the nodes.
    private class Node {
        private T item;
        private Node next;
        
        private Node(T i, Node n) {
            item = i;
            next = n;
        }
    }
    
    private Node top;           // the node containing the top item
    
    /**
     * Constructs an LLStack object for a stack that is initially
     * empty.
     */
    public LLStack() {
        top = null;
    }
    
    /** 
     * push - adds the specified item to the top of the stack.
     * Always returns true, because the linked list is never full.
     */
    public boolean push(T item) {
        // We insert the new node at the front of the linked list.
        // Note that we assign the new node's next field a reference to the
        // current front of the linked list (top).
        Node newNode = new Node(item, top);
        top = newNode;
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
        
        T removed = top.item;
        top = top.next;
        return removed;
    }
    
    /** 
     * peek - returns a reference to the item at the top of the stack
     * without removing it. Returns null is the stack is empty.
     */
    public T peek() {
        if (isEmpty())
            return null;
        return top.item;
    }
    
    /** 
     * isEmpty - returns true if the stack is empty, and false otherwise
     */
    public boolean isEmpty() {
        return (top == null);
    }
    
    /**
     * isFull - always returns false, because the linked list can
     * grow indefinitely and thus the stack is never full.
     */
    public boolean isFull() {
        return false;
    }
    
    /**
     * toString - converts the stack into a String of the form 
     * {top, one-below-top, two-below-top, ...}
     */
    public String toString() {
        String str = "{";
        
        Node trav = top;
        while (trav != null) {
            str = str + trav.item;
            if (trav.next != null)
                str = str + ", ";
            trav = trav.next;
        }
        
        str = str + "}";
        return str;
    }
}
