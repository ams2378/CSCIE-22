/*
 * SortCount.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/**
 * This class contains implementations of various array-sorting
 * algorithms.  All comparisons and moves are performed using helper
 * methods that maintain counts of these operations.  Each sort method
 * takes an array of objects of a class that implements the Comparable
 * interface.  The methods assume that all of the elements of the
 * array should be sorted.  The algorithms sort the array in place,
 * altering the original array.
 */
public class SortCountGeneric {
    /* 
     * the integers in the test arrays are drawn from the range 
     * 0, ..., MAX_VAL 
     */
    private static int MAX_VAL = 65536;
    
    private static long compares;     // total number of comparisons
    private static long moves;        // total number of moves
    
    /*
     * compare - a wrapper around the compareTo method that allows us
     * to count the number of comparisons that occur.
     */
    private static <T extends Comparable<T>> int compare(T el1, T el2) {
        compares++;
        return el1.compareTo(el2);
    }
    
    /*
     * move - moves an element of the specified array to a different
     * location in the array.  move(arr, dest, source) is equivalent
     * to arr[dest] = arr[source].  Using this method allows us to
     * count the number of moves that occur.
     */
    private static void move(Object[] arr, int dest, int source) {
        moves++;
        arr[dest] = arr[source];
    }
    
    /*
     * swap - swap the values of two variables.
     * Used by several of the sorting algorithms below.
     */
    private static void swap(Object[] arr, int a, int b) {
        Object temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        moves += 3;
    }
    
    /** 
     * randomArray - creates an array of randomly generated Integer
     * objects with the specified number of elements
     */
    public static Integer[] randomArray(int n) {
        Integer[] arr = new Integer[n];
        
        for (int i = 0; i < arr.length; i++) {
            int val = (int)(Math.random() * (MAX_VAL + 1));
            arr[i] = new Integer(val);
        }
        
        return arr;
    }
    
    /** 
     * almostSortedArray - creates an almost sorted array of Integer
     * objects with the specified number of elements
     */
    public static Integer[] almostSortedArray(int n) {
        /* Produce a random array and sort it. */
        Integer[] arr = randomArray(n);
        quickSort(arr);
        
        /* 
         * Move one quarter of the elements out of place by between 1
         * and 5 places.
         */
        for (int i = 0; i < n/8; i++) {
            int j = (int)(Math.random() * n);
            int displace = -5 + (int)(Math.random() * 11);
            int k = j + displace;
            if (k < 0)
                k = 0;
            if (k > n - 1)
                k = n - 1;
            
            swap(arr, j, k);
        }
        
        return arr;
    }
    
    /**
     * Sets the counts of moves and comparisons to 0.
     */
    public static void initStats() {
        compares = 0;
        moves = 0;
    }
    
    /**
     * Prints the current counts of moves and comparisons.
     */
    public static void printStats() {
        int spaces = (int)(Math.log(compares)/Math.log(10));
        for (int i = 0; i < (10 - spaces); i++)
            System.out.print(" ");
        System.out.print(compares + " comparisons\t");
        
        spaces = (int)(Math.log(moves)/Math.log(10));
        for (int i = 0; i < (10 - spaces); i++)
            System.out.print(" ");
        System.out.println(moves + " moves");
    }
    
    /*
     * indexSmallest - returns the index of the smallest element
     * in the subarray from arr[lower] to arr[upper].  Used by
     * selectionSort.
     */
    private static <T extends Comparable<T>>
        int indexSmallest(T[] arr, int lower, int upper)
    {
        int indexMin = lower;
        
        for (int i = lower + 1; i <= upper; i++)
            if (compare(arr[i], arr[indexMin]) < 0)
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
            if (compare(arr[i], arr[i-1]) < 0) {
                // Save a copy of the element to be inserted.
                T toInsert = arr[i];
                moves++;
                
                // Shift right to make room for element.
                int j = i;
                do {
                    move(arr, j, j - 1);
                    j = j - 1;
                } while (j > 0 && compare(toInsert, arr[j-1]) < 0);
                
                // Put the element in place.
                arr[j] = toInsert;
                moves++;
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
                if (compare(arr[i], arr[i-incr]) < 0) {
                    T toInsert = arr[i];
                    moves++;
                    
                    int j = i;
                    do {
                        move(arr, j, j-incr);
                        j = j - incr;
                    } while (j > incr-1 && compare(toInsert, arr[j-incr]) < 0);
                    
                    arr[j] = toInsert;
                    moves++;
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
                if (compare(arr[j], arr[j+1]) > 0)
                    swap(arr, j, j+1);
            }
        }
    }
    
    /*
     * A helper method for qSort that takes the array that begins with
     * element arr[first] and ends with element arr[last] and
     * partitions it into two subarrays using the middle element of
     * the array for the pivot.  It returns the index of the last
     * element of the left subarray formed by the partition.  All
     * elements in the left subarray are <= the pivot, and all
     * elements in the right subarray are >= the pivot.
     */
    private static <T extends Comparable<T>>
        int partition(T[] arr, int first, int last)
    {
        T pivot = arr[(first + last)/2];
        moves++;   // for the above assignment
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left
        
        while (true) {
            // Moving from left to right, find an element >= the pivot.
            do {
                i++;
            } while (compare(arr[i], pivot) < 0);
            
            // Moving from right to left, find an element <= the pivot.
            do {
                j--;
            } while (compare(arr[j], pivot) > 0); 
            
            // Swap the elements so that they end up in the correct
            // subarray, or quit if the indices have overlapped or crossed.
            if (i < j)
                swap(arr, i, j);
            else
                return j;   // index of last element in the left subarray
        }                   
    }
    
    /*
     * A recursive helper method that actually implements quicksort.
     * The initial recursive call is made by quicksort() -- see below.
     */
    private static <T extends Comparable<T>>
        void qSort(T[] arr, int first, int last)
    {
        // Partition the array.  split is the index of the last
        // element of the left subarray formed by the partition.
        int split = partition(arr, first, last);
        
        //
        // Note that we only make recursive calls on subarrays that
        // have two or more elements, and thus the base case is when
        // neither subarray has two or more elements.
        //
        if (first < split)
            qSort(arr, first, split);      // left subarray
        if (last > split + 1)
            qSort(arr, split + 1, last);   // right subarray
    }
    
    /** quickSort */
    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        qSort(arr, 0, arr.length - 1); 
    }
    
    /* merge - helper method for mergesort */
    private static <T extends Comparable<T>> void merge(T[] arr, T[] tmp, 
                                                        int leftStart, int leftEnd, int rightStart, int rightEnd)
    {
        int i = leftStart;    // index into left subarray
        int j = rightStart;   // index into right subarray
        int k = leftStart;    // index into tmp
        
        while (i <= leftEnd && j <= rightEnd) {
            if (compare(arr[i], arr[j]) < 0)
                tmp[k++] = arr[i++];
            else
                tmp[k++] = arr[j++];
            moves++;
        }
        
        while (i <= leftEnd) {
            tmp[k++] = arr[i++];
            moves++;
        }
        
        while (j <= rightEnd) {
            tmp[k++] = arr[j++];
            moves++;
        }
        
        for (i = leftStart; i <= rightEnd; i++) {
            arr[i] = tmp[i];
            moves++;
        }
    }
    
    /** mSort - recursive method for mergesort */
    private static <T extends Comparable<T>> void mSort(T[] arr, T[] tmp, 
                                                        int start, int end) 
    {
        if (start >= end)
            return;
        
        int middle = (start + end)/2;
        mSort(arr, tmp, start, middle);
        mSort(arr, tmp, middle + 1, end);
        merge(arr, tmp, start, middle, middle + 1, end);
    }
    
    /** mergesort */
    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        T[] tmp = (T[])new Comparable[arr.length];
        mSort(arr, tmp, 0, arr.length - 1);
    }
    
    /**
     * Prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(Object[] arr) {
        // Don't print it if it's more than 10 elements.
        if (arr.length > 10)
            return;
        
        System.out.print("{ ");
        
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        
        System.out.println("}");
    }
    
    public static void main(String args[]) {
        Integer[] a;       // the array
        Integer[] asave;   // a copy of the original unsorted array
        int numItems;
        String arrayType;
        
        /*
         * Get various parameters from the user.
         */
        Scanner in = new Scanner(System.in);
        System.out.print("How many items in the array? ");
        numItems = in.nextInt();
        in.nextLine();
        System.out.print("Random (r) or almost sorted (a)? ");
        arrayType = in.nextLine();
        System.out.println();
        
        /* 
         * Create the arrays. 
         */
        if (arrayType.equalsIgnoreCase("A"))
            a = almostSortedArray(numItems);
        else
            a = randomArray(numItems);
        asave = new Integer[numItems];
        System.arraycopy(a, 0, asave, 0, a.length);
        printArray(a);
        
        /*
         * Try each of the various algorithms, starting each time 
         * with a fresh copy of the initial array.
         */
        System.out.print("quickSort\t\t");
        System.arraycopy(asave, 0, a, 0, asave.length);
        initStats();
        quickSort(a);
        printStats();
        printArray(a);
        
        System.out.print("mergeSort\t\t");
        System.arraycopy(asave, 0, a, 0, asave.length);
        initStats();
        mergeSort(a);
        printStats();
        printArray(a);
        
        System.out.print("shellSort\t\t");
        System.arraycopy(asave, 0, a, 0, asave.length);
        initStats();
        shellSort(a);
        printStats();
        printArray(a);
        
        System.out.print("insertionSort\t\t");
        System.arraycopy(asave, 0, a, 0, asave.length);
        initStats();
        insertionSort(a);
        printStats();
        printArray(a);
        
        System.out.print("selectionSort\t\t");
        System.arraycopy(asave, 0, a, 0, asave.length);
        initStats();
        selectionSort(a);
        printStats();
        printArray(a);
        
        System.out.print("bubbleSort\t\t");
        System.arraycopy(asave, 0, a, 0, asave.length);
        initStats();
        bubbleSort(a);
        printStats();
        printArray(a);
    }
}
