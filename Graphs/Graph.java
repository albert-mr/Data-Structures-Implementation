package Graphs;

import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private Node[] nodes;
    private int[] pos;

    /**
     * Constructor of the class Graph
     */
    public Graph() {
    }

    /**
     * Add a node to the graph
     * @param nodes array of nodes
     */
    public void addNodes(Node[] nodes) {
        int sz;
        sz = nodes.length;
        this.nodes = new Node[nodes[sz - 1].getId() + 1];
        this.pos = new int[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[nodes[i].getId()] = nodes[i];
            this.pos[i] = nodes[i].getId();
        }
    }

    /**
     * Assign the edges to the nodes
     * @param userA user A
     * @param userB user B
     * @param t timestamp
     * @param i number of interactions
     */
    public void assignEdge(int userA, int userB, int t, int i) {
        Edge edge = new Edge(nodes[userB], t, i);
        nodes[userA].addEdge(edge);
    }

    /**
     * Breadth-first search
     * @param node node to start the search
     * @param cnt number of nodes
     * @return array of nodes
     */
    public int BFS(Node node, int cnt) {
        boolean newDisplay = false;
        LinkedList<Node> q = new LinkedList<>();
        q.offer(node);
        node.visited = true;
        cnt++;
        while (!q.isEmpty()) {
            Node nextNode = q.poll();
            newDisplay = false;
            for (Edge edge : nextNode.getEdges()) {
                if (!edge.getNode().visited) {
                    if (!newDisplay)
                        System.out.println("\nHere are their followed accounts:\n");
                    newDisplay = true;
                    q.offer(edge.getNode());
                    edge.getNode().visited = true;
                    cnt++;
                    edge.getNode().displayNode();
                    System.out.println();
                }
            }
        }
        return cnt;
    }


    /**
     * Explore the network
     */
    public void exploreNetwork() {
        int cnt = 0;
        long startTime = System.nanoTime();
        System.out.println("The user who follows the most accounts is:\n");
        Node n = getMostFollowed();
        n.displayNode();
        cnt = BFS(n, cnt);
        for (int id : pos) {
            if (!nodes[id].visited) {
                System.out.println("\nHere is another account:\n");
                cnt = BFS(nodes[id], cnt);
            }
        }
        System.out.println("Number of users displayed: " + cnt);
        resetVisited();
        System.out.println("\nProgram time: " + ((System.nanoTime() - startTime) / 1000000000) + "s " + (((System.nanoTime() - startTime) / 1000000) % 1000) + "ms " + (((System.nanoTime() - startTime) / 1000) % 1000) + "us");
    }


    /**
     * Recommend users based on criteria
     * @param sc scanner
     */
    public void recommendUsers(Scanner sc) {
        System.out.print("Enter your identifier: ");
        Node n = nodes[sc.nextInt()];
        long startTime = System.nanoTime();
        Random r = new Random();
        Node[] cmnFollowers;
        Node[] bestRec = new Node[10];
        int[] bestWeights = new int[10];
        int currWeight, i, numInterests;
        int[][] criteria = new int[10][4];
        boolean followed;
        for (int id : pos) {
            if (!isFollow(nodes[id], n) && !n.equals(nodes[id])) {
                currWeight = 0;
                followed = isFollow(n, nodes[id]);
                currWeight += followed ? 3 : 0;
                cmnFollowers = n.compareFollowers(nodes[id]);
                currWeight += r.nextInt(1, 3) * cmnFollowers.length;
                numInterests = n.compareInterests(nodes[id]);
                currWeight += r.nextInt(1, 3) * numInterests;
                i = 10;
                while (i > 0 && currWeight > bestWeights[i - 1]) {
                    i--;
                }
                for (int j = 9; j >= i; j--) {
                    if (j == i) {
                        bestWeights[j] = currWeight;
                        bestRec[j] = nodes[id];
                        criteria[j][3] = followed ? 1 : 0;
                        criteria[j][2] = cmnFollowers.length == 0 ? 0 : getMostInteracted(cmnFollowers, n);
                        criteria[j][1] = numInterests;
                        criteria[j][0] = cmnFollowers.length;
                    } else {
                        bestWeights[j] = bestWeights[j - 1];
                        bestRec[j] = bestRec[j - 1];
                        System.arraycopy(criteria[j - 1], 0, criteria[j], 0, 4);
                    }
                }

            }
        }
        try {
            displayRecommendations(criteria, bestRec);
            System.out.println("\nProgram time: " + ((System.nanoTime() - startTime) / 1000000000) + "s " + (((System.nanoTime() - startTime) / 1000000) % 1000) + "ms " + (((System.nanoTime() - startTime) / 1000) % 1000) + "us");
        } catch (NullPointerException e) {
            System.out.println("\nThis user is not included in the database.\n");
        }
    }

    /**
     * Display the recommendations
     * @param criteria criteria
     * @param bestRec best recommendations
     */
    private void displayRecommendations(int[][] criteria, Node[] bestRec) {
        boolean crit = true;
        System.out.println("You might be interested by the following accounts:\n\n");
        for (int i = 0; i < 10; i++) {
            bestRec[i].displayNode();
            System.out.print("\tCriteria: ");
            if (criteria[i][0] > criteria[i][1]) {
                System.out.print("Followed by " + criteria[i][0] + " - ");
                nodes[criteria[i][2]].getUsername();
                if (criteria[i][0] > 1)
                    System.out.print(" and more..");

            } else if (criteria[i][0] < criteria[i][1]) {
                System.out.print("You share " + criteria[i][1] + " interests");
            } else
                crit = false;
            if (criteria[i][3] == 1) {
                if (crit)
                    System.out.print(" and t");
                else
                    System.out.print("T");

                System.out.print("hey follow you");
            }
            System.out.println("\n");
        }
    }

    /**
     * Get the most interacted node
     * @param cmnFollowers common followers
     * @param n node
     * @return the most interacted node
     */
    private int getMostInteracted(Node[] cmnFollowers, Node n) {
        long maxInteractions = 0;
        int id = 0;
        for (Edge edge : n.getEdges()) {
            for (Node node : cmnFollowers) {
                if (node == edge.getNode()) {
                    maxInteractions = Math.max(edge.getInteractions(), maxInteractions);
                    id = node.getId();
                }
            }
        }
        return id;
    }

    /**
     * Get the most followed node
     * @param n1 node
     * @param n2 node
     * @return the most followed node
     */
    private boolean isFollow(Node n1, Node n2) {
        for (Edge edge : n1.getEdges()) {
            if (edge.getNode().equals(n2))
                return true;
        }
        return false;
    }

    /**
     * Get the most followed node
     * @return the most followed node
     */
    private Node getMostFollowed() {
        Node node = new Node();
        for (int id : pos) {
            if (node.getEdges().size() < nodes[id].getEdges().size()) {
                node = nodes[id];
            }
        }
        return node;
    }


    /**
     * Depth first search
     * @param n node
     * @param cnt number of nodes
     * @return number of nodes
     */
    private int DFS(Node n, int cnt) {
        n.visited = true;
        cnt++;
        for (Edge edge : n.getEdges()) {
            if (!edge.getNode().visited) {
                System.out.println("\n\t↓");
                System.out.print("\t" + edge.getNode().getId() + " - ");
                edge.getNode().getUsername();
                cnt = DFS(edge.getNode(), cnt);
            }
        }
        return cnt;
    }

    /**
     * Process of the drama dataset
     */
    public void contextualizeDrama() {
        int cnt = 0;
        boolean firstTime = true;
        long startTime = System.nanoTime();
        System.out.println("Processing the drama dataset...\n\n");
        for (int id : pos) {
            if (!nodes[id].visited && nodes[id].hasEdges()) {
                if(firstTime) {
                    System.out.println("You should catch up with the discourse in the following order:\n\n");
                    firstTime = false;
                }else
                    System.out.println("\n\nHere is another discourse that took place: \n");
                System.out.print("\t" + nodes[id].getId() + " - ");
                nodes[id].getUsername();
                cnt = DFS(nodes[id], cnt);
            }
        }
        System.out.println("\n\nNumber of users displayed: " + cnt);
        resetVisited();
        System.out.println("\nProgram time: " + ((System.nanoTime() - startTime) / 1000000000) + "s " + (((System.nanoTime() - startTime) / 1000000) % 1000) + "ms " + (((System.nanoTime() - startTime) / 1000) % 1000) + "us");
    }

    /**
     * Network analysis
     * @param sc scanner
     */
    public void networking(Scanner sc) {
        System.out.print("Enter your identifier: ");
        int start = sc.nextInt();
        System.out.print("Enter the other user's identifier: ");
        int end = sc.nextInt();
        long startTime = System.nanoTime();
        System.out.println("\nFinding the optimal contact chain...\n\n");
        dijkstra(nodes[start], nodes[end]);
        System.out.println("\nProgram time: " + ((System.nanoTime() - startTime) / 1000000000) + "s " + (((System.nanoTime() - startTime) / 1000000) % 1000) + "ms " + (((System.nanoTime() - startTime) / 1000) % 1000) + "us");
    }

    /**
     * Reset the visited attribute
     */
    private void resetVisited() {
        for (int id : pos) {
            nodes[id].visited = false;
        }
    }

    /**
     * Dijkstra algorithm
     * @param start start node
     * @param end end node
     */
    public void dijkstra(Node start, Node end) {
        long[] distances = new long[this.nodes.length];
        int[] walks = new int[this.nodes.length];

        //We set all distances to 0 in order to get the maximum timestamp from edges
        Arrays.fill(distances, Long.MAX_VALUE);
        distances[start.getId()] = 0;
        walks[start.getId()] = -1;

        int cnt = 0;
        long newNode;
        Node current = start;
        while (cnt < this.nodes.length && !end.visited) {
            for (Edge edge : current.getEdges()) {
                if (!edge.getNode().visited) {
                    newNode = distances[current.getId()] + edge.getTimestamp();
                    if (distances[edge.getNode().getId()] > newNode) {
                        distances[edge.getNode().getId()] = newNode;
                        walks[edge.getNode().getId()] = current.getId();
                    }
                }
            }
            current.visited = true;
            cnt++;
            current = getMin(distances);
        }
        displayPath(cnt, start.getId(), end.getId(), walks);
        System.out.println(cnt);
    }

    /**
     * Display the path
     * @param cnt
     * @param startId
     * @param endId
     * @param walks
     */
    private void displayPath(int cnt, int startId, int endId, int[] walks) {
        Stack<Node> finalPath = new Stack<>();
        if (cnt < this.nodes.length) {
            int previousPos = endId;
            while (previousPos != -1) {
                finalPath.push(nodes[previousPos]);
                previousPos = walks[previousPos];
            }
            System.out.print("[ " + finalPath.pop().getId());
            while (!finalPath.isEmpty()) {
                System.out.print(" → " + finalPath.pop().getId());
            }
            System.out.println(" ]");
        } else {
            System.out.println("There is no way to reach user nº" + endId + " through user nº" + startId);
        }
    }

    /**
     * Get the minimum value
     * @param d array of long
     * @return the minimum value
     */
    private Node getMin(long[] d) {
        long min = Long.MAX_VALUE;
        int index = -1;
        for (int id : this.pos) {
            if (!nodes[id].visited && min > d[id]) {
                min = d[id];
                index = id;
            }
        }
        return nodes[index];
    }
}