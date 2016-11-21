/* 
 * Graph.java
 * 
 * Computer Science E-22, Harvard University
 */

import java.io.*;
import java.util.*;

/**
 * An implementation of a Graph ADT.
 */
public class Graph {
    /*
     * Vertex - a private inner class for representing a vertex.
     */
    private class Vertex {
        private String id;
        private Edge edges;           // adjacency list, sorted by edge cost
        private Vertex next;          // next Vertex in linked list
        private boolean encountered;
        private boolean done;
        private Vertex parent;        // preceding vertex in path from root
        private double cost;          // cost of shortest known path
        
        private Vertex(String id) {
            this.id = id;
            cost = Double.POSITIVE_INFINITY;
        }
        
        /*
         * reinit - reset the starting values of the fields used by
         * the various algorithms, removing any values set by previous
         * uses of the algorithms.
         */
        private void reinit() {
            done = false;
            encountered = false;
            parent = null;
            cost = Double.POSITIVE_INFINITY;
        }
        
        /*
         * addToAdjacencyList - add the specified edge to the
         * adjacency list for this vertex.
         */
        private void addToAdjacencyList(Edge e) {
            /* Add to the front of the list. */
            e.next = edges;
            edges = e;
        }
        
        /*
         * pathString - returns a string that specifies the path from
         * the root of the current spanning tree (if there is one) to
         * this vertex.  If this method is called after performing
         * Dijkstra's algorithm, the returned string will specify the
         * shortest path.
         */
        private String pathString() {
            String str;
            
            if (parent == null)
                str = id;         /* base case: this vertex is the root */
            else
                str = parent.pathString() + " -> " + id;
            
            return str;
        }
        
        /*
         * toString - returns a string representation of the vertex 
         * of the following form:
         *    vertex v:
         *            edge to vi (cost = c1i)
         *            edge to vj (cost = c1j)
         *            ...
         */
        public String toString() {
            String str = "vertex " + id + ":\n";
            
            /* 
             * Iterate over the edges, adding a line to the string for
             * each of them.
             */
            Edge e = edges;
            while (e != null) {
                // Note: we have to use just the id field of the 
                // end vertex, or else we'll get infinite recursion!
                str += "\tedge to " + e.end.id;
                str += " (cost = " + e.cost + ")\n"; 
                
                e = e.next;
            }
            
            return str;
        }
    }
    
    /*
     * Edge - a private inner class for representing an edge.
     */
    private class Edge {
        private Vertex start;
        private Vertex end;
        private double cost;
        private Edge next;            // next Edge in adjacency list
        
        private Edge(Vertex start, Vertex end, double cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }
    
    /******* Graph instance variables and method start here. *********/
    
    /* A linked list of the vertices in the graph. */
    private Vertex vertices;
    
    /*
     * reinitVertices - private helper method that resets the starting
     * state of all of the vertices in the graph, removing any values
     * set by previous uses of the algorithms.
     */
    private void reinitVertices() {
        Vertex v = vertices;
        while (v != null) {
            v.reinit();
            v = v.next;
        }
    }
    
    /*
     * getVertex - private helper method that returns a reference to
     * the vertex with the specified id.  If the vertex isn't
     * in the graph, it returns null.
     */
    private Vertex getVertex(String id) {
        Vertex v = vertices;
        while (v != null && !v.id.equals(id))
            v = v.next;
        
        return v;
    }
    
    /*
     * addVertex - private helper method that adds a vertex with
     * the specified id and returns a reference to it.
     */
    private Vertex addVertex(String id) {
        Vertex v = new Vertex(id);
        
        /* Add to the front of the list. */
        v.next = vertices;
        vertices = v;
        
        return v;
    }
    
    /**
     * addEdge - add an edge with the specified cost, and with start
     * and end vertices that have the specified IDs.  The edge will
     * be stored in the adjacency list of the start vertex.  If a
     * specified vertex isn't already part of the graph, it will be
     * added, too.
     */
    public void addEdge(String startVertexID, String endVertexID,
                        double cost) {
        Vertex start = getVertex(startVertexID);
        if (start == null)
            start = addVertex(startVertexID);
        Vertex end = getVertex(endVertexID);
        if (end == null)
            end = addVertex(endVertexID);
        
        Edge e = new Edge(start, end, cost);
        start.addToAdjacencyList(e);
    }
    
    /**
     * toString - returns a concatenation of the string
     * representations of all of the vertices in the graph.
     */
    public String toString() {
        String str = "";
        
        Vertex v = vertices;
        while (v != null) {
            str += v;
            v = v.next;
        }
        
        return str;
    }
    
    /**
     * initFromFile - initialize a graph using the information in the
     * specified file.  The file should be a text file consisting of
     * lines that specify the edges of the graph in the following form:
     *     <start vertex data> <end vertex data> <cost>
     */
    public void initFromFile(String fileName) {
        String lineString = "";
        
        try {
            /* This Scanner will scan the file, one line at a time. */
            Scanner file = new Scanner(new File(fileName));
            
            /* Parse the file, one line at a time. */
            while (file.hasNextLine()) {
                lineString = file.nextLine();
                Scanner line = new Scanner(lineString);
                
                String startID = line.next();
                String endID = line.next();
                double cost = line.nextDouble();
                addEdge(startID, endID, cost);
            }
        } catch (IOException e) {
            System.out.println("Error accessing " + fileName);
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.out.println("invalid input line: " + lineString);
            System.exit(1);
        }
    }
    
    /**
     * depthFirstTrav - perform a depth-first traversal starting from
     * the vertex with the specified ID.  After making sure that the
     * start vertex exists, it makes an initial call to the recursive
     * method dfTrav, which does the actual traversal.
     */
    public void depthFirstTrav(String originID) {
        reinitVertices();
        
        /* Get the specified start vertex. */
        Vertex start = getVertex(originID);
        if (start == null)
            throw new IllegalArgumentException("no such vertex: " +
                                               originID);
        
        /* Start the recursion rolling... */
        dfTrav(start, null, true);
    }
    
    /*
     * dfTrav - a recursive method used to perform a depth-first
     * traversal, starting from the specified vertex v.  The parent
     * parameter specifies v's parent in the depth-first spanning
     * tree.  In the initial invocation, the value of parent should be
     * null, because the starting vertex is the root of the spanning
     * tree.
     */
    private static void dfTrav(Vertex v, Vertex parent, boolean shouldPrint) {
        /* Visit v. */
        if (shouldPrint)
            System.out.println("\t" + v.id + 
                               (parent == null ? "" : " (parent = " + parent.id + ")"));
        v.done = true;
        v.parent = parent;
        
        Edge e = v.edges;
        while (e != null) {
            Vertex w = e.end;
            if (!w.done)
                dfTrav(w, v, shouldPrint);
            e = e.next;
        }
    }
    
    /**
     * breadthFirstTrav - perform a breadth-first traversal starting from
     * the vertex with the specified ID.
     */
    public void breadthFirstTrav(String originID) {
        reinitVertices();
        
        /* Get the specified start vertex. */
        Vertex origin = getVertex(originID);
        if (origin == null)
            throw new IllegalArgumentException("no such vertex: " +
                                               originID);
        
        bfTrav(origin);
    }
    
    private static void bfTrav(Vertex origin) {
        /* Mark the origin as encountered, and add it to the queue. */
        origin.encountered = true;
        origin.parent = null;
        Queue<Vertex> q = new LLQueue<Vertex>();
        q.insert(origin);
        
        while (!q.isEmpty()) {
            /* Remove a vertex v and visit it. */
            Vertex v = q.remove();
            System.out.println("\t" + v.id + 
                               (v.parent == null ? "" : " (parent = " + v.parent.id + ")"));
            
            /* Add v's unencountered neighbors to the queue. */
            Edge e = v.edges;
            while (e != null) {
                Vertex w = e.end;
                if (!w.encountered) {
                    w.encountered = true;
                    w.parent = v;
                    q.insert(w);
                }
                e = e.next;
            }
        }
    }
    
    /**
     * dijkstra - apply Dijkstra's algorithm starting from the
     * specified origin vertex to find the shortest path from the
     * origin to all other vertices that can be reached from the
     * origin.
     *
     * The method prints the vertices in the order in which they are
     * finalized.  For each vertex v, it lists the total cost of the
     * shortest path from the origin to v, as well as v's parent
     * vertex.  Tracing back along the parents gives the shortest
     * path.
     */
    public void dijkstra(String originID) {
        /* This will give all vertices an infinite cost. */
        reinitVertices();
        
        /* Get the origin and set its cost to 0. */
        Vertex origin = getVertex(originID);
        if (origin == null)
            throw new IllegalArgumentException("no such vertex: " +
                                               originID);
        origin.cost = 0;
        
        while (true) {
            /* Find the unfinalized vertex with the minimal cost. */
            Vertex w = null;
            Vertex v = vertices;
            while (v != null) {
                if (!v.done && (w == null || v.cost < w.cost))
                    w = v;
                v = v.next;
            }
            
            /* 
             * If there are no unfinalized vertices, or if all of the
             * unfinalized vertices are unreachable from the origin
             * (which is the case if the w.cost is infinite), then
             * we're done.
             */
            if (w == null || w.cost == Double.POSITIVE_INFINITY)
                return;
            
            /* Finalize w. */
            System.out.println("\tfinalizing " + w.id + " (cost = " + w.cost +
                               (w.parent == null ? ")" : ", parent = " + w.parent.id + ")"));
            System.out.println("\t\tpath = " + w.pathString());
            w.done = true;
            
            /* Try to improve the estimates of w's unfinalized neighbors. */
            Edge e = w.edges;
            while (e != null) {
                Vertex x = e.end;
                if (!x.done) {
                    double cost_via_w = w.cost + e.cost;
                    if (cost_via_w < x.cost) {
                        x.cost = cost_via_w;
                        x.parent = w;
                    }
                }
                e = e.next;
            }
        }
    }
    
    /**
     * prim - apply Prim's algorithm starting from the specified
     * vertex to find a minimum spanning tree for the graph.  
     * The method assumes that the graph is connected.
     *
     * The "done" instance variable is used to divide the vertices
     * into two sets.  Initially, the origin is in one set (the one
     * containing vertices for which done == true), and all other
     * vertices are in a second set (the one containing vertices for
     * which done == false).  We repeatedly add the lowest-cost edge
     * joining a vertex in the first set to a vertex in the second
     * set, and then we move the vertex in the second set to the first
     * set by setting its done field to true.
     */
    public void prim(String originID) {
        reinitVertices();
        
        /* Get the origin and mark it as done. */
        Vertex origin = getVertex(originID);
        if (origin == null)
            throw new IllegalArgumentException("no such vertex: " +
                                               originID);
        origin.done = true;
        
        while (true) {
            /* 
             * Find the minimal-cost edge linking a done vertex with
             * one that isn't done. 
             */
            Edge edgeToAdd = null;
            Vertex v = vertices;
            while (v != null) {
                if (v.done) {
                    Edge e = v.edges;
                    while (e != null) {
                        if (!e.end.done && 
                            (edgeToAdd == null || e.cost < edgeToAdd.cost))
                            edgeToAdd = e;
                        e = e.next;
                    }
                }
                v = v.next;
            }
            
            /* 
             * If no such edge exists, we're done.
             */
            if (edgeToAdd == null)
                return;
            
            /* Add the edge and mark its end vertex done. */
            System.out.println("\tadding edge (" + edgeToAdd.start.id + ", " +
                               edgeToAdd.end.id + ")");
            edgeToAdd.end.parent = edgeToAdd.start;
            edgeToAdd.end.done = true;
        }
    }
    
    public boolean isConnected() {
        reinitVertices();
        
        /* 
         * Perform a depth-first traversal that begins with the
         * first vertex in the list and doesn't print the vertices as
         * it visits them.
         */
        dfTrav(vertices, null, false);
        
        /* 
         * If all vertices were visited, the graph is connected, 
         * otherwise it isn't.
         */
        Vertex v = vertices;
        while (v != null) {
            if (!v.done)
                return false;
            v = v.next;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Graph g = new Graph();
        
        System.out.print("Graph info file: ");
        String fileName = in.nextLine();
        g.initFromFile(fileName);
        
        System.out.print("starting point: ");
        String start = in.nextLine();
        
        System.out.println("depth-first traversal from " + start + ":");
        g.depthFirstTrav(start);
        
        System.out.println("breadth-first traversal from " + start + ":");
        g.breadthFirstTrav(start);
        
        System.out.println("Dijkstra's algorithm from " + start + ":");
        g.dijkstra(start);
        
        System.out.println("Prim's algorithm from " + start + ":");
        g.prim(start);
    }
}
