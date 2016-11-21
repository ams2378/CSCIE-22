/*
 * IteratorTest.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A program that tests our list-iterator implementation.
 */
public class IteratorTest {
    /*
     * numOccur - uses an iterator to determine and return the number
     * of occurrences of the specified item in the list l.
     */
    public static int numOccur(List l, Object item) {
        int numOccur = 0;
        
        ListIterator iter = l.iterator();
        while (iter.hasNext()) {
            Object itemAt = iter.next();
            if (itemAt.equals(item))
                numOccur++;
        }
        
        return numOccur;
    } 
    
    public static void main(String[] args) {
        List list = new LLList();
        
        list.addItem("a", 0);
        list.addItem("b", 0);
        list.addItem("a", 0);
        list.addItem("c", 0);
        list.addItem("c", 0);
        list.addItem("a", 0);
        
        System.out.println("a occurs " + numOccur(list, "a") + " times.");
        System.out.println("b occurs " + numOccur(list, "b") + " times.");
        System.out.println("c occurs " + numOccur(list, "c") + " times.");
    }
}
