/*
 * StackTester.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/**
 * A program that tests two implementations of our Stack interface.
 * It also demonstrates how easy it is to switch between different
 * implementations of the same interface.
 */
public class StackTester {
    public static void main(String[] args) {
        Stack<String> myStack = null;
        String commandChar, commandString, item;
        int type;
        
        Scanner in = new Scanner(System.in);
        
        /* 
         * Create the stack object, using the implementation selected
         * by the user. 
         */
        System.out.print("Which implementation? (1)ArrayStack (2)LLStack ");
        do {
            type = in.nextInt();
            if (type == 1)
                myStack = new ArrayStack<String>(10);
            else if (type == 2)
                myStack = new LLStack<String>();
            else
                System.out.println("Please enter 1 or 2.");
        } while (type != 1 && type != 2);
        
        /* Perform user-specified operations on the stack. */
        while(true) {
            // toString and isFull
            System.out.print(myStack);
            if (myStack.isFull())
                System.out.print(" (full)");
            System.out.println();
            System.out.println();
            
            commandString = "";
            do {
                System.out.print("[pUsh string | pOp | pEek | Quit] : ");
                commandString = in.nextLine();
            } while (commandString.length() == 0); 
            
            Scanner command = new Scanner(commandString);
            commandChar = command.next();
            
            try {
                if (commandChar.equals("q"))
                    System.exit(1);
                else if (commandChar.equalsIgnoreCase("u")) {
                    item = command.next();
                    if (!myStack.push(item))
                        System.out.println("Stack is full.");
                } else if (commandChar.equalsIgnoreCase("o")) {
                    if (myStack.isEmpty())
                        System.out.println("Stack is empty.");
                    else {
                        item = myStack.pop();
                        System.out.println("Popped: " + item);
                    }
                } else if (commandChar.equalsIgnoreCase("e")) {
                    if (myStack.isEmpty())
                        System.out.println("Stack is empty.");
                    else {
                        item = myStack.peek();
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
