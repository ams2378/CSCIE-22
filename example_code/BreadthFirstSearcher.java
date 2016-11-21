/*
 * BreadthFirstSearcher.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A class that is used to store and retrieve search nodes in the
 * order specified by breadth-first search.  It uses a queue to store
 * the search nodes.
 */
public class BreadthFirstSearcher extends Searcher {
    private Queue<SearchNode> nodeQueue;
    
    public BreadthFirstSearcher() {
        nodeQueue = new LLQueue<SearchNode>();
    }
    
    public void addNode(SearchNode node) {
        if (node != null)
            nodeQueue.insert(node);
    }
    
    public void addNodes(List nodeList) {
        int numNodes = nodeList.length();
        for (int i = 0; i < numNodes; i++) {
            nodeQueue.insert((SearchNode)nodeList.removeItem(0));
        }
    }
    
    public boolean hasMoreNodes() {
        return (!nodeQueue.isEmpty());
    } 
    
    public SearchNode nextNode() {
        return nodeQueue.remove();
    }
}
