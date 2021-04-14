package a05;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.NoSuchElementException;

public class KdTreeST<Key extends Comparable<Key>, Value>{
    private Node root;             // root of BST
    private int size;
    RedBlackBST<Point2D, Value> rbt = new RedBlackBST<>();

    private class Node {
        private Point2D p;      // the point
        private Value value;    // the symbol table maps the point to this value
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D point, Value val, RectHV rect) {
            this.p = point;
            this.value = val;
            this.rect = rect;
        }
    }

    // construct an empty symbol table of points
    public KdTreeST(Node node) {
        rbt.put(node.p, node.value);

    }

    // is the symbol table empty?
    public boolean isEmpty () {
        return size() == 0;
    }

    // number of points
    public int size () {
        return size();
    }

    // associate the value val with point p
    public void put (Node node){
        if (p.equals(null)) {
            throw new NullPointerException();
        }

        rbt.put(node.p, node.value);

    }

    // value associated with point p
    public Value get (Node x, Point2D point){
        if(!contains(point)) { throw new IllegalArgumentException(); }
        if (point == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;

        int cmp = point.compareTo(x.p);
        if      (cmp < 0) return get(x.lb, point);
        else if (cmp > 0) return get(x.rt, point);
        else              return x.value;
    }

    // does the symbol table contain point p?
    public boolean contains (Point2D p){
        if (p.equals(null)) {
            throw new NullPointerException();
        }

        return rbt.contains(p);
    }

    //all points in the symbol table
    public Iterable<Point2D> points () {
        if (isEmpty()) return new Queue<Point2D>();
        return (min(), max());

    }
    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Point2D min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).p;
    }

    private Node min(Node x) {
        if (x.lb == null) return x;
        else return min(x.lb);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Point2D max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).p;
    }

    private Node max(Node x) {
        if (x.rt == null) return x;
        else                 return max(x.rt);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range (RectHV rect){
        if (rect.equals(null)) {
            throw new NullPointerException();
        }

        Point2D xPoint = new Point2D(rect.xmin(), rect.xmax());
        Point2D yPoint = new Point2D(rect.ymin(), rect.ymax());
        Queue<Point2D> inRange = new Queue<Point2D>();

        for (Point2D point : rbt.keys()) {
            if (rect.contains(point)) inRange.enqueue(point);
        }

        return inRange;
    }

    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest (Point2D p){
        if (p.equals(null)) {
            throw new NullPointerException();
        } else if (rbt.isEmpty()) {
            return null;
        }

        double minDistance = rbt.select(0).distanceTo(p);
        Point2D nearest = null;

        for (Point2D point : rbt.keys()) {
            if (nearest == null) {
                nearest = point;
            } else {
                double distance = point.distanceTo(p);
                if (distance < minDistance) {
                    nearest = point;
                    minDistance = distance;
                }
            }

        }

        return nearest;
    }

    // unit testing of the methods (not graded)
    public static void main (String[]args){

    }
}