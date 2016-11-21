/*
 * PrintSeries.java
 *
 * Computer Science E-22, Harvard University
 *
 * A class that contains a recursive method for printing a
 * series of integers, separated by commas.
 *
 * The main method includes two examples of using this method.
 */

public class PrintSeries {
    public static void printSeries(int n1, int n2) {
        if (n1 == n2) {                 
            System.out.println(n2);
        } else {
            System.out.print(n1 + ", ");
            printSeries(n1 + 1, n2);
        }    
    }
    
    public static void main(String[] args) {
        printSeries(5, 8);
        printSeries(4, 4);
    }
}