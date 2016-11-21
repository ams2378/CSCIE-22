
import java.util.*;

public class problem1 {
    
    public static boolean isSorrted (int[] arr) {
        if (arr==null) {
            throw new IllegalArgumentException("input arg is NULL");
        } else if (arr.length <= 1) {
            return true;
        }
        
        boolean arrayAscending = true;
        boolean arrayDescending = true;
        
        for (int i=0; i < arr.length; i++) {
            for (int j=i+1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    arrayAscending = false;
                }                
                
                if (arr[i] < arr[j]) {
                    arrayDescending = false;
                }           
            }
        }
        
        return (arrayAscending || arrayDescending);
    } 
    
    public static int mystery(int a, int b) {
        System.out.println("mystery called a "+ a + " b "+ b);
        if (a < 0) {
            return 1;
        } else {
            return 2 + mystery(a - b, b);
        }
    }
    
    /*
    public static boolean search(int item, int[] arr) {
        for (int i = 0; i< arr.length; i++) {
            if (arr[i] == item) {
                return true;
            }
        }

        return false;
    }
    */

    public static boolean search(Object item, Object[] arr) {
        for (int i = 0; i< arr.length; i++) {
            if (arr[i].equals(item)) {
                return true;
            }
        }

        return false;
    }
    
    
    public static boolean search(int item, int[] arr, int pos) {
        
        if (pos < 0) {
            throw new IllegalArgumentException("array index can't be negative");
        } else if (arr.length==0 || pos > (arr.length-1)) {
            return false;
        }
        
        boolean itemPresent = false;
        itemPresent = search (item, arr, pos+1);
        
        if (arr[pos]==item || itemPresent==true)
            return true;
        else 
            return false;
    }

    public static boolean search(Object item, Object[] arr, int pos) {
        
        if (pos < 0) {
            throw new IllegalArgumentException("array index can't be negative");
        } else if (arr.length==0 || pos > (arr.length-1)) {
            return false;
        }
        
        boolean itemPresent = false;
        itemPresent = search (item, arr, pos+1);
        
        if (arr[pos].equals(item) || itemPresent==true)
            return true;
        else 
            return false;
    }
    
    
    public static void printReverse(String str) {
        if (str==null || str=="")
            return;
        
        int length=str.length();
        
        if (length>1) {
            printReverse(str.substring(1,length)); //(length-1));
            System.out.print(str.charAt(0)); 
        } 
        
        if (length==1) {
            System.out.print(str.charAt(0)); 

            return;
        }
    }

    
    public static int mostFrequentValue(int[] arr) {
        
        int mostFrequentItem=0;
        int countOccurance=0;
        int prevCountOccurance=0;
        int item=0;
        int finalCount=0;
        
        System.out.println("array length "+ arr.length); 
        
        // if array length is 1 then return the first item
        if (arr.length==1)
            return arr[0];
        
        for (int i=1; i<arr.length; i++) {
            if (arr[i]==arr[i-1]) {
                countOccurance++;
                item=arr[i];
                if (i==arr.length-1)
                    finalCount=countOccurance;
                //System.out.println("countOccurance "+ countOccurance + " value " + item ); 
            } else if (countOccurance > 0) { 
                if (countOccurance > prevCountOccurance) {
                    mostFrequentItem=item;
                    prevCountOccurance=countOccurance;
                    //System.out.println("countOccurance "+ prevCountOccurance + " value " + item ); 
                }
                finalCount=countOccurance;
                countOccurance=0;
            }
        }
        
        if (finalCount==0)
            return arr[0];
        else if (countOccurance > prevCountOccurance)
            return arr[arr.length-1];
        else 
            return mostFrequentItem;
    }

    
    
    public static void exam() {
        int[] a = { 10, 17, 3, 15, 7, 2 };            
        int[] b = a;            
        int[] c = { 13, 5, 12, 6, 11, 9 };            
        b[2] = c[2];            
        b = c;            
        c[2] = a[2];            
        System.out.println(a[2] + " " + b[2]);
    }
    
    
    public static int sumCube(int n) {
        if (n <= 0)
            return 0;
        
        return n*n*n + sumCube(n-1);
    }
    
    public static String mystery2(int m, int n) {
        if (n <= 0)                  
            return "*";              
        else { 
            String temp = mystery2(m - 1, n - 1); // Reminder: the + below performs // string concatenation. 
            return temp + m;
        } 
    }

      public static int sum_cubes3(int n) {          
          if (n <= 2) { 
              int ret1 = n * n * (n + 1) * (n + 1) / 4; 
              System.out.println(ret1);
              return ret1;
          }          
          
          int temp = sum_cubes3(n - 2);    
          //System.out.println("n is " + n + " temp is " + temp);
          temp = (int)Math.sqrt(temp); 
          //System.out.println("n is " + n + " temp is " + temp);
          temp = temp + 2*n - 1;   
          //System.out.println("n is " + n + " temp is " + temp);
          int ret2 = temp*temp;
          System.out.println(ret2);
          return ret2;
          //return temp * temp; 
      }

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        System.out.println("Hello, World");
        
        printReverse("harvard");
        System.out.println();
        printReverse("Hello World");
        
        int[] a = {2, 4, 6, 8, 10, 12};
        int[] b = new int[6];
        int[] c = new int[6];

        int[] a1 = {2, 2, 2, 2, 2, 2};
        int[] a2 = {12, 10, 8, 7, 2, 1};
        int[] a3 = {2, 4, 16, 8, 10, 12};
        
        int[] a4 = new int[0];
        
        
        b = a;
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }

        a[2] = c[5];
        c[2]++;
        //System.out.println(a[3] + " " + b[3] + " " + c[3]);      
        
        //boolean res = isSorrted(a3);
        
        //System.out.println(res); 
        //System.out.println("length of array a "+ a4.length); 
        //System.out.println(a4[3]); 
        
        //int mysteryRes = mystery(20,6);
        
        //System.out.println("Mystery result "+ mysteryRes); 
        
        boolean itemPresent = search (4, a, 5);
        
        //System.out.println("item present ? "+ itemPresent); 
        
        //int[] arr = {1, 1, 1, 1, 2, 3, 3, 8, 8, 11, 11, 14, 19, 19, 20, 20, 20};
        Integer[] arr = {1,4,4,4,6,7,7,11,11,11,11,11};
        
        if (search((Integer)(11), arr)) {
            System.out.println("item found"); 
        } else {
            System.out.println("item not found");
        }
        exam();
        mystery2(5,3);
        System.out.println(mystery2(5,3));
        int res = sumCube(4);
        System.out.println(res);
        
        sum_cubes3(5);
        
        //System.out.println("MostFrequentItem "+ mostFrequentValue(arr)); 
    }
}