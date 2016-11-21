/*
 * SumIntegers.java
 *
 * Computer Science E-22, Harvard University
 *
 * A class that contains a recursive method for computing the sum of the
 * integers from 1 to n.
 *
 * The main method includes two examples of using this method.
 */

public class SumIntegers {
    public static int sum(int n) {
        // base case
        if (n == 0) {
            return 0;
        }

        // recursive case
        int total = n + sum(n - 1);    
        return total;   

        // Note: we could also have done this:
        //   return n + sum(n - 1);
    }
    
    public static void main(String[] args) {
        int firstSum = sum(3);
        System.out.println("sum(3) = " + firstSum);
        System.out.println("sum(10) = " + sum(10));
    }
}
