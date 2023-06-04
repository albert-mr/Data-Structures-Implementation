package RTree;

import java.util.ArrayList;

public class Rectangle extends Figure{

    public ArrayList<Figure> children;

    public Point dlCorner;
    public Point urCorner;
    public boolean isLeaf;
    public double area;

    /**
     * Constructor for Rectangle
     * @param fig - figure to be inserted
     */
    public Rectangle(Figure fig){
        if(fig instanceof Circle){
            dlCorner = new Point(((Circle) fig).dlCorner);
            urCorner = new Point(((Circle) fig).urCorner);
            this.children = new ArrayList<>();
            this.addFigure(fig);
        }else{
            this.dlCorner = new Point(((Rectangle)fig).dlCorner);
            this.urCorner = new Point(((Rectangle)fig).urCorner);
            this.children = new ArrayList<>();
            this.addFigure(fig);
        }
    }

    /**
     * Inserts a point into the tree
     * @param p - point to be inserted
     */
    public void insertPoint(Circle p){
        if(this.children.get(0) instanceof Circle){ //if the first child is a circle
            this.addFigure(p);
        }else{ //if the first child is a rectangle
            double minArea = Double.MAX_VALUE;
            int pos = 0;
            int closest = -1;
            double tmp;
            for(Figure f : this.children){
                tmp = areaIncrease(f, p);
                if(minArea > tmp){
                    minArea = tmp;
                    closest = pos;
                }
                pos++;
            }
            ((Rectangle)this.children.get(closest)).insertPoint(p);
            //((Rectangle_R) this.children.get(closest)).defineCorners(p);
        }

        if(this.children.size() > 3){
            this.split();
        }
    }

    /**
     * Defines the corners of the rectangle
     * @param p - p
     */
    public void defineCorners(Figure p){
        this.dlCorner.pointMin(p.figureMinPoint());
        this.urCorner.pointMax(p.figureMaxPoint());
        this.area = this.urCorner.calculateArea(this.dlCorner.x, this.dlCorner.y);
        this.center = new Point((dlCorner.x+ urCorner.x)/2,(dlCorner.y+ urCorner.y)/2 );

    }

    /**
     * Adds a figure
     * @param f
     */
    public void addFigure(Figure f){
        this.defineCorners(f);
        f.parent = this;
        this.children.add(f);
    }

    /**
     * Calculate distance
     * @param f - figure
     * @return  - distance
     */
    @Override
    public double distance(Figure f){
        return Math.sqrt(Math.pow((this.center.x - f.center.x), 2) + Math.pow((this.center.y - f.center.y), 2));
    }

    /**
     * Furthest points
     */
    public void getBiggestDistance(){
        double maxDistance = 0;
        double tmp;
        ArrayList<Figure> auxChildren = new ArrayList<>();
        for(Figure f: this.children){
            for(Figure f1: this.children){
                tmp = f.distance(f1);
                if(maxDistance < tmp){
                    maxDistance = tmp;
                    auxChildren.clear();
                    auxChildren.add(f);
                    auxChildren.add(f1);
                }
            }
        }

        for(Figure f : this.children){
            if(!f.equals(auxChildren.get(0)) && !f.equals(auxChildren.get(1))){
                auxChildren.add(f);
            }
        }

        this.children.clear();
        this.children.addAll(auxChildren);

    }

    /**
     *Calculates area
     * @param f - figure
     * @param p - point
     * @return - area increase
     */
    public double areaIncrease(Figure f, Figure p) {
        double x1, x2, y1, y2;
        x1 = Math.max(f.figureMaxPoint().x, p.figureMaxPoint().x);
        x2 = Math.min(f.figureMinPoint().x, p.figureMinPoint().x);
        y1 = Math.max(f.figureMaxPoint().y, p.figureMaxPoint().y);
        y2 = Math.min(f.figureMinPoint().y, p.figureMinPoint().y);

        return (x1 - x2) * (y1 - y2);
    }


    /**
     * Splits into 2
     */
    public void split(){
        this.getBiggestDistance();
        Rectangle firstSplit = new Rectangle(this.children.get(0));
        Rectangle secondSplit = new Rectangle(this.children.get(1));
        Rectangle tmp1, tmp2;
        for(int i = 2; i < this.children.size(); i++){

            tmp1 = new Rectangle(this.children.get(0));
            tmp1.defineCorners(this.children.get(i));
            tmp2 = new Rectangle(this.children.get(1));
            tmp2.defineCorners(this.children.get(i));
            if(tmp1.area < tmp2.area){
                firstSplit.addFigure(this.children.get(i));
            }else{
                secondSplit.addFigure(this.children.get(i));
            }
        }

        if (this.parent == null) {
            this.children.clear();
            this.addFigure(firstSplit);
            this.addFigure(secondSplit);
        } else {
            this.parent.children.remove(this);
            this.parent.addFigure(firstSplit);
            this.parent.addFigure(secondSplit);
            this.parent = null;
        }
    }

    @Override
    public Point figureMinPoint() {
        return this.dlCorner;
    }

    @Override
    public Point figureMaxPoint() {
        return this.urCorner;
    }
    public boolean isInsideRectangle(Circle p){
        return p.center.x >= this.dlCorner.x && p.center.x <= this.urCorner.x && p.center.y >= this.dlCorner.y && p.center.y <= this.urCorner.y;
    }

    public double roundNumber(double number){
        return Math.round(number * 100.0) / 100.0;
    }

    public boolean delete(Circle p) {
        boolean result = false;
        for (Figure f : this.children) {
            if (f instanceof Circle) {
                if (roundNumber(f.center.x) == p.center.x &&  roundNumber(f.center.y) == p.center.y) {
                    this.children.remove(f);
                    return true;
                }
            } else {
                if (isInsideRectangle(p)) {
                    if (((Rectangle)f).delete(p)) {
                        result = true;
                        if (((Rectangle) f).children.isEmpty()) {
                            f.parent.children.remove(f);
                            defineCorners(f.parent);
                        }
                    }
                }
            }
        }
        return result;
    }
}
