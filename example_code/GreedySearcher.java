/*
 * GreedySearcher.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A class that is used to store and retrieve search nodes in the
 * order specified by greedy search.  It uses a heap-based priority
 * queue to store the search nodes, with -1 times the cost-to-goal
 * estimate as the priority.  Each search node is wrapped in a
 * PQItem object, which stores both the search node and its
 * priority.
 */
public class GreedySearcher extends Searcher {
    public static final int INITIAL_MAXSIZE = 1024;
    private Heap<PQItem> nodePQueue;
    
    public GreedySearcher() {
        nodePQueue = new Heap<PQItem>(INITIAL_MAXSIZE);
    }
    
    public void addNode(SearchNode node) {
        if (node != null)
            nodePQueue.insert(new PQItem(node, -1 * node.getCostToGoal()));
    }
    
    public void addNodes(List nodeList) {
        int numNodes = nodeList.length();
        for (int i = 0; i < numNodes; i++)
            addNode((SearchNode)nodeList.removeItem(0));
    }
    
    public boolean hasMoreNodes() {
        return (!nodePQueue.isEmpty());
    } 
    
    public SearchNode nextNode() {
        PQItem item = nodePQueue.remove();
        return (item == null ? null : (SearchNode)item.getData());
    }
}
