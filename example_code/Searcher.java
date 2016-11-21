/*
 * Searcher.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * An abstract class for state-space search algorithms.  It implements
 * functionality that is common to all state-space search algorithms,
 * and it specifies abstract methods that must be implemented by all
 * subclasses of this class.
 */
public abstract class Searcher {
    // The maximum depth that this Searcher will go in the search tree.
    // By default, the depth limit is -1, meaning there is no limit.
    private static final int NO_LIMIT = -1;
    private int depthLimit = NO_LIMIT;
    
    /**
     * addNode - add the specified search node to the collection of
     * nodes that this Searcher has yet to consider.
     */
    public abstract void addNode(SearchNode node);
    
    /**
     * addNodes - add the specified list of search nodes to the
     * collection of nodes that this Searcher has yet to consider.
     * The list should contain SearchNodes.
     */
    public abstract void addNodes(List nodes);
    
    /**
     * hasMoreNodes - does this searcher still have more nodes to visit?
     */
    public abstract boolean hasMoreNodes(); 
    
    /**
     * nextNode - Get the next node that should be considered according
     * to the search strategy used by this Searcher.
     */
    public abstract SearchNode nextNode();
    
    public void setDepthLimit(int depth) {
        depthLimit = depth;
    }
    
    public int getDepthLimit() {
        return depthLimit;
    }
    
    /**
     * depthLimitReached - returns true if the specified node is
     * at or beyond the depth limit for this Searcher, and false
     * otherwise.
     */
    public boolean depthLimitReached(SearchNode node) {
        if (depthLimit == NO_LIMIT)
            return false;
        else
            return (node.getDepth() >= depthLimit);
    }
}
