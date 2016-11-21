/*
 * HeapSort.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * HeapSort - a class containing an implementation of the heapsort
 * algorithm.  
 */
public class HeapSort {
    public static final int NUM_ELEMENTS = 10;
    
    /** heapSort */
    public static <T extends Comparable<T>> void heapSort (T[] arr) {
        // Turn the array into a max-at-top heap.
        // Note that this does *not* create an extra copy of the array.
        // The heap object simply holds a reference to the original array, 
        // with the elements rearranged as needed.
        Heap<T> heap = new Heap<T>(arr);
        
        // Repeatedly put elements in their correct positions.
        int endUnsorted = arr.length - 1;
        while (endUnsorted > 0) {
            //
            // Get the largest remaining element and put it where it
            // belongs -- at the end of the portion of the array that
            // is still unsorted.
            //
            T largestRemaining = heap.remove();
            arr[endUnsorted] = largestRemaining;
            
            endUnsorted--;
        }
    }
    
    public static void printArray(Object[] arr) {
        System.out.print("{ ");
        
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        
        System.out.println("}");
    }
    
    public static void main(String[] args) { 
        Integer[] arr = new Integer[NUM_ELEMENTS];
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            arr[i] = (int)(50 * Math.random());
        }
        printArray(arr);
        
        heapSort(arr);
        System.out.print("heap sort:\t");
        printArray(arr);
    }
}
