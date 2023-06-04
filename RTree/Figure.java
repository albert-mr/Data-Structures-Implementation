package RTree;

public abstract class Figure {
    public Rectangle parent;
    public Point center;

    public abstract Point figureMinPoint();
    public abstract Point figureMaxPoint();
    public abstract double distance(Figure f);

}
