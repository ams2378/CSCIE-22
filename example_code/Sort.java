/*
 * Sort.java
 *
 * Computer Science E-22, Harvard University
 *
 * ======IMPORTANT: Your solution to problem 9 should go 
 * in SortCount.java, rather than in this file.========
 * 
 * You should not need to modify this file.
 *
 * To call one of these methods from another class, precede its name
 * by the name of the class.  For example:
 *
 *     Sort.bubbleSort(arr);
 */

/**
 * Sort - a class containing implementations of various array-sorting
 * algorithms.  Each method takes an array of ints.  The methods
 * assume that the array is full.  They sort the array in place,
 * altering the original array.
 */
public class Sort {
    public static final int NUM_ELEMENTS = 10;

    /*
     * swap - swap the values of two variables.
     * Used by several of the sorting algorithms below.
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
     * indexSmallest - returns the index of the smallest element
     * in the subarray from arr[lower] to arr[upper].  Used by
     * selectionSort.
     */
    private static int indexSmallest(int[] arr, int lower, int upper) {
        int indexMin = lower;

        for (int i = lower+1; i <= upper; i++)
            if (arr[i] < arr[indexMin])
                indexMin = i;

        return indexMin;
    }

    /** selectionSort */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int j = indexSmallest(arr, i, arr.length - 1);
            swap(arr, i, j);
        }
    }

    /** insertionSort */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                // Save a copy of the element to be inserted.
                int toInsert = arr[i];

                // Shift right to make room for element.
                int j = i;
                do {
                    arr[j] = arr[j - 1];
                    j = j - 1;
                } while (j > 0 && toInsert < arr[j-1]);
                
                // Put the element in place.
                arr[j] = toInsert;
            }
        }
    }

    /** shellSort */
    public static void shellSort(int[] arr) {
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
                if (arr[i] < arr[i-incr]) {
                    int toInsert = arr[i];

                    int j = i;
                    do {
                        arr[j] = arr[j - incr];
                        j = j - incr;
                    } while (j > incr-1 &&
                      toInsert < arr[j-incr]);

                    arr[j] = toInsert;
                }
            }

            // Calculate increment for next pass.
            incr = incr / 2;
        }
    }

    /** bubbleSort */
    public static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1])
                    swap(arr, j, j+1);
            }
        }
    }

    /* partition - helper method for qSort */
    private static int partition(int[] arr, int first, int last) {
        int pivot = arr[(first + last)/2];
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left

        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);
            do {
                j--;
            } while (arr[j] > pivot); 

            if (i < j)
                swap(arr, i, j);
            else
                return j;   // index of last element in the left subarray
        }                   
    }

    /* qSort - recursive method that does the work for quickSort */
    private static void qSort(int[] arr, int first, int last) {
        int split = partition(arr, first, last);

        if (first < split)
            qSort(arr, first, split);      // left subarray
        if (last > split + 1)
            qSort(arr, split + 1, last);   // right subarray
    }

    /** quicksort */
    public static void quickSort(int[] arr) {
        qSort(arr, 0, arr.length - 1); 
    }

    /* merge - helper method for mergesort */
    private static void merge(int[] arr, int[] tmp, 
      int leftStart, int leftEnd, int rightStart, int rightEnd)
    {
        int i = leftStart;    // index into left subarray
        int j = rightStart;   // index into right subarray
        int k = leftStart;    // index into tmp
        
        while (i <= leftEnd && j <= rightEnd) {
            if (arr[i] < arr[j])
                tmp[k++] = arr[i++];
            else
                tmp[k++] = arr[j++];
        }
        
        while (i <= leftEnd)
            tmp[k++] = arr[i++];
        
        while (j <= rightEnd)
            tmp[k++] = arr[j++];
        
        for (i = leftStart; i <= rightEnd; i++)
            arr[i] = tmp[i];
    }
    
    /** mSort - recursive method for mergesort */
    private static void mSort(int[] arr, int[] tmp, int start, int end) {
        if (start >= end)
            return;
        
        int middle = (start + end)/2;
        mSort(arr, tmp, start, middle);
        mSort(arr, tmp, middle + 1, end);
        merge(arr, tmp, start, middle, middle + 1, end);
    }
    
    /** mergesort */
    public static void mergeSort(int[] arr) {
        int[] tmp = new int[arr.length];
        mSort(arr, tmp, 0, arr.length - 1);
    }

    /**
     * printArray - prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(int[] arr) {
        System.out.print("{ ");

        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");

        System.out.println("}");
    }

    public static void main(String[] arr) { 
        int[] orig = new int[NUM_ELEMENTS];
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            orig[i] = (int)(50 * Math.random());
        }
        printArray(orig);

        int[] copy = new int[NUM_ELEMENTS];

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

        /* mergesort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        mergeSort(copy);
        System.out.print("mergesort:\t");
        printArray(copy);
    }
}
