/*
 * Lagrange.java
 *
 * Computer Science E-22
 */

import java.util.*;

/**
 * An application that reads positive integers from the user and
 * breaks them into sums of at most four positive perfect squares.
 */
public class Lagrange {
    private int number;      // the number we are trying to break up
    
    /* Put any additional fields below. */
    private int[] term;
    private boolean[] termValid;

    /*
     * largestSquare - a private helper method that returns the 
     * largest perfect square less than or equal to the specified 
     * positive integer n.
     */
    private static int largestSquare(int n) {
        int sqrt = (int)Math.sqrt(n);
        return sqrt * sqrt;
    }

    /**
     * constructor
     */
    public Lagrange(int num) {
        number = num;
        // initialize the field(s) that you add below
        term = new int[4];
        termValid = new boolean[4];
    }

    /**
     * printSolution - print the solution to the problem
     */
    public void printSolution() {
        System.out.print(number + " = ");

        // Complete this method below.
        for (int i=0; i<4; i++) {        
            if (termValid[i]) 
                System.out.print(term[i]+ " ");
        }
    }
    
    /**
     * findSum method - a "wrapper" method that makes the initial
     * call to the key recursive-backtracking method (the findSumRB 
     * method found below), and that returns the same value that it returns.
     * 
     * NOTE: If you choose to modify the signature of the findSumRB method
     * or the meaning of its parameters, you should also change the call 
     * made by this method. Otherwise, no changes are needed.
     */
    public boolean findSum(int num) {
        return findSumRB(num, 0, 4);
    }
       
    /**
     * findSumRB method - the key recursive-backtracking method.
     * We call it to break the specified number (num) into a sum 
     * of at most maxTerms perfect squares.  
     * Returns true if the solution has been found and false otherwise.
     * 
     * NOTE: If you choose to modify the signature of this method,
     * you must also change the code in the previous method that
     * calls this method.
     */
    public boolean findSumRB(int num, int minTerms, int maxTerms) {
        
        // Solution found- return true
        if (term[0]+term[1]+term[2]+term[3]==number) {
            return true;
        } else if ((num != 1 && num%2 == 1 && maxTerms==0) || minTerms > 3) {
            return false;
        } else if (maxTerms==2) {
            if (largestSquare(num) == 1 && num > 2)
                return false;
        }
        
        int nextPerfSq = num;
        boolean perfSqNotFound = false;
                    
        do {
            int perfSq = largestSquare(nextPerfSq);
            
            applyValue(perfSq, minTerms);     
                
            if (findSumRB (num-perfSq, minTerms+1, maxTerms-1))
                return true;

            removeValue(minTerms);
                           
            if (perfSq==1)
                perfSqNotFound = false;
            else 
                perfSqNotFound = true;
            
            nextPerfSq = perfSq-1;
        } while (perfSqNotFound);
        
        return false;
    }
    
    /* Put any additional methods below. */

    public void applyValue(int perfSq, int index) {
        term[index] = perfSq;
        termValid[index] = true;
    }
    
    public void removeValue(int index) {
        term[index] = 0;
        termValid[index] = false;
    }
    
    /** YOU SHOULD NOT CHANGE THIS METHOD. **/
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a positive integer (-1 to quit): ");
            int n = console.nextInt();
            console.nextLine();
            
            if (n == -1) {
                System.out.println("Goodbye!");
                return;
            } else if (n <= 0)
                continue;
    
            Lagrange problem = new Lagrange(n);
            
            if (problem.findSum(n)) {
                problem.printSolution();
            } else {
                System.out.println("could not find a sum for " + n);
                System.out.println();
            }
            System.out.println();
        }
    }
}
