/*
 * List.java
 * 
 * Computer Science E-22, Harvard University
 */

/**
 * An interface that defines a simple ADT for a list of objects of any
 * type.
 *
 * Methods that take an index i throw an IndexOutOfBoundsException
 * if i is less than 0 or greater than or equal to the length of the list.
 */
public interface List {
    /** returns the item at position i in the list */
    Object getItem(int i);

    /** 
     * adds the specified item at position i in the list, shifting the
     * items that are currently in positions i, i+1, i+2, etc. to the
     * right by one.  Returns false if the list is full, and true
     * otherwise.
     */
    boolean addItem(Object item, int i);

    /** 
     * removes the item at position i in the list, shifting the items
     * that are currently in positions i+1, i+2, etc. to the left by
     * one.  Returns a reference to the removed object.
     */
    Object removeItem(int i);

    /** returns the number of items in the list */
    int length();

    /** returns true if the list is full, and false otherwise */
    boolean isFull();

    /** returns an iterator object for this list. */
    ListIterator iterator();
}
