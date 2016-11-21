/* 
 * Bag.java
 * 
 * Computer Science E-22, Harvard University
 */

/**
 * An interface for a Bag ADT.
 */
public interface Bag {
    /** 
     * adds the specified item to the Bag.  Returns true on success
     * and false if there is no more room in the Bag.
     */
    boolean add(Object item);

    /** 
     * removes one occurrence of the specified item (if any) from the
     * Bag.  Returns true on success and false if the specified item
     * (i.e., an object equal to item) is not in the Bag.
     */
    boolean remove(Object item);

    /**
     * returns true if the specified item is in the Bag, and false
     * otherwise.
     */
    boolean contains(Object item);

    /**
     * returns true if the calling object contain all of the items in
     * otherBag, and false otherwise.  Also returns false if otherBag 
     * is null or empty. 
     */
    boolean containsAll(Bag otherBag);

    /**
     * returns the number of items in the Bag.
     */
    int numItems();

    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    Object grab();

    /**
     * toArray - return an array containing the current contents of the bag
     */
    Object[] toArray();
    
    /**
     * roomLeft - This method should return the number of additional items that ArrayBag has room to store. 
     */
    int roomLeft();
    
    /**
     * isEmpty - This method should return true if the called ArrayBag is empty, and false otherwise.
     */
    boolean isEmpty();    
    
    /**
     * increaseCapacity - This method should increase the maximum capacity of the called ArrayBag by the specified amount. 
     */
    void increaseCapacity(int amount);
        
    /**
     * addItems - This method should attempt to add to the called ArrayBag all of the items found in the parameter other.  
     */
    boolean addItems(Bag other);
        
    /**
     * intersectionWith - This method should create and return an ArrayBag containing one occurrence of any item that is found in both the called object and the parameter other.
     */
    Bag intersectionWith(Bag other);
    
    
} 