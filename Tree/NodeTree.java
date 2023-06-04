package Tree;

public class NodeTree {

    public int id;  //id of the algorithm
    public String name; //name of the algorithm
    public String language; //language of the algorithm
    public String cost; //cost of the algorithm
    public long timestamp; //timestamp of the algorithm
    public NodeTree left; //left child
    public NodeTree right; //right child
    int height; //height of the node

    /**
     * Constructor of the class NodeTree
     * @param id id of the algorithm
     * @param name name of the algorithm
     * @param language language of the algorithm
     * @param cost cost of the algorithm
     * @param timestamp timestamp of the algorithm
     */
    public NodeTree(int id, String name, String language, String cost, long timestamp) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.cost = cost;
        this.timestamp = timestamp;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}


