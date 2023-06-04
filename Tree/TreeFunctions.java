package Tree;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeFunctions {

    /**
     * This method is used to read the file containing the information of all
     * the algorithms.
     * @return tree The tree containing all the information of the algorithms.
     */
    public static BinarySearchTree read () throws FileNotFoundException {
        File f = new File("src/Tree/TreeFiles/treeXXL.paed");
        BinarySearchTree tree = new BinarySearchTree();
        Scanner parameter = new Scanner(f);
        int N, id, i;
        long timestamp;
        String tmp, name, cost, language, y;
        String[] all;

        N = parameter.nextInt();
        tmp = parameter.nextLine();

        for (i = 0; i < N; i++) {
            y = parameter.nextLine();
            all = y.split("[;]+");

            id = Integer.valueOf(all[0]);
            name = all[1];
            language = all[2];
            cost = all[3];
            timestamp = Integer.valueOf(all[4]);

            tree.insert(id, name, language, cost, timestamp);
        }
        return tree;
    }

    /**
     * This method is to get the id of the algorithm that the user wants to delete
     * @param tree the tree that contains the algorithms
     */
    public static void delete(BinarySearchTree tree) {
        Scanner sc = new Scanner(System.in);
        int id = 0;
        System.out.print("\nEnter the algorithm's identifier: ");
        id = sc.nextInt();
        long startTime = System.nanoTime();
        tree.deleteAlgorithm(id);
        System.out.println("Program time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
    }
    /**
        * This method is used to get the information from the user about the new node
        * that we want to add to the tree.
        * @param tree is the tree that will be modified.
     */
    public static void addAlgorithm(BinarySearchTree tree) {
        Scanner sc = new Scanner(System.in);
        int id;
        long timestamp;
        String name, language, cost;
        System.out.print("Enter the algorithm's identifier: ");
        id = sc.nextInt();
        System.out.print("Enter the algorithm's name: ");
        name = sc.next();
        System.out.print("Enter the algorithm's language: ");
        language = sc.next();
        System.out.print("Enter the algorithm's cost: ");
        cost = sc.next();
        System.out.print("Enter the algorithm's timestamp: ");
        timestamp = sc.nextLong();
        long startTime = System.nanoTime();
        tree.insert(id, name, language, cost, timestamp);
        System.out.println("Program time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
        System.out.println("\nThe algorithm was correctly added to the feed.");
    }
    /**
     * This method is used to get the timestamp of the algorithm that the user wants to search.
     */
    public static void algorithmSearchExact() {
        Scanner sc = new Scanner(System.in);
        long timestamp = 0;
        System.out.print("\nEnter the timestamp to search for: ");
        timestamp = sc.nextInt();
        long startTime = System.nanoTime();
        NodeTree root = BinarySearchTree.searchExact(BinarySearchTree.root, timestamp);
        System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
        if (root != null) {
            displayAlgorithm(root);
        } else {
            System.out.println("\nThe algorithm was not found.");
        }
    }
    /**
     * This method is used to get the 2 timestamps to be able to display all the algorithms that
     * are between them.
     */
    public static void algorithmSearchRange() {
        Scanner sc = new Scanner(System.in);
        long timestampMin = 0;
        long timestampMax = 0;
        int count = 0;
        System.out.print("\nEnter the minimum timestamp to search for: ");
        timestampMin = sc.nextLong();
        System.out.print("\nEnter the maximum timestamp to search for: ");
        timestampMax = sc.nextLong();
        count = BinarySearchTree.algorithmRangeCount(BinarySearchTree.root, timestampMin, timestampMax);
        System.out.println(count + " algorithms were found in this range...\n");
        long startTime = System.nanoTime();
        BinarySearchTree.algorithmRange(timestampMin, timestampMax);
        System.out.println("Program time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
    }

    /**
     * This method is used to display the information of the algorithm that the user wants to see
     * in the Search by timestamp(exact) option.
     */
    public static void displayAlgorithm(NodeTree root) {
        System.out.println("\nAn algorithm was found... " + root.name + ": " + root.language + ", " + root.cost);
    }
}