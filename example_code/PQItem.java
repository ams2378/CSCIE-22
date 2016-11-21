/*
 * PQItem.java
 *
 * Computer Science E-22, Harvard University
 */

/**
 * PQItem - a class that is used for items stored in a priority
 * queue (e.g., a heap).  Associated with each item is a priority, and
 * the class' compareTo() method compares items according to their
 * priorities.
 */
public class PQItem implements Comparable<PQItem> {
    private Object data;
    private double priority;
    
    public PQItem(Object data, double priority) {
        this.data = data;
        this.priority = priority;
    }
    
    public Object getData() {
        return data;
    }
    
    public double getPriority() {
        return priority;
    }
    
    public int compareTo(PQItem other) {
        double diff = priority - other.priority;
        if (diff > 1e-6)
            return 1;
        else if (diff < -1e-6)
            return -1;
        else
            return 0;
    }
    
    public String toString() {
        if (data == null) {
            if ((int)priority == priority)
                return "" + (int)priority;
            else
                return "" + priority;
        } else
            return data + "(priority = " + priority + ")";
    }
}
