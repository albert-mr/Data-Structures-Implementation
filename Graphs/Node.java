package Graphs;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    private final int id;
    private String name;
    private String alias;
    private final String[] interests;
    private ArrayList<Edge> edges;
    public boolean visited;

    /**
     * Constructor Node
     */
    public Node(){
        this.id = 0;
        this.edges = new ArrayList<>();
        this.interests = new String[0];
        this.visited = false;
    }

    /**
     * Constructor Node
     * @param id id of the node
     * @param name name of the node
     * @param alias alias of the node
     * @param interests interests of the node
     */
    public Node(int id, String name, String alias, String[] interests){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.interests = Arrays.copyOf(interests, interests.length);
        this.edges = new ArrayList<>();
        this.visited = false;
    }

    /**
     * Add an edge to the node
     * @param edge edge to add
     */
    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    /**
     * get edges of the node
     * @return edges of the node
     */
    public ArrayList<Edge> getEdges(){ return this.edges; }

    /**
     * If the node has edges
     * @return true if the node has edges
     */
    public boolean hasEdges(){ return this.edges.size() != 0; }

    /**
     * Get the id of the node
     * @return id of the node
     */
    public int getId(){ return this.id; }

    /**
     * Get the interests of the node
     * @return interests of the node
     */
    public String[] getInterests(){ return this.interests; }

    /**
     * Get username of the node
     */
    public void getUsername(){ System.out.print(this.name + " (" + this.alias + ")"); }

    /**
     * Display the node
     */
    public void displayNode(){
        int i;
        System.out.print( "\t" + this.id + " - " + this.name + " (" + this.alias + ")\n\tInterests: ");
        if(interests.length != 0) {
            System.out.print(interests[0]);
            for (i = 1; i < interests.length - 1; i++) {
                System.out.print(", " + interests[i]);
            }
            if (i == 2)
                System.out.print(" & " + interests[i]);
            System.out.println();
        }else{
            System.out.println("No interests");
        }
    }

    /**
     * Compare the interests of the node with the interests of the other node
     * @param n node to compare
     * @return true if the nodes are equals
     */
    public int compareInterests(Node n){
        int numInterests = 0;
        for(String i : this.interests) {
            if(Arrays.asList(n.getInterests()).contains(i))
                numInterests++;
        }
        return numInterests;
    }

    /**
     * Compare the followers of the node with the followers of the other node
     * @param n node to compare
     * @return true if the nodes are equals
     */
    public Node[] compareFollowers(Node n){
        ArrayList<Node> cmnFollowers = new ArrayList<>();
        for (Edge edge : this.edges) {
            for (Edge e : edge.getNode().getEdges()) {
                if (n.equals(e.getNode())) {
                    cmnFollowers.add(edge.getNode());
                }
            }
        }
        return (cmnFollowers.toArray(new Node[cmnFollowers.size()]));
    }
}
