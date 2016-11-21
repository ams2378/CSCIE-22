/*
 * ArrayList.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/**
 * A class that implements our simple List interface using an array.
 */
public class ArrayList implements List {
    private Object[] items;     // the items in the list
    private int length;         // # of items in the list
    
    /**
     * Constructs an ArrayList object with the specified maximum size
     * for a list that is initially empty.
     */
    public ArrayList(int maxSize) {
        items = new Object[maxSize];
        length = 0;
    }
    
    /** getItem - returns the item at position i in the list */
    public Object getItem(int i) {
        if (i < 0 || i >= length)
            throw new IndexOutOfBoundsException();
        return items[i];
    }
    
    /** 
     * addItem - adds the specified item at position i in the list,
     * shifting the items that are currently in positions i, i+1, i+2,
     * etc. to the right by one.  Returns false if the list is full,
     * and true otherwise.
     */
    public boolean addItem(Object item, int i) {
        if (i < 0 || i > length)
            throw new IndexOutOfBoundsException();
        
        if (isFull())
            return false;
        
        // make room for the new item
        for (int j = length - 1; j >= i; j--)
            items[j + 1] = items[j];
        
        items[i] = item;
        length++;
        return true;
    }
    
    /** 
     * removeItem - removes the item at position i in the list,
     * shifting the items that are currently in positions i+1, i+2,
     * etc. to the left by one.  Returns a reference to the removed
     * object.
     *
     * Here again, we don't need a special case for i == 0 (see the
     * note accompanying addItem above).
     */
    public Object removeItem(int i) {
        if (i < 0 || i >= length)
            throw new IndexOutOfBoundsException();
        
        Object removed = items[i];
        
        // fill in the "hole" left by the removed item
        for (int j = i; j < length - 1; j++)
            items[j] = items[j + 1];       
        items[length - 1] = null;
        
        length--;
        return removed;
    }
    
    /** length - returns the number of items in the list */
    public int length() {
        return length;
    }
    
    /** 
     * isFull - always returns false, because the linked list can
     * grow indefinitely and thus the list is never full.
     */
    public boolean isFull() {
        return (length == items.length);
    }
    
    /**
     * toString - converts the list into a String of the form 
     * {item0, item1, ...}
     */
    public String toString() {
        String str = "{";
        
        for (int i = 0; i < length; i++) {
            str = str + items[i];
            if (i < length - 1)
                str = str + ", ";
        }
        
        str = str + "}";
        return str;
    }
    
    /**
     * iterator - returns an iterator for this list
     */
    public ListIterator iterator() {
        // still needs to be implemented
        return null;
    }
}
