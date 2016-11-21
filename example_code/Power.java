/* 
 * Power.java
 * 
 * Computer Science E-22, Harvard University
 */

import java.math.BigInteger;
import java.util.*;

/**
 * Demonstrates how recursion can be used in two different ways
 * to raise an integer to a power.
 */
public class Power {
    /* 
     * A class variable that allows us to keep track of the number
     * of method calls.
     */
    private static int numCalls;
    
    /**
     * power1 - a less efficient approach for recursively computing
     * x^n.  It basically does the same thing that an iterative approach
     * would do -- multiplying n factors of x -- but it does so using
     * recursion.
     */
    public static int power1(int x, int n) {
        numCalls++;   // keep track of how many method calls we make
        
        if (n < 0)
            throw new IllegalArgumentException("n must be >= 0");
        if (n == 0)
            return 1;
        else
            return x * power1(x, n-1);
    }
    
    /**
     * power2 - a more efficient method for recursively computing
     * x^n.  Takes a divide-and-conquer approach -- recursively
     * solving x^(n/2) and then returning either its square or 
     * x times its square.
     */
    public static int power2(int x, int n) {
        numCalls++;   // keep track of how many method calls we make
        
        if (n < 0)
            throw new IllegalArgumentException("n must be >= 0");
        if (n == 0)
            return 1;
        
        // Recursively compute x to the (n/2) power.
        int temp = power2(x, n/2);
        
        // Test to see if n is even.  If it is, we return temp^2.
        // Otherwise, we return x * temp^2.
        if ((n % 2) == 0)  
            return temp * temp; 
        else
            return x * temp * temp;
    }
    
    /**
     * power2Big - implements power2 using Java's BigInteger class, so that
     * we can handle large results.
     */
    public static BigInteger power2Big(BigInteger x, int n) {
        numCalls++;   // keep track of how many method calls we make
        
        if (n < 0)
            throw new IllegalArgumentException("n must be >= 0");
        if (n == 0)
            return BigInteger.ONE;
        
        // Recursively compute x to the (n/2) power.
        BigInteger temp = power2Big(x, n/2);
        
        // Test to see if n is even.  If it is, we return temp^2.
        // Otherwise, we return x * temp^2.
        if ((n % 2) == 0)  
            return temp.multiply(temp); 
        else
            return x.multiply(temp.multiply(temp));
    }
    
    public static void main(String[] args) {
        int x, n;
        int result;
        
        Scanner in = new Scanner(System.in);
        
        do {
            System.out.print("Enter the base x: ");
            x = in.nextInt();
            in.nextLine();
            System.out.print("Enter the exponent n (-1 to quit): ");
            n = in.nextInt();
            in.nextLine();
            
            if (n >= 0) {
                numCalls = 0;
                System.out.print("power1 gives " + power1(x, n));
                System.out.println(" (" + numCalls + " method calls)");
                
                numCalls = 0;
                System.out.print("power2 gives " + power2(x, n));
                System.out.println(" (" + numCalls + " method calls)");
                
                numCalls = 0;
                System.out.print("power2Big gives " + 
                                 power2Big(new BigInteger(Integer.toString(x)), n));
                System.out.println(" (" + numCalls + " method calls)");
                
                System.out.println();
            }
        } while (n != -1);
    }
}
