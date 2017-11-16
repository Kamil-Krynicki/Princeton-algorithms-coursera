

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by K on 2016-09-26.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
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

        Arrays.sort(pointsCopy);

        if (pointsCopy.length < 4) {
            this.lineSegments = new LineSegment[0];
            return;
        }


        List<LineSegment> tmpSegments = new LinkedList<>();

        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = i + 1; j < pointsCopy.length; j++) {
                for (int k = j + 1; k < pointsCopy.length; k++) {
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        if (pointsCopy[j].slopeOrder().compare(pointsCopy[i], pointsCopy[k]) == 0
                                && pointsCopy[k].slopeOrder().compare(pointsCopy[j], pointsCopy[l]) == 0) {
                            tmpSegments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
                    }
                }
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
