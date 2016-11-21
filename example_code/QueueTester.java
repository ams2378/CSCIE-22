/**
 * QueueTester.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/**
 * A program that tests two implementations of our Queue interface.
 * It also demonstrates how easy it is to switch between different
 * implementations of the same interface.
 */
public class QueueTester {
    public static void main(String[] args) {
        Queue<String> myQueue = null;
        String commandChar, commandString, item;
        int type;
        
        Scanner in = new Scanner(System.in);
        
        /* 
         * Create the stack object, using the implementation selected
         * by the user. 
         */
        System.out.print("Which implementation? (1)ArrayQueue (2)LLQueue ");
        do {
            type = in.nextInt();
            in.nextLine();
            if (type == 1)
                myQueue = new ArrayQueue<String>(10);
            else if (type == 2)
                myQueue = new LLQueue<String>();
            else
                System.out.println("Please enter 1 or 2.");
        } while (type != 1 && type != 2);
        
        /* Perform user-specified operations on the stack. */
        while(true) {
            // toString and isFull
            System.out.print(myQueue);
            if (myQueue.isFull())
                System.out.print(" (full)");
            System.out.println();
            System.out.println();
            
            commandString = "";
            do {
                System.out.print("[Insert string | Remove | Peek | Quit] : ");
                commandString = in.nextLine();
            } while (commandString.length() == 0); 
            
            Scanner command = new Scanner(commandString);
            commandChar = command.next();
            
            try {
                if (commandChar.equalsIgnoreCase("q"))
                    System.exit(1);
                else if (commandChar.equalsIgnoreCase("i")) {
                    // insert
                    item = in.nextLine();
                    if (myQueue.insert(item) == false)
                        System.out.println("Queue is full.");
                } else if (commandChar.equalsIgnoreCase("r")) {
                    // remove (and isEmpty)
                    if (myQueue.isEmpty())
                        System.out.println("Queue is empty.");
                    else {
                        item = myQueue.remove();
                        System.out.println("Removed: " + item);
                    }
                } else if (commandChar.equalsIgnoreCase("p")) {
                    // peek (and isEmpty)
                    if (myQueue.isEmpty())
                        System.out.println("Queue is empty.");
                    else {
                        item = myQueue.peek();
                        System.out.println("Peeked at: " + item);
                    }
                } else
                    System.out.println("Invalid command: " + commandString);
            } catch (NoSuchElementException e) {
                System.out.println("Invalid command: " + commandString);
            }
        }
    }
}
