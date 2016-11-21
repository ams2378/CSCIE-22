/*
 * HashTable.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A class that implements a hash table that employs open addressing
 * using either linear probing, quadratic probing, or double hashing.
 */
public class HashTable {
    /* Private inner class for an entry in the hash table */
    private class Entry {
        private String key;
        private LLList valueList;        // all of the values with this key
        private boolean hasBeenRemoved;  // has this entry been removed?
        
        private Entry(String key, int value) {
            this.key = key;
            valueList = new LLList();
            valueList.addItem(value, 0);
            hasBeenRemoved = false;
        }
    }
    
    // parameters for the second hash function -- see h2() below
    private static final int H2_MIN = 5;
    private static final int H2_DIVISOR = 11;
    
    // possible types of probing
    public static final int LINEAR = 0;
    public static final int QUADRATIC = 1;
    public static final int DOUBLE_HASHING = 2;
    public static final int NUM_PROBE_TYPES = 3;
    
    private Entry[] table;             // the hash table itself
    private int probeType = LINEAR;    // the type of probing
    
    // keeps track of how many times we perform a probe of a given length
    private int[] probeLengthCount;
    
    public HashTable(int size, int probeType) {
        if (probeType >= 0 && probeType < NUM_PROBE_TYPES)
            this.probeType = probeType;
        else
            throw new IllegalArgumentException("invalid probeType: " + 
                                               probeType);
        
        table = new Entry[size];
        probeLengthCount = new int[size + 1];
        for (int i = 0; i <= size; i++)
            probeLengthCount[i] = 0;
    }
    
    public HashTable(int size) {
        // Call the other constructor to do the work.
        this(size, LINEAR);
    }
    
    /* first hash function */
    private int h1(String key) {
        int h1 = key.hashCode() % table.length;
        if (h1 < 0)
            h1 += table.length;
        return h1;
    }
    
    /* second hash function */
    private int h2(String key) {
        int h2 = key.hashCode() % H2_DIVISOR;
        if (h2 < 0)
            h2 += H2_DIVISOR;
        h2 += H2_MIN;
        return h2;
    }
    
    /* 
     * probeIncrement - returns the amount by which the current index
     * should be incremented to obtain the nth element in the probe
     * sequence
     */
    private int probeIncrement(int n, int h2) {
        if (n <= 0)
            return 0;
        
        switch (probeType) {
            case LINEAR:
                return 1;
            case QUADRATIC:
                return (2*n - 1);
            case DOUBLE_HASHING:
            default:
                return h2;
        }
    }
    
    /*
     * probe - attempt to find a slot in the hash table for the specified key.
     *
     * If key is currently in the table, it returns the index of the entry.
     * If key isn't in the table, it returns the index of the first empty cell
     * in the table.
     * If overflow occurs, it returns -1.
     */
    private int probe(String key) {
        int i = h1(key);    // first hash function
        int h2 = h2(key);   // second hash function
        int positionsChecked = 1;
        
        // keep probing until we get an empty position or a match
        while (table[i] != null && !key.equals(table[i].key)) {
            if (positionsChecked == table.length) {
                probeLengthCount[positionsChecked]++;
                return -1;
            }
            
            i = (i + probeIncrement(positionsChecked, h2)) % table.length;
            positionsChecked++;
        }
        
        probeLengthCount[positionsChecked]++; 
        return i;
    }
    
    /**
     * insert - insert the specified (key, value) pair in the hash table
     */
    public void insert(String key, int value) {
        if (key == null)
            throw new IllegalArgumentException("key must be non-null");
        
        int i = h1(key); 
        int h2 = h2(key);
        int positionsChecked = 1;
        int firstRemoved = -1;
        
        while (table[i] != null && !key.equals(table[i].key)) {
            if (table[i].hasBeenRemoved && firstRemoved == -1)
                firstRemoved = i;
            
            if (positionsChecked == table.length)
                break;
            
            i = (i + probeIncrement(positionsChecked, h2)) % table.length;
            positionsChecked++;
        }
        
        probeLengthCount[positionsChecked]++; 
        
        if (table[i] != null && key.equals(table[i].key))
            table[i].valueList.addItem(value, 0);
        else if (firstRemoved != -1)
            table[firstRemoved] = new Entry(key, value);
        else if (table[i] == null)
            table[i] = new Entry(key, value);
        else
            throw new RuntimeException("overflow occurred");
    }
    
    /**
     * search - search for the specified key, and return the
     * associated list of values, or null if the key is not in the
     * table
     */
    public LLList search(String key) {
        if (key == null)
            throw new IllegalArgumentException("key must be non-null");
        
        int i = probe(key);
        
        if (i == -1 || table[i] == null)
            return null;
        else
            return table[i].valueList;
    }
    
    /**
     * remove - remove from the table the entry for the specified key
     */
    public void remove(String key) {
        if (key == null)
            throw new IllegalArgumentException("key must be non-null");
        
        int i = probe(key);
        if (i == -1 || table[i] == null)
            return;
        
        table[i].key = null;
        table[i].valueList = null;
        table[i].hasBeenRemoved = true;
    }
    
    /**
     * printStats - print the statistics for the table -- i.e., the
     * number of keys and items, and stats for the number of times
     * that probes of different lengths were performed
     */
    public void printStats() {
        int numProbes = 0;
        int probeLengthSum = 0;
        int numKeys = 0;
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].hasBeenRemoved)
                numKeys++;
        }
        System.out.println("\n" + numKeys + " keys");
        
        System.out.println("probe-length stats:");
        System.out.println("length\tcount");
        for (int i = 1; i <= table.length; i++) {
            if (probeLengthCount[i] != 0)
                System.out.println(i + "\t" + probeLengthCount[i]);
            numProbes += probeLengthCount[i];
            probeLengthSum += (probeLengthCount[i] * i);
        }
        System.out.println("average probe length = " +
                           (double)probeLengthSum / numProbes);
    }
}
