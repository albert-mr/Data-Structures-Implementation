package RTree;

import RTree.Circle;
import RTree.Point;
import RTree.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class VisualizeRect extends JFrame {

    /**
     * Constructor for VisualizeRect
     */
    public VisualizeRect(){
        setTitle("RTrees");
        setSize(1500,1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Method to paint the tree
     * @param r the root of the tree
     * @param p the number of nodes
     */
    //needs work
    public void paint(Rectangle r, int p) {
        Random rand = new Random();
        Graphics g = getGraphics();
        if (r.children.get(0) instanceof Circle) {
            g.setColor(Color.decode(((Circle)r.children.get(p)).color));
            p++;
            g.drawOval((int) r.figureMinPoint().x*10, (int) r.figureMinPoint().y*10, 20*(int) ((Circle) r.children.get(p)).radius, 20*(int) ((Circle) r.children.get(p)).radius);
        } else {
            for (int i = 0; i < r.children.size(); i++) {
                g.setColor(new Color(rand.nextFloat(1), rand.nextFloat(1), rand.nextFloat(1)));
                g.drawRect((int) r.children.get(i).figureMinPoint().x*10, (int) r.children.get(i).figureMinPoint().y*10, (int) r.children.get(i).figureMaxPoint().x*10, (int) r.children.get(i).figureMaxPoint().y *10);
                paint(((Rectangle)r.children.get(i)), p);
            }
        }
    }
}