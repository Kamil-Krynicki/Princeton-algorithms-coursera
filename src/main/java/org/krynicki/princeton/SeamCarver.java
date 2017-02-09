package org.krynicki.princeton;

import edu.princeton.cs.introcs.Picture;

import java.awt.*;
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
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return w();
    }

    // height of current picture
    public int height() {
        return h();
    }

    private int w() {
        return picture.width();
    }

    public int h() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validate(x, y);

        if (isEdge(x, y))
            return MAX_ENERGY;

        double vEnergy = energy(picture.get(x, y + 1), picture.get(x, y - 1));
        double hEnergy = energy(picture.get(x + 1, y), picture.get(x - 1, y));

        return Math.sqrt(vEnergy * vEnergy + hEnergy * hEnergy);
    }

    private double energy(Color a, Color b) {
        int bDiff = a.getBlue() - b.getBlue();
        int gDiff = a.getGreen() - b.getGreen();
        int rDiff = a.getRed() - b.getRed();
        return bDiff * bDiff + gDiff * gDiff + rDiff * rDiff;
    }

    private boolean isEdge(int x, int y) {
        return x == 0 || y == 0 || x == w() - 1 || y == h() - 1;
    }

    private void validate(int x, int y) {
        if (x < 0 || x > w() - 1) throw new IndexOutOfBoundsException();
        if (y < 0 || y > h() - 1) throw new IndexOutOfBoundsException();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] costs = new double[w()][h()];
        int[][] parents = new int[w()][h()];

        Arrays.fill(costs[0], MAX_ENERGY);
        Arrays.fill(parents[0], 0);


        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[] prvCosts = new double[w()];
        double[] nextCosts = new double[w()];
        int[][] parents = new int[w()][h()];

        Arrays.fill(prvCosts, MAX_ENERGY);
        Arrays.fill(parents[0], 0);


        for (int y = 0; y < h()-1; y++) {
            prvCosts = nextCosts;
            nextCosts = new double[w()];
            Arrays.fill(nextCosts, Double.MAX_VALUE);

            for (int x = 1; x < w() - 1; x++) {
                if(nextCosts[x-1] > prvCosts[x] + energy(x-1, y+1)) {
                    nextCosts[x-1] = prvCosts[x] + energy(x-1, y+1);
                    parents[x-1][y+1] = x;
                }

                if(nextCosts[x+1] > prvCosts[x] + energy(x+1, y+1)) {
                    nextCosts[x+1] = prvCosts[x] + energy(x+1, y+1);
                    parents[x+1][y+1] = x;
                }

                if(nextCosts[x] > prvCosts[x] + energy(x, y+1)) {
                    nextCosts[x] = prvCosts[x] + energy(x, y+1);
                    parents[x][y+1] = x;
                }
            }
        }

        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        return;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        return;
    }

    public static void main(String... args) {
        Picture image  = new Picture(new File(args[0]));

        SeamCarver sc = new SeamCarver(image);

        sc.findVerticalSeam();
    }
}
