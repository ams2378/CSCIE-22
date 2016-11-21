/*
 * EightPuzzle.java
 *
 * Computer Science E-22, Harvard University
 */

import java.util.*;

/**
 * A class that is used to solve Eight-Puzzle problems using one of
 * several state-space search algorithms.  It extends the abstract
 * SearchProblem class, which includes functionality that is common to
 * all state-space search problems.  This subclass implements the
 * abstract methods from the SearchProblem class with code that is
 * specific to the Eight Puzzle.
 */
public class EightPuzzle extends SearchProblem {
    private Board initBoard;
    
    public EightPuzzle(String initBoardString) {
        initBoard = new Board(initBoardString);
    }
    
    public SearchNode makeFirstNode() {
        return new SearchNode(initBoard, null, null, 0, 0,
                              initBoard.stepsToGoal());
    }
    
    public int numOperators() {
        return 4;
    }
    
    public SearchNode applyOperator(int i, SearchNode node) {
        Board currentBoard = (Board)node.getState();
        Board newBoard = new Board(currentBoard);
        
        boolean movePossible = newBoard.moveBlank(i);
        if (movePossible) 
            return new SearchNode(newBoard, node, Board.moveString[i], 
                                  node.getDepth() + 1, node.getCostFromStart() + 1,
                                  newBoard.stepsToGoal());
        else
            return null;
    }
    
    public boolean isGoal(SearchNode node) {
        Board board = (Board)node.getState();
        
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            if (board.getTile(i / Board.DIM, i % Board.DIM) != i)
                return false;
        }
        
        return true;
    }
    
    public void printSolution(SearchNode node) {
        if (node == null)
            return;
        
        SearchNode predecessor = node.getPredecessor();
        printSolution(predecessor);
        
        if (predecessor == null)
            System.out.println("\n  initial board:");
        else
            System.out.println("\n  move blank " + node.getOperator() + ":");
        System.out.println((Board)node.getState());
    }
    
    public static void main(String[] args) {
        String[] searcherNames = {"BREADTH-FIRST", "DEPTH-FIRST",
            "ITERATIVE DEEPENING", "GREEDY", "A*"};
        Scanner in = new Scanner(System.in);
        
        System.err.print("initial board: ");
        String boardString = in.nextLine();
        EightPuzzle puzzle = new EightPuzzle(boardString);
        
        System.out.println();
        for (int i = 0; i < searcherNames.length; i++)
            System.out.println(i + ". " + searcherNames[i] + " SEARCH: ");
        System.out.print("\nWhich search algorithm (0 - " + 
                         (searcherNames.length - 1) + "): ");
        int algNum = in.nextInt();
        in.nextLine();
        
        Searcher searcher = null;
        switch (algNum) {
            case 0:
                searcher = new BreadthFirstSearcher();
                break;
            case 1:
                searcher = new DepthFirstSearcher();
                break;
            case 2:
                searcher = new IterativeDeepeningSearcher();
                break;
            case 3:
                searcher = new GreedySearcher();
                break;
            case 4:
                searcher = new AStarSearcher();
                break;
            default:
                System.out.println("unknown algorithm: " + algNum);
                System.exit(1);
        }
        
        System.err.print("show visited nodes (y/n): ");
        String choice = in.nextLine();
        boolean showVisited = choice.equalsIgnoreCase("y");
        
        if (algNum == 0 || algNum == 1) {
            System.err.print("depth limit (-1 for no limit): ");
            int limit = in.nextInt();
            in.nextLine();
            searcher.setDepthLimit(limit);
        }
        
        SearchNode soln = puzzle.findSolution(searcher, showVisited);
        System.out.println("\nvisited " + puzzle.numNodesVisited() + " nodes");
        System.out.println("max depth reached = " +
                           puzzle.maxDepthReached());
        
        if (soln != null) {
            System.out.println("found a solution at depth " +
                               soln.getDepth());
            System.err.print("show solution (y/n)? ");
            choice = in.nextLine();
            if (choice.equalsIgnoreCase("Y"))
                puzzle.printSolution(soln);
        } else {
            System.out.println("\nno solution found");
        }
    }
}