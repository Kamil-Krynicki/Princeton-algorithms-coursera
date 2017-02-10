package org.krynicki.princeton;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

/**
 * Created by kamil.krynicki on 09/02/2017.
 */
public class SeamCarver {
    private static final double MAX_ENERGY = 1000.0d;
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(notNull(picture));
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // difference of pixel at column x and row y
    public double energy(int x, int y) {
        return energy(picture, x, y);
    }

    private double energy(Picture pic, int x, int y) {
        validate(pic, x, y);

        if (isEdge(pic, x, y))
            return MAX_ENERGY;

        double vEnergy = difference(pic.get(x, y + 1), pic.get(x, y - 1));
        double hEnergy = difference(pic.get(x + 1, y), pic.get(x - 1, y));

        return Math.sqrt(vEnergy + hEnergy);
    }

    private double difference(Color a, Color b) {
        int bDiff = a.getBlue() - b.getBlue();
        int gDiff = a.getGreen() - b.getGreen();
        int rDiff = a.getRed() - b.getRed();
        return bDiff * bDiff + gDiff * gDiff + rDiff * rDiff;
    }

    private boolean isEdge(Picture pic, int x, int y) {
        return x == 0 || y == 0 || x == pic.width() - 1 || y == pic.height() - 1;
    }

    private void validate(Picture pic, int x, int y) {
        if (x < 0 || x > pic.width() - 1) throw new IndexOutOfBoundsException();
        if (y < 0 || y > pic.height() - 1) throw new IndexOutOfBoundsException();
    }

    private void validate(Picture pic, int[] seam) {
        if (pic.width() <= 1)
            throw new IllegalArgumentException();
        if (pic.height() != seam.length)
            throw new IllegalArgumentException();

        for (int aSeam : seam)
            if (aSeam < 0 || aSeam > pic.width() - 1)
                throw new IllegalArgumentException();

        for (int i = 1; i < seam.length; i++)
            if (Math.abs(seam[i - 1] - seam[i]) > 1)
                throw new IllegalArgumentException();
    }

    private <T> T notNull(T o) {
        if (o == null) throw new NullPointerException();
        return o;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return findSeam(rot(picture));
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findSeam(picture);
    }

    private int[] findSeam(Picture pic) {
        int w = pic.width();
        int h = pic.height();

        double[] prvCosts;
        double[] nextCosts = new double[w];
        int[][] parents = new int[h][w];

        Arrays.fill(nextCosts, MAX_ENERGY);
        Arrays.fill(parents[0], 0);


        for (int y = 0; y < h - 1; y++) {
            prvCosts = nextCosts;
            nextCosts = new double[w];
            Arrays.fill(nextCosts, Double.MAX_VALUE);

            for (int x = 1; x < w - 1; x++) {
                if (nextCosts[x - 1] > prvCosts[x] + energy(pic, x - 1, y + 1)) {
                    nextCosts[x - 1] = prvCosts[x] + energy(pic, x - 1, y + 1);
                    parents[y + 1][x - 1] = x;
                }

                if (nextCosts[x + 1] > prvCosts[x] + energy(pic, x + 1, y + 1)) {
                    nextCosts[x + 1] = prvCosts[x] + energy(pic, x + 1, y + 1);
                    parents[y + 1][x + 1] = x;
                }

                if (nextCosts[x] > prvCosts[x] + energy(pic, x, y + 1)) {
                    nextCosts[x] = prvCosts[x] + energy(pic, x, y + 1);
                    parents[y + 1][x] = x;
                }
            }
        }
        return minPath(min(nextCosts), parents);
    }

    private int[] minPath(int start, int[][] parents) {
        int[] result = new int[parents.length];
        int next = start;
        int y = parents.length;

        while (--y >= 0) {
            next = parents[y][result[y] = next];
        }

        return result;
    }

    private int min(double[] nextCosts) {
        double minCost = nextCosts[0];
        int minCostIndex = 0;

        for (int i = 1; i < nextCosts.length; i++) {
            if (nextCosts[i] < minCost) {
                minCost = nextCosts[i];
                minCostIndex = i;
            }
        }
        return minCostIndex;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        picture = rot(removeSeam(rot(picture), notNull(seam)));
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        picture = removeSeam(picture, notNull(seam));
    }

    private Picture removeSeam(Picture pic, int[] seam) {
        validate(pic, seam);

        Picture out = new Picture(pic.width() - 1, pic.height());

        for (int y = 0; y < pic.height(); y++) {
            for (int x = 0; x < seam[y]; x++)
                out.set(x, y, pic.get(x, y));

            for (int x = seam[y] + 1; x < pic.width(); x++)
                out.set(x - 1, y, pic.get(x, y));
        }

        return out;
    }

    private Picture rot(Picture pic) {
        Picture rotated = new Picture(pic.height(), pic.width());

        for (int x = 0; x < pic.width(); x++)
            for (int y = 0; y < pic.height(); y++)
                rotated.set(y, x, pic.get(x, y));

        return rotated;
    }

    public static void main(String... args) {
        long t1 = System.currentTimeMillis();
        Picture image = new Picture(new File(args[0]));

        SeamCarver sc = new SeamCarver(image);

        print(sc);

        System.out.println(Arrays.toString(sc.findVerticalSeam()));


        sc.removeVerticalSeam(sc.findVerticalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findVerticalSeam()));

        sc.removeVerticalSeam(sc.findVerticalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findVerticalSeam()));

        sc.removeVerticalSeam(sc.findVerticalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findVerticalSeam()));

        sc.removeVerticalSeam(sc.findVerticalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findVerticalSeam()));

        sc.removeVerticalSeam(sc.findVerticalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findVerticalSeam()));

        //sc.removeVerticalSeam(sc.findVerticalSeam());

        //print(sc);
        System.out.println(Arrays.toString(sc.findVerticalSeam()));

        image = new Picture(new File(args[0]));

        sc = new SeamCarver(image);

        print(sc);

        System.out.println(Arrays.toString(sc.findHorizontalSeam()));


        sc.removeHorizontalSeam(sc.findHorizontalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findHorizontalSeam()));

        sc.removeHorizontalSeam(sc.findHorizontalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findHorizontalSeam()));

        sc.removeHorizontalSeam(sc.findHorizontalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findHorizontalSeam()));

        sc.removeHorizontalSeam(sc.findHorizontalSeam());

        print(sc);
        System.out.println(Arrays.toString(sc.findHorizontalSeam()));

        long t2 = System.currentTimeMillis();

        System.out.println(t2 - t1);
    }

    private static void print(SeamCarver sc) {
        for (int y = 0; y < sc.height(); y++) {
            for (int x = 0; x < sc.width(); x++) {
                System.out.print(sc.energy(x, y));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
