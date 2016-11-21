/*
 * ListTester.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/**
 * A program that tests two implementations of our simple List
 * interface.  It also demonstrates how easy it is to switch between
 * different implementations of the same interface.
 */
public class ListTester {
    public static void main(String[] args) {
        List myList = null;
        String commandChar, commandString;
        String item;
        int i, type;
        
        Scanner in = new Scanner(System.in);
        
        /* 
         * Create the list object, using the implementation selected
         * by the user. 
         */
        System.out.print("Which implementation? (1)ArrayList (2)LLList ");
        do {
            type = in.nextInt();
            in.nextLine();
            if (type == 1)
                myList = new ArrayList(10);
            else if (type == 2)
                myList = new LLList();
            else
                System.out.println("Please enter 1 or 2.");
        } while (type != 1 && type != 2);
        
        /* Perform user-specified operations on the list. */
        while(true) {
            // toString and isFull
            System.out.print(myList);
            if (myList.isFull())
                System.out.print(" (full)");
            System.out.println();
            System.out.println();
            
            commandString = "";
            do {
                System.out.print("[Get pos | Insert item pos | Remove pos |" + 
                                 " Length | Quit]: ");
                commandString = in.nextLine();
            } while (commandString.length() == 0); 
            
            Scanner command = new Scanner(commandString);
            commandChar = command.next();
            
            try {
                if (commandChar.equalsIgnoreCase("q"))
                    System.exit(1);
                else if (commandChar.equalsIgnoreCase("g")) {
                    // getItem (and length)
                    if (myList.length() == 0)
                        System.out.println("The list is empty.");
                    else {
                        i = command.nextInt();
                        item = (String)myList.getItem(i);
                        System.out.println("Item " + i + " = " + item);
                    }
                } else if (commandChar.equalsIgnoreCase("i")) {
                    item = command.next();
                    i = command.nextInt();
                    if (!myList.addItem(item, i))
                        System.out.println("The list is full.");
                } else if (commandChar.equalsIgnoreCase("r")) {
                    // removeItem (and length)
                    if (myList.length() == 0)
                        System.out.println("The list is empty.");
                    else {
                        i = command.nextInt();
                        item = (String)myList.removeItem(i);
                        System.out.println("Removed: " + item);
                    }
                } else if (commandChar.equalsIgnoreCase("l")) {
                    // length
                    System.out.println("The list is " + myList.length() +
                                       " items long."); 
                } else {
                    System.out.println("Invalid command: " + commandString);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index: " + commandString);
            } catch (NoSuchElementException e) {
                System.out.println("Invalid command: " + commandString);
            }
        }
    }
}
