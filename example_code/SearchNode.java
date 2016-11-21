/*
 * SearchNode.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * A class for nodes in a state-space search tree.
 */
public class SearchNode {
    private Object state;           // the state of the problem
    private SearchNode predecessor; // the node's parent in the tree
    private String operator;        // the operator used to produce the state
    private int numSteps;           // the node's depth in the tree
    private double costFromStart;   // cost from the root node to this node
    private double costToGoal;      // cost-to-goal estimate
    
    public SearchNode(Object state, SearchNode predecessor, String operator,
                      int numSteps, double cost, double costToGoal) {        
        this.state = state;
        this.predecessor = predecessor;
        this.operator = operator;
        this.numSteps = numSteps;
        this.costFromStart = cost;
        this.costToGoal = costToGoal;
    }
    
    public Object getState() {
        return state;
    }
    
    public SearchNode getPredecessor() {
        return predecessor;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public int getNumSteps() {
        return numSteps;
    }
    
    public int getDepth() {
        return numSteps;
    }
    
    public double getCostFromStart() {
        return costFromStart;
    }
    
    public double getCostToGoal() {
        return costToGoal;
    }
    
    /**
     * createsCycle - returns true if the state in this node is
     * already present somewhere else in the path from the root node
     * to this node, and false otherwise.
     */
    public boolean createsCycle() {
        SearchNode ancestor = predecessor;
        while (ancestor != null) {
            if (state.equals(ancestor.state))
                return true;
            ancestor = ancestor.predecessor;
        }
        
        return false;
    }
}
