

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Collections;

/**
 * Created by K on 2016-10-05.
 */
public class KdTree {
    private TreeNode points;

    public KdTree() {
        this.points = null;
    }

    public boolean isEmpty() {
        return points == null;
    }

    public int size() {
        return isEmpty() ? 0 : points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("p == null");

        if (isEmpty()) {
            points = new TreeNode(p);
        } else {
            points.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("p == null");
        return isEmpty() ? false : points.contains(p);
    }

    public void draw() {
        if (!isEmpty()) {
            points.draw(new RectHV(0, 0, 1, 1));
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("p == null");

        if (!isEmpty()) {
            Queue<Point2D> inRectangle = new Queue<>();

            points.collectRange(rect, inRectangle);

            return inRectangle;
        } else {
            return Collections.emptySet();
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("p == null");

        if (!isEmpty()) {
            return points.nearest(p, this.points.point);
        } else {
            return null;
        }
    }

    private class TreeNode {
        private final Point2D point;

        private TreeNode left;
        private RectHV leftPane;

        private TreeNode right;
        private RectHV rightPane;

        private int size;
        private boolean isVertical;

        public TreeNode(Point2D p, RectHV limit, boolean isVertical) {
            this.point = p;
            this.size = 1;

            this.left = null;
            this.right = null;
            this.isVertical = isVertical;

            if (isVertical) {
                this.leftPane = new RectHV(limit.xmin(), limit.ymin(), point.x(), limit.ymax());
                this.rightPane = new RectHV(point.x(), limit.ymin(), limit.xmax(), limit.ymax());
            } else {
                this.leftPane = new RectHV(limit.xmin(), limit.ymin(), limit.xmax(), point.y());
                this.rightPane = new RectHV(limit.xmin(), point.y(), limit.xmax(), limit.ymax());
            }
        }

        public TreeNode(Point2D p) {
            this(p, new RectHV(0, 0, 1, 1), true);
        }

        public void add(Point2D p) {
            if (this.point.equals(p)) {
                return;
            }

            TreeNode direction = closer(p);

            if (direction == null) {
                createNode(p);
                size++;
            } else {
                direction.add(p);

                if (left != null && right != null) {
                    size = left.size + right.size + 1;
                } else {
                    size = direction.size + 1;
                }
            }
        }

        private void createNode(Point2D p) {
            if (leftPane.contains(p)) {
                left = new TreeNode(p, leftPane, !isVertical);
            } else {
                right = new TreeNode(p, rightPane, !isVertical);
            }
        }

        public void draw(RectHV limit) {
            if (isVertical) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(point.x(), limit.ymin(), point.x(), limit.ymax());
            } else {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(limit.xmin(), point.y(), limit.xmax(), point.y());
            }

            if (left != null) {
                left.draw(leftPane);
            }

            if (right != null) {
                right.draw(rightPane);
            }

            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(point.x(), point.y());
            StdDraw.setPenRadius(0.001);
        }

        public Point2D nearest(Point2D p, Point2D champion) {
            Point2D nearestSoFar = p.distanceTo(champion) < p.distanceTo(this.point) ? champion : this.point;

            TreeNode closer = closer(p);
            TreeNode further = other(closer);

            if (closer != null) {
                nearestSoFar = closer.nearest(p, nearestSoFar);
            }

            if (further != null) {
                if (p.distanceTo(nearestSoFar) > this.furtherPane(p).distanceTo(p)) {
                    nearestSoFar = further.nearest(p, nearestSoFar);
                }
            }

            return nearestSoFar;
        }

        private void collectRange(RectHV rect, Queue<Point2D> collector) {
            if (rect.contains(this.point)) {
                collector.enqueue(this.point);
            }

            if (right != null && rect.intersects(rightPane)) {
                right.collectRange(rect, collector);
            }

            if (left != null && rect.intersects(leftPane)) {
                left.collectRange(rect, collector);
            }
        }

        public boolean contains(Point2D p) {
            if (p.equals(this.point)) {
                return true;
            }

            TreeNode direction = closer(p);

            return direction == null ? false : direction.contains(p);
        }

        private RectHV other(RectHV pane) {
            return pane == leftPane ? rightPane : leftPane;
        }

        private RectHV closerPane(Point2D p) {
            return leftPane.contains(p) ? leftPane : rightPane;
        }

        private RectHV furtherPane(Point2D p) {
            return leftPane.contains(p) ? rightPane : leftPane;
        }

        private TreeNode other(TreeNode node) {
            return node == left ? right : left;
        }

        private TreeNode further(Point2D p) {
            return leftPane.contains(p) ? right : left;
        }

        private TreeNode closer(Point2D p) {
            return leftPane.contains(p) ? left : right;
        }

        public int size() {
            return size;
        }
    }

}
