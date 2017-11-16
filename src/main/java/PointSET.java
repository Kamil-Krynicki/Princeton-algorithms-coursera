

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by K on 2016-10-05.
 */
public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public static void main(String[] args) {
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("p == null");
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("p == null");
        return points.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("p == null");

        Collection<Point2D> result = new ArrayList<Point2D>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }

        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("p == null");

        Point2D nearestPoint = null;

        double minDistance = Double.MAX_VALUE;

        for (Point2D point : points) {
            if (point.distanceTo(p) < minDistance) {
                minDistance = point.distanceTo(p);
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }
}
