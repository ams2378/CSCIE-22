/*
 * WordIndex.java
 *
 * An application that uses a hash table to index words in a text document.
 *
 * Computer Science E-22, Harvard University
 */

import java.io.*;
import java.util.*;

public class WordIndex {
    /**
     * createIndex - returns a reference to an initialized WordIndex
     * object, based on user-specified parameters
     */
    public static HashTable createIndex() {
        Scanner in = new Scanner(System.in);
        
        System.out.print("What size hash table? ");
        int tableSize = in.nextInt();
        in.nextLine();
        
        System.out.print("What type of probing: (L)inear, (Q)uadratic, (D)ouble? ");
        int probeType;
        String choice = in.nextLine();
        switch (choice.charAt(0)) {
            case 'd':
            case 'D':
                probeType = HashTable.DOUBLE_HASHING;
                break;
            case 'q':
            case 'Q':
                probeType = HashTable.QUADRATIC;
                break;
            case 'l': 
            case 'L':
            default:
                probeType = HashTable.LINEAR;
        }
        
        return new HashTable(tableSize, probeType);    
    }
    
    /**
     * populate - populate the index with the (word, line-number)
     * obtained by processing the specified text file
     */
    public static void populate(HashTable index, String fname) {
        try {
            // This Scanner will scan the file, one line at a time.
            Scanner file = new Scanner(new File(fname));
            
            int lineNum = 1;
            while (file.hasNextLine()) {
                // This Scanner will scan the line, one word at a time.
                Scanner line = new Scanner(file.nextLine()).useDelimiter("[,!. ]+");
                while (line.hasNext()) {
                    String word = line.next();
                    index.insert(word, lineNum);
                }
                lineNum++;
            }
        } catch (IOException e) {
            System.out.println("Error accessing " + fname);
            System.exit(1);
        }
    }
    
    public static void main(String[] args) {
        System.out.println();
        HashTable index = createIndex();
        
        Scanner in = new Scanner(System.in);
        System.out.print("Name of file to process: ");
        String fname = in.nextLine();
        
        try {
            populate(index, fname);
        } catch (RuntimeException e) {
            System.out.println(e);
            index.printStats();
            System.exit(1);
        }
        index.printStats();
        
        while (true) {
            String commandString = "";
            do {
                System.out.print("\n[(S)earch word | (R)emove word | " +
                                 "(Q)uit] : ");
                commandString = in.nextLine();
            } while (commandString.length() == 0); 
            
            Scanner command = new Scanner(commandString);
            String commandChar = command.next();
            
            try {
                if (commandChar.equalsIgnoreCase("q"))
                    return;
                else if (commandChar.equalsIgnoreCase("s")) {
                    // search
                    String word = command.next();
                    LLList lineNums = index.search(word);
                    if (lineNums == null)
                        System.out.println(word + " not found");
                    else
                        System.out.println(lineNums);
                } else if (commandChar.equalsIgnoreCase("r")) {
                    // remove
                    String word = command.next();
                    index.remove(word);
                } else
                    System.out.println("Invalid command: " + commandString);
            } catch (NoSuchElementException e) {
                System.out.println("Invalid command: " + commandString);
            } catch (RuntimeException e) {
                System.out.println(e);
                System.exit(1);
            }
        }
    }
}
