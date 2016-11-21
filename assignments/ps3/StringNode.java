/*
 * StringNode.java
 *
 * Author:          Computer Science E-22
 * Modified by:     Adil Sadik, sadik.adil@gmail.com
 */

import java.io.*;
import java.util.*;

/**
 * A class for representing a string using a linked list.  Each
 * character of the string is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The string as a
 * whole is represented by storing a reference to the first node in
 * the linked list.  The methods in this class are static methods that
 * take a reference to a string linked-list as a parameter.  This
 * approach allows us to use recursion to write many of the methods.
 */
public class StringNode {
    private char ch;
    private StringNode next;

    /**
     * Constructor
     */
    public StringNode(char c, StringNode n) {
        ch = c;
        next = n;
    }

    /**
     * getNode - private helper method that returns a reference to
     * node i in the given linked-list string.  If the string is too
     * short, returns null.
     */
    private static StringNode getNode(StringNode str, int i) {
        if (i < 0 || str == null)
            return null;
                
        while (i != 0) {
            str = str.next;
            i--;
        }
        
        return str;
        
    }

    /*****************************************************
     * Public methods (in alphabetical order)
     *****************************************************/

    /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(StringNode str, int i) {
        if (str == null)
            throw new IllegalArgumentException("the string is empty");
        
        StringNode node = getNode(str, i);

        if (node != null) 
            return node.ch;     
        else
            throw new IllegalArgumentException("invalid index: " + i);
    }

    /**
     * concat - returns the concatenation of two linked-list strings
     */
    public static StringNode concat(StringNode str1, StringNode str2) {
        StringNode cat, trav, copy1, copy2, prevNode;

        if (str1 == null) {
            cat = copy(str2);
            return cat;
        }
       
        if (str2 == null) {
            cat = copy(str1);
            return cat;
        }
        
        copy1 = copy(str1);
        copy2 = copy(str2);
        
        trav = copy1;
        prevNode = trav;
        while (trav != null) {
            prevNode = trav;
            trav = trav.next;
        }
        //System.out.println("trav.ch " + prevNode.ch);
        prevNode.next = copy2;
        
        return copy1;
        
    }

    /**
     * convert - converts a standard Java String object to a linked-list
     * string and returns a reference to the linked-list string
     */
    public static StringNode convert(String s) {
        if (s == null || s.length() == 0)
            return null;

        StringNode firstNode = new StringNode(s.charAt(0), null);
        StringNode prevNode = firstNode;
        StringNode nextNode;

        for (int i = 1; i < s.length(); i++) {
            nextNode = new StringNode(s.charAt(i), null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }

        return firstNode;
    }
    
    /**
     * copy - returns a copy of the given linked-list string
     */
    public static StringNode copy(StringNode str) {
        if (str == null)
            return null;
        
        StringNode prevNode = null;
        StringNode trav = str;
        boolean firstNode = true;
        StringNode firstNodeStr = null;
        
        while (trav != null) {
            if (firstNode==true) {
                StringNode copyFirst = new StringNode(trav.ch, null);
                firstNodeStr = copyFirst;
                firstNode = false;
                prevNode = copyFirst;
            } else {
                StringNode copyFirst = new StringNode(trav.ch, null);
                prevNode.next = copyFirst;
                prevNode = copyFirst;
            }
            trav = trav.next;
        }
        return firstNodeStr;
    }

    /**
     * deleteChar - deletes character i in the given linked-list string and
     * returns a reference to the resulting linked-list string
     */
    public static StringNode deleteChar(StringNode str, int i) {
        if (str == null)
            throw new IllegalArgumentException("string is empty");
        else if (i < 0) 
            throw new IllegalArgumentException("invalid index: " + i);
        else if (i == 0) 
            str = str.next;
        else {
            StringNode prevNode = getNode(str, i-1);
            if (prevNode != null && prevNode.next != null) 
                prevNode.next = prevNode.next.next;
            else
                throw new IllegalArgumentException("invalid index: " + i);
        }

        return str;
    }

    /**
     * indexOf - returns the position of the first occurrence of
     * character ch in the given linked-list string.  If there is
     * none, returns -1.
     */
    public static int indexOf(StringNode str, char ch) {
       
        char c;
        int index=0;
       
        while (str!=null) {
            if (ch == str.ch) {
                return index;
            } else {
                str = str.next;
                index++;
            }
        }
        
        return -1;
        
    }

    /**
     * insertChar - inserts the character ch before the character
     * currently in position i of the specified linked-list string.
     * Returns a reference to the resulting linked-list string.
     */
    public static StringNode insertChar(StringNode str, int i, char ch) {
        StringNode newNode, prevNode;

        if (i < 0) 
            throw new IllegalArgumentException("invalid index: " + i);
        else if (i == 0) {
            newNode = new StringNode(ch, str);
            str = newNode;
        } else {
            prevNode = getNode(str, i-1);
            if (prevNode != null) {
                newNode = new StringNode(ch, prevNode.next);
                prevNode.next = newNode;
            } else 
                throw new IllegalArgumentException("invalid index: " + i);
        }

        return str;
    }

    /**
     * insertSorted - inserts character ch in the correct position
     * in a sorted list of characters (i.e., a sorted linked-list string)
     * and returns a reference to the resulting list.
     */
    public static StringNode insertSorted(StringNode str, char ch) {
        StringNode newNode, trail, trav;

        // Find where the character belongs.
        trail = null;
        trav = str;
        while (trav != null && trav.ch < ch) {
            trail = trav;
            trav = trav.next;
        }

        // Create and insert the new node.
        newNode = new StringNode(ch, trav);
        if (trail == null) {
            // We never advanced the prev and trav references, so
            // newNode goes at the start of the list.
            str = newNode;
        } else 
            trail.next = newNode;

        return str;
    }

    /**
     * length - recursively determines the number of characters in the
     * linked-list string to which str refers
     */
    public static int length(StringNode str) {
        if (str == null)
            return  0;
        
        int len=0;
        
        do {
            len++;
            str=str.next;
        } while (str != null);
        
        return len;
    }

    /**
     * numOccurrences - find the number of occurrences of the character
     * ch in the linked list to which str refers
     */
    public static int numOccurrences(StringNode str, char ch) {
        if (str == null)
            return 0;
     
        int numOccur = numOccurrences(str.next, ch);
        if (str.ch == ch)
            numOccur++;
        
        return numOccur;
    }

    /**
     * print - recursively writes the specified linked-list string to System.out
     */
    public static void print(StringNode str) {
        if (str == null)
            return;
        else {
            while (str != null) {
                System.out.print(str.ch);
                str=str.next; //print(str.next);
            }
        }
    }

    /**
     * read - reads a string from an input stream and returns a
     * reference to a linked list containing the characters in the string
     */
    public static StringNode read(InputStream in) throws IOException {
        StringNode str; 
        char ch = (char)in.read();

        if (ch == '\n')    // base case
            str = null;         
        else
            str = new StringNode(ch, read(in));
    
        return str;
    }
    
    /**
     * removeAllSpaces - removes all spaces from the linked-list string
     * to which str refers. Modifies the linked list itself, rather than
     * creating a new list. Returns a reference to the first node
     * in the modified linked list, because the first node will change
     * if the old first node contained a space.
     */
    public static StringNode removeAllSpaces(StringNode str) {
        if (str == null)
            return null;
                   
        StringNode trav = str;
        StringNode prevNode = str;
        boolean firstNode = true;
        
        while (trav != null) {
            //System.out.println("current letter : " + trav.ch); 
            if (trav.ch == ' ') {
                if (firstNode==true) {
                    str = trav.next;
                    firstNode = false;
                } else {
                    prevNode.next = trav.next;
                }
            }
            firstNode = false;
            prevNode = trav;
            trav = trav.next;
        }
        return str; //getNode(str,0);        
    }

    
    /**
     * replace - creates and returns a new linked-list string representing
     * the string in which all occurrences of the character oldChar in 
     * the string str are replaced by newChar. This method does *not* modify
     * the original list.
     */
    public static StringNode replace(StringNode str, char oldChar, char newChar) {
        if (str == null)
            return null;
               
        StringNode copyFirst, trav;
        copyFirst = copy(str);
        
        trav = copyFirst;
        while (trav != null) {
            if (trav.ch == oldChar)
                trav.ch = newChar;
            trav = trav.next;
        }
        
        return copyFirst;
    }
    
    /*
     * toString - creates and returns the Java string that
     * the current StringNode represents.  Note that this
     * method -- unlike the others -- is a non-static method.
     * Thus, it will not work for empty strings, since they
     * are represented by a value of null, and we can't use
     * null to invoke this method.
     */
    public String toString() {
        String str = "";
        StringNode trav = this;   // start trav on the current node
            
        while (trav != null) {
            str = str + trav.ch;
            trav = trav.next;
        }
         
        return str;
    }
    
    /**
     * toUpperCase - converts all of the characters in the specified
     * linked-list string to upper case.  Modifies the list itself,
     * rather than creating a new list.
     */
    public static void toUpperCase(StringNode str) {        
        StringNode trav = str; 
        while (trav != null) {
            trav.ch = Character.toUpperCase(trav.ch); 
            trav = trav.next;
        }
    } 
    
    /**
     * printEveryOther - prints every other char in str
     * Implemneted using recursion
     */
    public static void printEveryOther(StringNode str) {
        
        if (str == null) {
            System.out.println();
            return;
        } 
        
        char c = str.ch;
        System.out.print(c); 
        
        if (str.next != null) {
            printEveryOther(str.next.next);
        } else {
            System.out.println();
            return;
        }
    }
    
    
    /**
     * startsWith - returns true if the string "str" starts with 
     * "prefix"; otherwise returns false
     */
    public static boolean startsWith(StringNode str, StringNode prefix) {
    
        if (prefix == null)
            return true;
        
        if (str == null && prefix != null)
            return false;
        
        boolean match=false; 
        match = (prefix.ch == str.ch) && startsWith(str.next, prefix.next);
        
        return match;

    }
              
    public static void main(String[] args) throws IOException {
        StringNode copy, str, str1, str2, str3;
        String line;
        int n;
        char ch;

        StringNode str0 = StringNode.convert("method jlko");
        StringNode.printEveryOther(str0);
        
        StringNode str10 = convert("recursion");
        StringNode str20 = convert("recur");
        StringNode str30 = convert("recurse");
        
        if (startsWith(str10, str20))
            System.out.println("startsWith returned true");
        else
            System.out.println("startsWith returned false");
        
        if (startsWith(str10, str30))
            System.out.println("startsWith returned true");
        else
            System.out.println("startsWith returned false");
        
        // convert, print, and toUpperCase
        str = StringNode.convert("fine");
        System.out.print("Here's a string: "); 
        StringNode.print(str);
        System.out.println();
        System.out.print("Here it is in upper-case letters: "); 
        StringNode.toUpperCase(str);
        StringNode.print(str);
        System.out.println();
        System.out.println();

        Scanner in = new Scanner(System.in);
        
        // read, toString, and length.
        System.out.print("Type a string: ");
        String s = in.nextLine();
        str1 = StringNode.convert(s);
        System.out.print("Your string is: "); 
        System.out.println(str1);        // implicit toString call
        System.out.println("\nIts length is " + StringNode.length(str1) + 
            " characters.");

        // removeAllSpaces
        System.out.println("Here is the string with spaces removed: "); 
        str1 = StringNode.removeAllSpaces(str1);
        System.out.println(str1);

        // charAt
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to get (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        try {
            ch = StringNode.charAt(str1, n);
            System.out.println("That character is " + ch);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
    
        // indexOf
        System.out.print("\nWhat character to search for? ");
        line = in.nextLine();
        n = StringNode.indexOf(str1, line.charAt(0));
        if (n == -1)
            System.out.println("Not in the string.");
        else
            System.out.println("The index of that character is: " + n);

        // deleteChar and copy
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to delete (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        copy = StringNode.copy(str1);
        try {
            str1 = StringNode.deleteChar(str1, n);
            StringNode.print(str1);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        System.out.print("\nUnchanged copy: ");
        StringNode.print(copy);
        System.out.println();

        // insertChar
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to insert before (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        System.out.print("What character to insert? ");
        line = in.nextLine();
        try {
            str1 = StringNode.insertChar(str1, n, line.charAt(0));
            StringNode.print(str1);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }

        // replace
        System.out.print("Character to replace: ");
        char oldCh = in.next().charAt(0);
        in.nextLine();
        System.out.print("Character to put it its place: ");
        char newCh = in.next().charAt(0);
        in.nextLine();
        System.out.println("Resulting string: " + 
          StringNode.replace(str1, oldCh, newCh));
        System.out.println("Original string: " + str1);
       
        
        // concat
        System.out.print("\nType another string: ");
        s = in.nextLine();
        str2 = StringNode.convert(s);
        System.out.println("Its length is " + StringNode.length(str2) + 
            " characters.");
        System.out.print("Concatenation = ");
        StringNode.print(StringNode.concat(str1, str2));
        System.out.println();

        // insertSorted
        System.out.print("\nType a string of characters in alphabetical order: ");
        s = in.nextLine();
        str3 = StringNode.convert(s);
        System.out.print("What character to insert in order? ");
        line = in.nextLine();
        str3 = StringNode.insertSorted(str3, line.charAt(0));
        StringNode.print(str3);
        System.out.println();
    }
}