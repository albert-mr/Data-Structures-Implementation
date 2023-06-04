package RTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class RTreeFunctions {
    private static Rectangle rTree;

    /**
     * Reads the RTree file and creates the RTree
     * @return RTree
     */
    public static Rectangle read() throws FileNotFoundException {

        File f = new File("src/RTree/RTreeFiles/rtreeS.paed");
        Scanner parameter = new Scanner(f);
        int n, i;
        float x, y, radius;
        String color, tmp, aux;
        String[] all;

        n = parameter.nextInt();
        aux = parameter.nextLine();

        tmp = parameter.nextLine();
        all = tmp.split(";");
        x = Float.parseFloat(all[0]);
        y = Float.parseFloat(all[1]);
        radius = Float.parseFloat(all[2]);
        color = all[3];
        System.out.println(x + " " + y + " " + radius + " " + color);
        rTree = new Rectangle(new Circle(new Point(x, y), radius, color));

        for (i = 0; i < n - 1; i++) {
            tmp = parameter.nextLine();
            all = tmp.split(";");
            x = Float.parseFloat(all[0]);
            y = Float.parseFloat(all[1]);
            radius = Float.parseFloat(all[2]);
            color = all[3];
            System.out.println(x + " " + y + " " + radius + " " + color);
            rTree.insertPoint(new Circle(new Point(x, y), radius, color));
        }

        return rTree;
    }

    /**
     * Adds a circle to the RTree
     */
    public static void addCircle(Rectangle rTree) {
        float x, y, radius;
        String color;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the X coordinate of the circle's center: ");
        x = sc.nextFloat();
        System.out.print("Enter the Y coordinate of the circle's center: ");
        y = sc.nextFloat();
        System.out.print("Enter the circle's radius:");
        radius = sc.nextFloat();
        System.out.print("Enter the circle's color: ");
        color = sc.next();
        rTree.insertPoint(new Circle(new Point(x, y), radius, color));
        System.out.println();
    }

    /**
     * Ask for the information to delete a circle
     */
    public static void deleteCircle(Rectangle rTree) {
        double x, y;
        String color;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the X coordinate of the circle's center: ");
        x = sc.nextDouble();
        System.out.print("Enter the Y coordinate of the circle's center: ");
        y = sc.nextDouble();
        if (rTree.delete(new Circle(new Point(x,y),0, ""))) {
            System.out.println("\nThe circle was correctly removed from the canvas. ");
        } else {
            System.out.println("Circle not found");
        }
    }

    /**
     * Search by area
     * @return the area
     */
    public static double[] searchArea() {

        String all;
        String[] all1;
        double[] d = new double[4];
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the rectangle's first point (X,Y): ");
        all = s.nextLine();
        all1 = all.split(",");
        d[0] = Double.parseDouble(all1[0]);
        d[1] = Double.parseDouble(all1[1]);

        System.out.println("Enter the rectangle's second point (X,Y): ");
        all = s.nextLine();
        all1 = all.split(",");
        d[2] = Double.parseDouble(all1[0]);
        d[3] = Double.parseDouble(all1[1]);

        return d;
    }

    /**
     * Search by distance
     * @param r the rectangle
     * @param points the points
     */
    public static void convenientArea(Figure r, double[] points) {
        for (int i = 0; i < ((Rectangle)r).children.size(); i++) {
            if (((Rectangle)r).children.get(0) instanceof Circle){
                if (isInside(((Circle)((Rectangle)r).children.get(i)), points))
                    printPoints(((Circle)((Rectangle)r).children.get(i)));
            } else {
                convenientArea(((Rectangle) r).children.get(i), points);
            }
        }

    }

    /**
     * If the circle is inside the rectangle
     * @param f the circle
     * @param p the rectangle
     * @return true if the circle is inside the rectangle
     */
    public static boolean isInside(Circle f, double[] p) {
        return ((f.urCorner.x <= p[2] && p[0] <= f.dlCorner.x) && (f.urCorner.y <= p[3] && p[1] <= f.dlCorner.y));
    }

    /**
     * Prints the circle's information
     * @param r the circle
     */
    public static void printPoints(Circle r) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        System.out.println((r.color) + " (" + df.format(r.center.x) + "," + df.format(r.center.y) + ") r=" + df.format(r.radius));
    }

    public static void searchLikeness(Rectangle r) {
        float x, y, radius;
        String color;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the X coordinate of the circle's center: ");
        x = sc.nextFloat();
        System.out.print("Enter the Y coordinate of the circle's center: ");
        y = sc.nextFloat();
        System.out.print("Enter the circle's radius:");
        radius = sc.nextFloat();
        System.out.print("Enter the circle's color: ");
        color = sc.next();
        printLikeness(color, r);
    }

    public static void printLikeness (String color, Rectangle r){
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);

        for (int i = 0; i < ((Rectangle)r).children.size(); i++) {
            if (((Rectangle)r).children.get(0) instanceof Circle){
                if (((Circle)((Rectangle)r).children.get(i)).color.equals(color)) {
                    System.out.println("Color: " + (color) + "is equal in circle with center at (" + df.format(r.center.x) + "," + df.format(r.center.y));
                }
            } else {
                printLikeness(color, (Rectangle) r.children.get(i));
            }
        }
    }

}


