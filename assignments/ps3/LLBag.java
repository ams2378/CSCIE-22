/* 
 * ArrayBag.java
 * 
 * Computer Science E-22, Harvard University
 * Adil Sadik
 * sadik.adil@gmail.com
 */

import java.util.*;

/**
 * An implementation of a Bag ADT using an array.
 */
public class LLBag implements Bag {
    
    // Inner class for a node.  We use an inner class so that the LLList
    // methods can access the instance variables of the nodes.
    private class Node {
        private Object item;
        private Node next;
        
        private Node(Object i, Node n) {
            item = i;
            next = n;
        }
    }
    
    private Node head;     // dummy head node
    private Node tail;     // dummy head node
    private int numItems;    // # of items in the bag
    
    /**
     * Constructs a LLList object for a list that is initially empty.
     */
    public LLBag() {
        head = new Node(null, null);
        tail = head;
        numItems = 0;
    }
    
    
    /** 
     * add - adds the specified item to the Bag.  Returns true on
     * success and false if there is no more room in the Bag.
     */
    public boolean add(Object item) {
        if (item == null)
            throw new IllegalArgumentException("item must be non-null");
        if (numItems == 0) {// if bag is empty
            addItem(item, 0);
        } else {
            addItem(item, -1); // add to the tail
        }
        
        return true;
    }
    
    private void addItem(Object item, int index) {
        if (index == 0) { // add to the head
            head.item = item;
            head.next = null;
        } else { // add to the tail
            Node newNode = new Node(item, null);
            tail.next = newNode;
            tail = newNode;
        }
        numItems++;
    }
    
    /** 
     * remove - removes one occurrence of the specified item (if any)
     * from the Bag.  Returns true on success and false if the
     * specified item (i.e., an object equal to item) is not in the Bag.
     */
    public boolean remove(Object item) {
        
        Node trav = head;
        Node prevNode = head;
        
        while (trav != null) {
            if (trav.item == item) {
                prevNode.next = trav.next;
                numItems--;
                return true;
            }
            prevNode = trav;
            trav = trav.next;
        }
        
        return false;  // item not found
    }
    
    /**
     * contains - returns true if the specified item is in the Bag, and
     * false otherwise.
     */
    public boolean contains(Object item) {
        Node trav = head;
        
        while (trav != null) {
            if (trav.item == item) {
                return true;
            }
            trav = trav.next;
        }
        return false;
    }
    
    /**
     * containsAll - does this ArrayBag contain all of the items in
     * otherBag?  Returns false if otherBag is null or empty. 
     */
    public boolean containsAll(Bag otherBag) {
        if (otherBag == null || otherBag.numItems() == 0)
            return false;
        
        Object[] otherItems = otherBag.toArray();
        
        for (int i = 0; i < otherItems.length; i++) {
            if (!contains(otherItems[i]))
                return false;
        }
        
        return true;
    }
    
    /**
     * numItems - returns the number of items in the Bag.
     */
    public int numItems() {
        return numItems;
    }
    
    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    public Object grab() {
        if (numItems == 0)
            throw new NoSuchElementException("the bag is empty");
        int whichOne = (int)(Math.random() * numItems) - 1;
        
        Node trav = head;
        int travIndex = -1;
        while (travIndex < whichOne) {
            travIndex++;
            trav = trav.next;
        }
        
        System.out.println("travindex " + travIndex );
        
        return trav.item;
    }
    
    /**
     * toArray - return an array containing the current contents of the bag
     */
    public Object[] toArray() {
        Object[] copy = new Object[numItems];
        
        Node trav = head;
        int index = 0;
        
        while (trav != null) {
            copy[index] = trav.item;
            trav = trav.next;
            index++;
        }
        
        return copy;
    }
    
    /**
     * toString - converts this ArrayBag into a readable String object.
     * Overrides the Object version of this method.
     */
    public String toString() {
        String str = "{";
        
        Node trav = head;
        
        while (trav != null) {
            str = str + " " + trav.item;
            trav = trav.next;
        }
        
        str = str + " }";
        return str;
    }
    
    /* Test the ArrayBag implementation. */
    public static void main(String[] args) {
        
        // Create an LLBag named bag1.
        Bag bag1 = new LLBag();
        
        bag1.add(1);
        bag1.add(2);
        bag1.add(3);
        bag1.add("test");
        bag1.add("hello");
        
        System.out.println("num of items = " + bag1.numItems());
        System.out.println("bag 1 = " + bag1.toString());
        System.out.println("remove item - hello - from bag ");
        
        bag1.remove("hello");
        System.out.println("num of items = " + bag1.numItems());
        System.out.println("bag 1 = " + bag1.toString());
        System.out.println();
        
        System.out.println("grabbed an item from bag = " + bag1.grab());
    }
}