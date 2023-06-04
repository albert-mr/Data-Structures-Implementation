package Graphs;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class ReadFile {

    /**
     * Reads a file and returns a list of nodes
     * @param filename name of the file
     * @return Graph
     */
    public Graph read (String filename) throws FileNotFoundException {
        Graph g;
        long start = System.nanoTime();
        File f = new File (filename);
        Scanner sc = new Scanner(f);
        int N, F, cnt = 0;
        String tmp, interestmp;
        String[] information;
        String[] interests;
        Node[] nodes;

        N = sc.nextInt();
        nodes = new Node[N];
        g = new Graph();
        tmp = sc.nextLine();

        for (int i = 0; i < N; i++) {
            tmp = sc.nextLine();
            information = tmp.split(";");
            if(information.length > 3) {
                interestmp = information[information.length - 1];
                interests = interestmp.split(",");
            }else{
                interests = new String[0];
            }

            nodes[cnt] = new Node(Integer.parseInt(information[0]),information[1],information[2],interests);
            cnt++;
        }

        g.addNodes(nodes);
        F = sc.nextInt();
        tmp = sc.nextLine();

        for (int i = 0; i < F; i++){
            tmp = sc.nextLine();
            information = tmp.split(";");
            g.assignEdge(Integer.parseInt(information[0]),Integer.parseInt(information[1]),Integer.parseInt(information[2]),Integer.parseInt(information[3]));
        }
        long end = (System.nanoTime() - start);
        System.out.println("The file was read in " + end/1000000 + "." + (end%1000000)/1000 + " milliseconds.\n");
        return g;
    }
}