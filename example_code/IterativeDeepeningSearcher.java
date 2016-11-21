/*
 * IterativeDeepeningSearcher.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A class that is used to store and retrieve search nodes in the
 * order specified by iterative-deepening search.  
 *
 * It extends DepthFirstSearcher, using its methods to manage the
 * stored search nodes.  
 *
 * It begins with a depth limit of 0 and keeps track of the initial
 * node added to the stack (the root node of the search tree).
 * Whenever it runs out of nodes, it increases the depth limit and
 * starts over with the initial node.
 */
public class IterativeDeepeningSearcher extends DepthFirstSearcher {
    private SearchNode initialNode;
    
    public IterativeDeepeningSearcher() {
        setDepthLimit(0);
    }
    
    public void addNode(SearchNode node) {
        // Keep track of the initial node.
        if (!super.hasMoreNodes() && node.getDepth() == 0)
            initialNode = node; 
        
        super.addNode(node);
    }
    
    public void addNodes(List nodeList) {
        // Keep track of the initial node.
        if (!super.hasMoreNodes() && 
            ((SearchNode)nodeList.getItem(0)).getDepth() == 0) {
            initialNode = (SearchNode)nodeList.getItem(0);
        }
        
        super.addNodes(nodeList);
    }
    
    public boolean hasMoreNodes() {
        // If we run out of nodes, we increase the depth limit and
        // start again with the initial node.
        if (!super.hasMoreNodes()) {
            setDepthLimit(getDepthLimit() + 1);
            super.addNode(initialNode);
            
            // print status message
            System.err.println("increased depth limit to " + getDepthLimit());
        }
        
        return true;
    } 
}
