package a05;

import edu.princeton.cs.algs4.*;

public class PointST<Value> {

    RedBlackBST<Point2D, Value> rbt;

    // construct an empty symbol table of points
    public PointST() {
        rbt = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() { return rbt.isEmpty(); }

    // number of points
    public int size() { return rbt.size(); }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if(p.equals(null)) { throw new NullPointerException(); }

        rbt.put(p, val);

    }

    // value associated with point p
    public Value get(Point2D p) {
        if(p.equals(null)) { throw new NullPointerException(); }

        return rbt.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if(p.equals(null)) { throw new NullPointerException(); }

        return rbt.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return rbt.keys();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if(rect.equals(null)) { throw new NullPointerException(); }

        Point2D xPoint = new Point2D(rect.xmin(), rect.xmax());
        Point2D yPoint = new Point2D(rect.ymin(), rect.ymax());
        Queue<Point2D> inRange = new Queue<Point2D>();

        for(Point2D point: rbt.keys()) {
            if(rect.contains(point)) inRange.enqueue(point);
        }

        return inRange;
    }

    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if(p.equals(null)) { throw new NullPointerException(); }
        else if(rbt.isEmpty()) { return null; }

        double minDistance = rbt.select(0).distanceTo(p);
        Point2D nearest = null;

        for(Point2D point : rbt.keys()) {
            if(nearest == null) { nearest = point; }
            else {
                double distance = point.distanceTo(p);
                if(distance < minDistance) {
                    nearest = point;
                    minDistance = distance;
                }
            }

        }

        return nearest;
    }

    // unit testing of the methods (not graded)
    public static void main(String[] args) {

    }

}