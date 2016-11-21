/*
 * LinkedTreeIterator.java
 *
 * Computer Science E-119, Harvard University
 *
 * YOU SHOULD *NOT* NEED TO MODIFY THIS FILE.
 */

/**
 * An interface that describes the functionality that must be supported
 * by classes that implement iterators for LinkedTree objects.
 */
public interface LinkedTreeIterator {
    // Are there other nodes to see in this traversal?
    boolean hasNext();
    
    // Return the value of the key in the next node in the
    // traversal, and advance the position of the iterator.
    int next();
}
