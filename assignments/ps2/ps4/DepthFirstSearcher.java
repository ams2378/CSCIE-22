/*
 * DepthFirstSearcher.java
 *
 * Computer Science E-22, Harvard University
 */

/*
 * A class that is used to store and retrieve search nodes in the
 * order specified by depth-first search.  It uses a stack to store
 * the search nodes.
 */
public class DepthFirstSearcher extends Searcher {
    private Stack<SearchNode> nodeStack;
    
    public DepthFirstSearcher() {
        nodeStack = new LLStack<SearchNode>();
    }
    
    public void addNode(SearchNode node) {
        if (node != null)
            nodeStack.push(node);
    }
    
    public void addNodes(List nodeList) {
        int numNodes = nodeList.length();
        for (int i = 0; i < numNodes; i++) {
            nodeStack.push((SearchNode)nodeList.removeItem(0));
        }
    }
    
    public boolean hasMoreNodes() {
        return (!nodeStack.isEmpty());
    } 
    
    public SearchNode nextNode() {
        return nodeStack.pop();
    }
}