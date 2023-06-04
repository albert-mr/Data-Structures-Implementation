package Tree;

import org.w3c.dom.Node;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BinarySearchTree {

    public static NodeTree root; //root of the tree

    /**
     * Constructor
     */
    BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a new node in the tree with the information of the new algorithm.
     * Calls the function to insert the node in the tree.
     * @param id the id of the new algorithm
     * @param name the name of the new algorithm
     * @param language the language of the new algorithm
     * @param cost the cost of the new algorithm
     * @param timestamp the timestamp of the new algorithm
     */
    public void insert(int id, String name, String language, String cost, long timestamp) {
        root = insertBST(root, id, name, language, cost, timestamp);
    }

    /**
     * Inserts a new node in the tree with the information of the new algorithm.
     * @param node the root of the tree
     * @param id the id of the algorithm
     * @param name the name of the algorithm
     * @param language the language of the algorithm
     * @param cost the cost of the algorithm
     * @param timestamp the timestamp of the algorithm
     * @return the root of the tree
     */
    public NodeTree insertBST(NodeTree node, int id, String name, String language, String cost, long timestamp) {
        if (node == null) { //if the tree is empty
            node = new NodeTree(id, name, language, cost, timestamp);
            return node;
        } else if (timestamp < node.timestamp) { //if it is smaller than the root we go left
            node.left = insertBST(node.left, id, name, language, cost, timestamp);
        } else if (timestamp > node.timestamp) { //if it is bigger than the root we go right
            node.right = insertBST(node.right, id, name, language, cost, timestamp);
        } else {
            return node; //if it is equal we do nothing
        }
        NodeTree nw = new NodeTree(id, name, language, cost, timestamp);
        return balance(node, nw); //balance the tree
    }

    /**
     * Balances the tree in case of insertion.
     * @param node the subtree to be balanced
     * @param nw the new node to be inserted
     * @return the root of the tree balanced
     */

    public NodeTree balance(NodeTree node, NodeTree nw) {
        node.height = 1 + Math.max(calculateHeight(node.right), calculateHeight(node.left));
        int balance = calculateBalance(node);
        if (balance > 1) { //the left subtree is bigger than the right
            if (nw.timestamp < node.left.timestamp) //apply right rotation
                return rightRotation(node);
            else if (nw.timestamp > node.left.timestamp) //apply left right rotation
                node.left = leftRotation(node.left);
                return rightRotation(node);
        }
        else if (balance < -1) { //the right subtree is bigger than the left
            if (nw.timestamp > node.right.timestamp) //apply left rotation
                return leftRotation(node);
            else if (nw.timestamp < node.right.timestamp) //apply right left rotation
                node.right = rightRotation(node.right);
                return leftRotation(node);
        }
        return node;
    }

    /**
     * Balances the tree in case of a deletion
     * @param node the subtree to be balanced
     * @return the root of the tree balanced
     */
    public NodeTree balanceDelete(NodeTree node) {
        node.height = 1 + Math.max(calculateHeight(node.right), calculateHeight(node.left));
        int balance = calculateBalance(node);
        if (balance > 1) { //the left subtree is bigger than the right
            if (calculateBalance(node.left) >= 0) //apply right rotation
                return rightRotation(node);
            else //apply left right rotation
                node.left = leftRotation(node.left);
                return rightRotation(node);
        }
        else if (balance < -1) { //the right subtree is bigger than the left
            if (calculateBalance(node.right) <= 0) //apply left rotation
                return leftRotation(node);
            else //apply right left rotation
                node.right = rightRotation(node.right);
                return leftRotation(node);
        }
        return node;
    }

    /**
     * Calculates the height of the tree
     * @param node the subtree to be calculated
     * @return the height of the tree
     */
    public int calculateHeight(NodeTree node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Calculates the balance of the tree
     * @param node the subtree to be calculated
     * @return the balance of the tree
     */
    public int calculateBalance(NodeTree node) {
        return node == null ? 0 : (calculateHeight(node.left) - calculateHeight(node.right));
    }

    /**
     * Performs a right rotation
     * @param node the subtree to be rotated
     * @return the root of the tree rotated
     */
    public NodeTree rightRotation(NodeTree node) {
        NodeTree temp = node.left;
        NodeTree temp2 = temp.right;
        temp.right = node;
        node.left = temp2;
        node.height = 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
        temp.height = 1 + Math.max(calculateHeight(temp.left), calculateHeight(temp.right));
        return temp;
    }

    /**
     * Performs a left rotation
     * @param node the subtree to be rotated
     * @return the root of the tree rotated
     */
    public NodeTree leftRotation(NodeTree node) {
        NodeTree temp = node.right;
        NodeTree temp2 = temp.left;
        temp.left = node;
        node.right = temp2;
        node.height = Math.max(calculateHeight(node.left), calculateHeight(node.right)) + 1;
        temp.height = Math.max(calculateHeight(temp.left), calculateHeight(temp.right)) + 1;
        return temp;
    }

    /**
     * Searches for an exact algorithm in the tree with the timestamp of the algorithm
     * @param root the root of the tree
     * @param timestamp the timestamp of the algorithm
     * @return the algorithm if it is found, null otherwise
     */
    public static NodeTree searchExact(NodeTree root, long timestamp) {
        if (root==null || root.timestamp==timestamp) { //if we have found the algorithm or if the tree is empty
            return root;
        }
        if (root.timestamp < timestamp) //we go right
            return searchExact(root.right, timestamp);

        return searchExact(root.left, timestamp); //we go left
    }

    /**
     * Calls the delete function to delete the algorithm with the id given by the user
     * @param id the id of the algorithm
     */
    public void deleteAlgorithm(int id) {
        root = deleteNode(root, id);
    }

    /**
     * Deletes the algorithm with the id given by the user
     * @param root the root of the tree
     * @param id the id of the algorithm
     * @return the root of the tree
     */
    public NodeTree deleteNode(NodeTree root, int id) {
        boolean leaf = false; //if the node is a leaf
        if (root != null) {
            root.right = deleteNode(root.right, id); //we go right
            root.left = deleteNode(root.left, id); //we go left
            if (root.id == id) { //if we have found the node
                System.out.println("The " + root.name + " " + root.language + " algorithm was correctly removed from the feed.");
                if (root.left != null && root.right != null) { //if the node has two children
                    NodeTree aux = root;
                    NodeTree min = min(aux.right);
                    root.timestamp = min.timestamp; //TO DO: change all values to the min node
                    root.id = min.id;
                    root.name = min.name;
                    root.language = min.language;
                    root.cost = min.cost;
                    root.right = deleteNode(root.right, aux.id);
                } else if (root.left != null) { //if the node has only one child
                    root = root.left;
                } else if (root.right != null) { //if the node has only one child
                    root = root.right;
                } else { //if the node has no children
                    leaf = true;
                    root = null;
                }
            }
        } else { //if the node is null
            return root;
        }
        return leaf ? root : balanceDelete(root);
    }

    /**
     * Finds the minimum node in the tree
     * @param root the root of the tree
     * @return the minimum node
     */

    public static NodeTree min(NodeTree root) {
        if (root.left == null)
            return root;
        return min(root.left);
    }

    /**
     * Calls the function to display the tree in preorder
     */
    public static void displayOrder() {
        displayOrderBST(root);
    }

    /**
     * Displays the tree in preorder
     * @param root the root of the tree
     */
    public static void displayOrderBST(NodeTree root) {
        if (root != null) {
            displayOrderBST(root.right); //we go right
            String timestamp = timestampToDate(root.timestamp); //we convert the timestamp to a date
            System.out.println("\t" + root.id + "---" + root.name + ": " + root.language + ", " + root.cost + " - " + timestamp);
            displayOrderBST(root.left); //we go left
        }
    }

    /**
     * Converts the timestamp to a date
     * @param timestamp the timestamp to be converted
     * @return the date in String format dd/MM/yyyy
     */
    public static String timestampToDate(long timestamp) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);
    }

    /**
     * Counts the algorithms that are in a given range of timestamps
     * @param root the root of the tree
     * @param timestampMin the minimum timestamp
     * @param timestampMax the maximum timestamp
     * @return the number of algorithms in the range
     */

    public static int algorithmRangeCount(NodeTree root, long timestampMin, long timestampMax) {
        if (root == null) {
            return 0;
        }
        if (timestampMin <= root.timestamp && timestampMax >= root.timestamp) { //if the algorithm is in the range
            return 1 + algorithmRangeCount(root.left, timestampMin, timestampMax)
                    + algorithmRangeCount(root.right, timestampMin, timestampMax);
        }
        else if (root.timestamp > timestampMax) { //if the algorithm is after the range
            return algorithmRangeCount(root.left, timestampMin, timestampMax);
        }
        else {
            return algorithmRangeCount(root.right, timestampMin, timestampMax); //if the algorithm is before the range
        }
    }

    /**
     * Calls the function to display the algorithms found in a given range of timestamps
     * @param timestampMin the minimum timestamp
     * @param timestampMax the maximum timestamp
     */
    public static void algorithmRange(long timestampMin, long timestampMax) {
        algorithmRangeDisplay(root, timestampMin, timestampMax);
    }

    /**
     * Displays the algorithms found in a given range of timestamps
     * @param root the root of the tree
     * @param timestampMin the minimum timestamp
     * @param timestampMax the maximum timestamp
     */
    public static void algorithmRangeDisplay(NodeTree root, long timestampMin, long timestampMax) {
        if (root == null) {
            return;
        }
        if (timestampMin < root.timestamp) { //we go left
            algorithmRangeDisplay(root.left, timestampMin, timestampMax);
        }
        if (timestampMin <= root.timestamp && timestampMax >= root.timestamp) { //if the algorithm is in the range
            System.out.println("\t" + root.name + ": " + root.language + ", " + root.cost);
        }
        algorithmRangeDisplay(root.right, timestampMin, timestampMax); //we go right
    }
}
