import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

import RTree.*;
import RTree.Point;
import RTree.Rectangle;
import RTree.VisualizeRect;
import Tables.Map;
import Tree.*;
import Tables.*;
import Graphs.*;

public class Menu {
    private Graph graph;
    public Menu(){}

    public void mainMenu() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean checkFile;
        int option;
        char g, tree, canvas, tables;
        //todo define as global
        BinarySearchTree treeBST;
        NodeTree root = null;
        Rectangle rTree;
        //NodeRTree rootR = null;
        long startTime;

        do {
            option = startMenu(sc);
            switch (option) {

                case 1:

                    do {

                        g = graphMenu(sc);
                        switch (g) {
                            case 'A':
                                readGraphFile(true, sc);
                                graph.exploreNetwork();
                                break;
                            case 'B':
                                readGraphFile(true, sc);
                                graph.recommendUsers(sc);
                                break;
                            case 'C':
                                readGraphFile(false, sc);
                                graph.contextualizeDrama();
                                break;
                            case 'D':
                                readGraphFile(true, sc);
                                graph.networking(sc);
                                break;
                            case 'E':
                                System.out.println((""));
                                break;
                            default:
                                System.out.println("\nWrong option. Please try again.\n");
                                break;
                        }
                    } while (g != 'E');

                    break;

                case 2:
                    startTime = System.nanoTime();
                    treeBST = TreeFunctions.read();
                    System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");

                    do {
                        tree = treeMenu(sc);
                        switch (tree) {
                            case 'A' -> TreeFunctions.addAlgorithm(treeBST);
                            case 'B' -> TreeFunctions.delete(treeBST);

                            //BinarySearchTree.deleteAlgorithm(66);
                            case 'C' -> {
                                System.out.println();
                                BinarySearchTree.displayOrder();
                            }
                            case 'D' -> TreeFunctions.algorithmSearchExact();

                            //TreeFunctions.displayAlgorithm(root);
                            case 'E' -> TreeFunctions.algorithmSearchRange();

                            //TreeFunctions.displayAlgorithm(root);
                            case 'F' -> System.out.println((""));
                        }
                    } while (tree != 'F');

                    break;
                case 3:
                    startTime = System.nanoTime();
                    rTree = RTreeFunctions.read();
                    System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
                    do {
                        canvas = canvasMenu(sc);
                        switch (canvas) {
                            case 'A':
                                RTreeFunctions.addCircle(rTree);
                                break;
                            case 'B':
                                RTreeFunctions.deleteCircle(rTree);
                                break;
                             case 'C':
                                 new VisualizeRect().paint(rTree, 0);
                                break;
                            case 'D':
                                double[] points;
                                points = RTreeFunctions.searchArea();
                                System.out.println();
                                RTreeFunctions.convenientArea(rTree, points);
                                break;
                            case 'E':
                                RTreeFunctions.searchLikeness(rTree);
                                break;
                            case 'F':
                                System.out.println();
                                break;
                        }
                    } while (canvas != 'F');

                    break;
                case 4:
                    startTime = System.nanoTime();
                    Map map = TablesFunctions.read();
                    System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
                    do {
                        tables = tablesMenu(sc);
                        switch (tables) {
                            case 'A' -> TablesFunctions.addBusiness(map);
                            case 'B' -> TablesFunctions.removeBusiness(map);
                            case 'C' -> TablesFunctions.checkBusiness(map);
                            case 'D' -> TablesFunctions.showBusinessByDay(map);
                            case 'E' -> System.out.println((""));
                        }
                    } while (tables != 'E');

                    break;
                case 5:
                    System.out.println("\nShutting down LinkedTree...");
                    break;

            }
        } while (option != 5);
    }

    private int startMenu (Scanner input) {
        int option;

        do {
            System.out.println(".* LinkedTree *.\n");
            System.out.println("""
                    1. Followers (Graphs)
                    2. Feed (Trees)
                    3. Canvas (R-Trees)
                    4. Advertising (TABLES)

                    5. Exit
                    """);
            System.out.print("Choose an option: ");
            option = input.nextInt();
        } while (option < 1 || 5 < option);

        return option;
    }

    private char graphMenu (Scanner input) {
        char option;

        System.out.println("""

                A. Explore the network
                B. Suggest users
                C. Contextualize drama
                D. Networking

                E. Go back
                """);
        System.out.print("What functionality do you want to run? ");
        option = input.next().charAt(0);

        return option;
    }

    private char treeMenu (Scanner input) {
        char option;

        System.out.println("""

                A. Add algorithm
                B. Remove algorithm
                C. List algorithms
                D. Search by timestamp (exact)
                E. Search by timestamp (range)

                F. Go back
                """);
        System.out.print("What functionality do you want to run? ");
        option = input.next().charAt(0);

        return option;
    }

    private char tablesMenu (Scanner input) {
        char option;

        System.out.println("""

                A. Add business
                B. Delete business
                C. Check business
                D. Weekly histogram

                E. Go back
                """);
        System.out.print("What functionality do you want to run? ");
        option = input.next().charAt(0);

        return option;
    }

    private char canvasMenu (Scanner input) {
        char option;

        System.out.println("""

                A. Add circle
                B. Remove circle
                C. Visualize
                D. Search by area
                E. Special search

                F. Go back
                """);
        System.out.print("What functionality do you want to run? ");
        option = input.next().charAt(0);

        return option;
    }


    private void readGraphFile(boolean type, Scanner sc){
        boolean checkFile;
        do {
            checkFile = true;
            System.out.print("Please enter the filename: ");
            try {
                if(type)
                    graph = new ReadFile().read("src/Graphs/GraphsFiles/" + sc.next());
                else
                    graph = new ReadFile().read("src/Graphs/DramaFiles/" + sc.next());
            } catch (FileNotFoundException e) {
                System.out.println("\nWrong path. Please write 'filename'.paed\n");
                checkFile = false;
            }
        }while(!checkFile);
    }
}
