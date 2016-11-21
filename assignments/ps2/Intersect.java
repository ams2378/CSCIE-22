
public class Intersect {
    
    /*
     * swap - swap the values of two variables.
     * Used by several of the sorting algorithms below.
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
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
     * intersect - find intersect of two arrayes:
     * { arr1 and arr2}
     */
    public static int[] intersect(int[] arr1, int[] arr2) {
        int newArrLength;
        if (arr1.length <= arr2.length)
            newArrLength = arr1.length;
        else
            newArrLength = arr2.length;
        
        // Create new array to find intersect
        int[] newArr = new int[newArrLength];
        
        // Sort both arrays
        quickSort(arr1);
        quickSort(arr2);
        
        //printArray(arr1);
        //printArray(arr2);
        
        int indexArr1=0;
        int indexArr2=0;
        int indexNewArr=0;
        
        while ( indexArr1 < arr1.length && indexArr2 < arr2.length) {
               if (arr1[indexArr1]==arr2[indexArr2]) {
                   newArr[indexNewArr] = arr1[indexArr1];
                   indexArr1++;
                   indexArr2++;
                   indexNewArr++;
               } else if (arr1[indexArr1] > arr2[indexArr2]) {
                   indexArr2++;
               } else { // if (arr1[indexArr1] < arr2[indexArr2])
                   indexArr1++;
               }
        }
        return newArr;
    }
    
    public static void main(String[] arr) { 

        int[] arr1 = {10, 4, 7, 7, 8, 5, 15, 298, 9, 13, 17};
        int[] arr2 = {17, 88, 7, 87, 84, 298, 298};
        
        int [] newArr = intersect(arr1, arr2);
        printArray(newArr);
    }
}