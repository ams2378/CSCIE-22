/*
 * ListIterator.java
 *
 * Computer Science E-119, Harvard University
 *
 * YOU SHOULD *NOT* NEED TO MODIFY THIS FILE.
 */

/**
 * An interface for an iterator that iterates over a List.
 */
public interface ListIterator {
    /**
     * does the iterator have additional items to visit?
     */
    boolean hasNext();
    
    /**
     * return a reference to the next Object in the iteration
     */
    Object next();
}
