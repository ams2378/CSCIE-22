/*
 * SortGeneric.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * SortGeneric - a class containing implementations of various
 * array-sorting algorithms.  Unlike the Sort class, this class uses
 * generic methods so that we can ensure that the arrays contain
 * elements of the same type. Each method takes an array of objects of
 * a class that implements the Comparable<T> interface.  The methods
 * assume that the array is full.  They sort the array in place,
 * altering the original array.
 */
public class SortGeneric {
    public static final int NUM_ELEMENTS = 10;
    
    /*
     * swap - swap the values of two variables.
     * Used by several of the sorting algorithms below.
     */
    private static void swap(Object[] arr, int a, int b) {
        Object temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    
    /*
     * indexSmallest - returns the index of the smallest element
     * in the subarray from arr[lower] to arr[upper].  Used by
     * selectionSort.
     */
    private static <T extends Comparable<T>> int
        indexSmallest(T[] arr, int lower, int upper)
    {
        int indexMin = lower;
        
        for (int i = lower+1; i <= upper; i++)
            if (arr[i].compareTo(arr[indexMin]) < 0)
            indexMin = i;
        
        return indexMin;
    }
    
    
    /** selectionSort */
    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int j = indexSmallest(arr, i, arr.length - 1);
            swap(arr, i, j);
        }
    }
    
    /** insertionSort */
    public static <T extends Comparable<T>> void insertionSort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i-1]) < 0) {
                // Save a copy of the element to be inserted.
                T toInsert = arr[i];
                
                // Shift right to make room for element.
                int j = i;
                do {
                    arr[j] = arr[j - 1];
                    j = j - 1;
                } while (j > 0 && toInsert.compareTo(arr[j-1]) < 0);
                
                // Put the element in place.
                arr[j] = toInsert;
            }
        }
    }
    
    /** shellSort */
    public static <T extends Comparable<T>> void shellSort(T[] arr) {
        /*
         * Find initial increment: one less than the largest
         * power of 2 that is <= the number of objects.
         */
        int incr = 1;
        while (2 * incr <= arr.length)
            incr = 2 * incr;
        incr = incr - 1;
        
        /* Do insertion sort for each increment. */
        while (incr >= 1) {
            for (int i = incr; i < arr.length; i++) {
                if (arr[i].compareTo(arr[i-incr]) < 0) {
                    T toInsert = arr[i];
                    
                    int j = i;
                    do {
                        arr[j] = arr[j - incr];
                        j = j - incr;
                    } while (j > incr-1 &&
                             toInsert.compareTo(arr[j-incr]) < 0);
                    
                    arr[j] = toInsert;
                }
            }
            
            // Calculate increment for next pass.
            incr = incr / 2;
        }
    }
    
    /** bubbleSort */
    public static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j].compareTo(arr[j+1]) > 0)
                    swap(arr, j, j+1);
            }
        }
    }
    
    /* partition - helper method for qSort */
    private static <T extends Comparable<T>>
        int partition(T[] arr, int first, int last) {
        T pivot = arr[(first + last)/2];
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left
        
        while (true) {
            do {
                i++;
            } while (arr[i].compareTo(pivot) < 0);
            do {
                j--;
            } while (arr[j].compareTo(pivot) > 0); 
            
            if (i < j)
                swap(arr, i, j);
            else
                return j;   // index of last element in the left subarray
        }                   
    }
    
    /* qSort - recursive method that does the work for quickSort */
    private static <T extends Comparable<T>>
        void qSort(T[] arr, int first, int last)
    {
        int split = partition(arr, first, last);
        
        if (first < split)
            qSort(arr, first, split);      // left subarray
        if (last > split + 1)
            qSort(arr, split + 1, last);   // right subarray
    }
    
    /** quicksort */
    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        qSort(arr, 0, arr.length - 1); 
    }
    
    /**
     * printArray - prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(Object[] arr) {
        System.out.print("{ ");
        
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        
        System.out.println("}");
    }
    
    public static void main(String[] arr) { 
        Integer[] orig = new Integer[NUM_ELEMENTS];
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            orig[i] = (int)(50 * Math.random());
        }
        printArray(orig);
        
        Integer[] copy = new Integer[NUM_ELEMENTS];
        
        /* selection sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        selectionSort(copy);
        System.out.print("selection sort:\t");
        printArray(copy);
        
        /* insertion sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        insertionSort(copy);
        System.out.print("insertion sort:\t");
        printArray(copy);
        
        /* Shell sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        shellSort(copy);
        System.out.print("Shell sort:\t");
        printArray(copy);
        
        /* bubble sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        bubbleSort(copy);
        System.out.print("bubble sort:\t");
        printArray(copy);
        
        /* quicksort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        quickSort(copy);
        System.out.print("quicksort:\t");
        printArray(copy);
    }
}
