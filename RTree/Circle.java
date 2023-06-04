package RTree;

public class Circle extends Figure {
    public Point dlCorner;
    public Point urCorner;
    public double radius;
    public double area;
    public String color;

    public Circle (Point p, double radius, String color) {
        this.center = p;
        this.dlCorner = new Point(center.x - radius, center.y - radius);
        this.urCorner = new Point(center.x + radius, center.y + radius);
        this.radius = radius;
        this.area = Math.pow(2*radius, 2);
        this.color = color;
    }

    public Circle (Circle c) {
        this.urCorner = c.urCorner;
        this.dlCorner = c.dlCorner;
        this.center = c.center;
        this.radius = c.radius;
        this.area = c.area;
        this.color = c.color;
    }

    @Override
    public Point figureMinPoint() {return dlCorner;}

    @Override
    public Point figureMaxPoint() {
        return urCorner;
    }

    @Override
    public double distance(Figure f) {
        return Math.sqrt(Math.pow((this.center.x - f.center.x), 2) + Math.pow((this.center.y - f.center.y), 2));
    }
}
