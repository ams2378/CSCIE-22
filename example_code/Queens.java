/*
 * Queens.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/** 
 * Queens - solves the n-queens puzzle using recursive backtracking 
 */
public class Queens {
    /* Constants used to layout the screen. */
    private static final int BASE_X = 20;
    private static final int BASE_Y = 10;
    private static final int ROW_HEIGHT = 2;
    private static final int COL_WIDTH = 6;
    
    private int boardSize;              // n, the dimension of the board
    private Scanner console;            // scanner for standard input stream
    
    private boolean[] colEmpty;         // keeps track of empty columns
    private boolean[] upDiagEmpty;      // keeps track of empty up-diagonals
    private boolean[] downDiagEmpty;    // keeps track of empty down-diagonals
    private boolean[][] queenOnSquare;  // occupied squares
    
    private int solutionTarget;         // # solns we want to find
    private int solutionsFound;         // # solns found thus far
    private int stepsToShow;            // # steps we want to display
    private int stepsTaken;             // # steps taken thus far
    
    /** 
     * Default constructor - for an 8x8 chess board, with a target of
     * 10 solutions and 100 steps shown.
     */
    public Queens(Scanner console) {
        this(8, 10, 100, console);         // calls the other constructor
    }
    
    /** 
     * Main constructor - initializes the state of a chess board
     * with the specified boardSize
     */
    public Queens(int boardSize, int solutionTarget, int stepsToShow, Scanner console) {
        this.boardSize = boardSize;
        this.solutionTarget = solutionTarget;
        this.stepsToShow = stepsToShow;
        this.console = console;
        
        solutionsFound = 0;
        stepsTaken = 0;
        
        colEmpty = new boolean[boardSize];
        upDiagEmpty = new boolean[2 * boardSize - 1];
        downDiagEmpty = new boolean[2 * boardSize - 1];
        queenOnSquare = new boolean[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                colEmpty[col] = true;
                upDiagEmpty[row + col] = true;
                downDiagEmpty[(boardSize - 1) + row - col] = true;
                queenOnSquare[row][col] = false;
            }
        }
    }
    
    /*
     * placeQueen - place a queen at the specified row and column
     */
    public void placeQueen(int row, int col) {
        queenOnSquare[row][col] = true;
        colEmpty[col] = false;
        upDiagEmpty[row + col] = false;
        downDiagEmpty[(boardSize - 1) + row - col] = false;
        
        if (stepsTaken < stepsToShow)
            displayBoard();
        stepsTaken++;
    }
    
    /*
     * removeQueen - remove the queen at the specified row and column
     */
    public void removeQueen(int row, int col) {
        queenOnSquare[row][col] = false;
        colEmpty[col] = true;
        upDiagEmpty[row + col] = true;
        downDiagEmpty[(boardSize - 1) + row - col] = true;
        
        if (stepsTaken < stepsToShow)
            displayBoard();
        stepsTaken++;
    }
    
    /*
     * isSafe - returns true if it is safe to place a queen at the
     * specified square, and false otherwise.
     */
    public boolean isSafe(int row, int col) {
        return (colEmpty[col] && 
                upDiagEmpty[row + col]&& 
                downDiagEmpty[(boardSize - 1) + row - col]); 
    }
    
    /*
     * findSafeColumn - recursive function to search for solutions.
     * Tries to find a safe column in the specified row.
     * If it finds one, it makes a recursive call to handle the next row.
     * If it can't find one, it backtracks by returning from the
     * recursive call.
     */
    public void findSafeColumn(int row) {
        if (row == boardSize) {  // base case: a solution!
            solutionsFound++;
            displayBoard();
            if (solutionsFound >= solutionTarget)
                System.exit(0);
            return;
        } 
        
        // Try each column in the current row.
        for (int col = 0; col < boardSize; col++) {
            if (isSafe(row, col)) {
                placeQueen(row, col);
                
                // Move onto the next row.
                findSafeColumn(row + 1);
                
                // If we get here, we've backtracked.
                removeQueen(row, col);
            }
        }
    }         
    
    /**
     * displayBoard - display the current state of the board and wait
     * for the user to hit ENTER.
     */
    public void displayBoard() {
        System.out.println("SOLUTIONS FOUND: " + solutionsFound);
        System.out.println();
        
        for (int row = -1; row < boardSize; row++) {
            for (int col = -1; col < boardSize; col++) {
                if (row == -1 && col >= 0) {
                    if (col < 10)
                        System.out.print(" ");
                    System.out.print(col);
                } else if (col == -1 && row >= 0) {
                    if (row < 10)
                        System.out.print(" ");
                    System.out.print(row);
                } else if (row >= 0 && col >= 0 && queenOnSquare[row][col]) {
                    System.out.print(" Q");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("Press [ENTER] to continue or enter Q to quit.");
        String line = console.nextLine();
        if (line.equalsIgnoreCase("Q")) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        
        System.out.print("What is the dimension of the board? ");
        int dim = console.nextInt();
        console.nextLine();
        
        System.out.print("How many solutions should be found? ");
        int numSolns = console.nextInt();
        console.nextLine();
        
        System.out.print("How many steps should be shown? ");
        int numSteps = console.nextInt();
        console.nextLine();
        
        Queens q = new Queens(dim, numSolns, numSteps, console);
        q.findSafeColumn(0);    // start with row 0
    }
}
