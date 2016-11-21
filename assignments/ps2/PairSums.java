
public class PairSums {
    
    public static final int NUM_ELEMENTS = 8;
    
    /*
     * swap - swap the values of two variables.
     * Used by several of the sorting algorithms below.
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
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
            else {
                //System.out.print("called partition " + i + " "); printArray(arr);
                return j;   // index of last element in the left subarray
            }
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
    
    /**
     * There are two nested for loops. if the array length is N then outer loop
     * will execute N times. The inner loop will execute N-1 times. So the runtime
     * is O(n^2)
     */ 
    public static void findPairs(int k, int[] arr) {
        for (int i=0; i<arr.length; i++) {
            for (int j=i+1; j<arr.length; j++) {
                if (arr[i]+arr[j]==k) {
                    System.out.println(arr[i] + " + " + arr[j] + " = " + k);
                }
            }
        }
    }
    
    /**
     * This method has two steps :
     * - step 1 : sort the array- here quicksort is used. So the complexity of this step is O(nlogn)
     * - step 2 : find pirs
     *   In this step an alternative approach is taken. There is only one while loop and we start walking the
     *   array using two indices- one starts from the start of array and another starts from the end of array.
     *   Both indices moves gradually towars the center. 
     *   So, the complexity of this step is O(n)
     * 
     *   Total complexity (step1 + step2) is O(nlogn) + O(n) ~ O(nlogn)
     */ 
    public static void findPairsFaster(int k, int[] arr) {
        // first sort the array- using quicksort as it's complexity is O(nlogn)
        quickSort(arr);
        //printArray(arr);
        
        int j = 0;
        int i = arr.length-1;
        
        while(j < i) {    
            if (arr[i]+arr[j] == k) {
                System.out.println(arr[i] + " + " + arr[j] + " = " + k);
                i--;
                j++;
            }
            
            if (arr[i]+arr[j] > k) {
                i--;
            } else if (arr[i]+arr[j] < k) {
                j++;
            }
        }
    }
    
    public static void main(String[] arr) { 
        int k=0;
        int[] orig = new int[NUM_ELEMENTS];
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            orig[i] = (int)(50 * Math.random());
        }
        
        int[] copy = new int[NUM_ELEMENTS];
        
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        
        int[] array = {10, 4, 7, 7, 8, 5, 15, 8};
        printArray(array);
        
        System.out.println("using findPairs method");
        findPairs(16, array);
        
        System.out.println("using findPairsFaster method");
        findPairsFaster(9, array);
    }
}