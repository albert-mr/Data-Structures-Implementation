package RTree;

public class Point {
    public double x;
    public double y;

    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    public void pointMin(Point p){
        this.x = Math.min(this.x, p.x);
        this.y = Math.min(this.y, p.y);
    }

    public void pointMax(Point p){
        this.x = Math.max(this.x, p.x);
        this.y = Math.max(this.y, p.y);
    }

    public double calculateArea(double x, double y){
         return (Math.abs(this.x - x) * Math.abs(this.y - y));
    }
}
