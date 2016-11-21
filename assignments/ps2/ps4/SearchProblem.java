/*
 * SearchProblem.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * An abstract class for state-space search problems.  It implements
 * functionality that is common to all state-space search problems,
 * and it specifies abstract methods that must be implemented by all
 * subclasses of this class.
 */
public abstract class SearchProblem {
    private int numNodesVisited; 
    private int maxDepthReached;
    
    public int numNodesVisited() {
        return numNodesVisited;
    }
    
    public int maxDepthReached() {
        return maxDepthReached;
    }
    
    /**
     * makeFirstNode - create a search node that represents the
     * initial state of the search problem.
     */
    public abstract SearchNode makeFirstNode();
    
    /**
     * numOperators - return the number of operators associated with
     * the search problem.  This method is needed so that
     * generateSuccessors() can apply each of the operators, one at a
     * time.
     */
    public abstract int numOperators();
    
    /**
     * applyOperator - apply the specified operator to the state
     * stored in the specified node, and return a SearchNode
     * representing the resulting state -- or null if the operator
     * could not be applied.
     */
    public abstract SearchNode applyOperator(int opNum, SearchNode node);
    
    /**
     * generateSuccessors - generate SearchNodes for all of the
     * successors of the specified node and return a List that
     * contains the successors.
     */
    public List generateSuccessors(SearchNode node) {
        LLList successors = new LLList();
        
        for (int i = 0; i < numOperators(); i++) {
            SearchNode successor = applyOperator(i, node);
            if (successor != null && !successor.createsCycle())
                successors.addItem(successor, 0);
        }
        
        return successors;
    }
    
    /**
     * isGoal - does the specified SearchNode contain a goal state?
     */
    public abstract boolean isGoal(SearchNode node);
    
    /**
     * printSolution - take the specified goal node and print the
     * steps taken to get from the initial state to the goal state.
     */
    public abstract void printSolution(SearchNode goalNode);
    
    /**
     * findSolution - search for a solution to this search problem
     * using the specified search algorithm (as embodied in the
     * Searcher object).  showVisited indicates whether the method
     * should display the states as it visits them (i.e., as it tests
     * them to see if they are a goal state).
     */
    public SearchNode findSolution(Searcher searcher, boolean showVisited) {
        numNodesVisited = 0;
        maxDepthReached = 0;
        
        searcher.addNode(makeFirstNode());
        
        try {
            while (searcher.hasMoreNodes()) {
                SearchNode node = searcher.nextNode();
                numNodesVisited++;
                if (node.getDepth() > maxDepthReached)
                    maxDepthReached = node.getDepth();
                if (showVisited)
                    System.out.println(node.getState());
                
                if (isGoal(node))
                    return node;
                
                if (!searcher.depthLimitReached(node))
                    searcher.addNodes(generateSuccessors(node));
            }
        } catch (OutOfMemoryError e) {
            System.out.println("out of memory after visiting " +
                               numNodesVisited + " nodes");
        }
        
        return null;    // failed to find a solution
    }
}