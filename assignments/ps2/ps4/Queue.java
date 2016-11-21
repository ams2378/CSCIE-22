/*
 * Queue.java
 * 
 * Computer Science E-22, Harvard University
 */

/**
 * A generic interface that defines a simple ADT for a queue of
 * objects of a particular type T.
 */
public interface Queue<T> {
    /** 
     * adds the specified item to the rear of the queue.  Returns false
     * if the list is full, and true otherwise.
     */
    boolean insert(T item);

    /** 
     * removes the item at the front of the queue and returns a
     * reference to the removed object.  Returns null is the queue is
     * empty.
     */
    T remove();

    /** 
     * returns a reference to the item at the front of the queue without
     * removing it. Returns null is the queue is empty.
     */
    T peek();

    /** returns true if the queue is empty, and false otherwise */
    boolean isEmpty();

    /** returns true if the queue is full, and false otherwise */
    boolean isFull();
}