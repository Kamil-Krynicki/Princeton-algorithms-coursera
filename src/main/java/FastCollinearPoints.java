

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by K on 2016-09-26.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("points == null");

        if (points.length < 2) {
            this.lineSegments = new LineSegment[0];
            return;
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeated points");
                }
            }
        }

        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        if (pointsCopy.length < 4) {
            this.lineSegments = new LineSegment[0];
            return;
        }

        List<LineSegment> tmpSegments = new LinkedList<>();

        for (int i = 0; i < pointsCopy.length - 3; i++) {

            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());

            for (int from = 1, to = 2; to < pointsCopy.length; to++) {
                while (to < pointsCopy.length
                        && Double.compare(pointsCopy[0].slopeTo(pointsCopy[from]), pointsCopy[0].slopeTo(pointsCopy[to])) == 0) {
                    to++;
                }
                if (to - from > 2 && pointsCopy[0].compareTo(pointsCopy[from]) < 0) {
                    tmpSegments.add(new LineSegment(pointsCopy[0], pointsCopy[to - 1]));
                }
                from = to;
            }
        }

        this.lineSegments = tmpSegments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(this.lineSegments, this.lineSegments.length);
    }
}