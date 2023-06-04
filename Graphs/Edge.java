package Graphs;

public class Edge {
    private Node node;
    private long timestamp;
    private int interactions;

    /**
     * Constructor of the class Edge
     */
    public Edge(){ }

    /**
     * Constructor of the class Edge
     * @param node node of the edge
     * @param t timestamp of the edge
     * @param i number of interactions of the edge
     */
    public Edge(Node node, int t, int i){
        this.node = node;
        this.timestamp = t;
        this.interactions = i;
    }

    /**
     * Get the node of the edge
     * @return node of the edge
     */
    public Node getNode(){ return this.node; }

    /**
     * Get the timestamp of the edge
     * @return timestamp of the edge
     */
    public long getTimestamp(){ return this.timestamp; }

    /**
     * Get the number of interactions of the edge
     * @return number of interactions of the edge
     */
    public long getInteractions(){ return this.interactions; }


}

