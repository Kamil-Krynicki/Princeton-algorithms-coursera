package org.krynicki.princeton;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] results;
    private int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int size, int trials) {
        if (size <= 0) throw new IllegalArgumentException("size <= 0");
        if (trials <= 0) throw new IllegalArgumentException("trials <= 0");

        this.results = new double[trials];
        this.trials = trials;

        Percolation p;

        int attemtps;

        int randCol, randRow;

        for (int i = 0; i < trials; i++) {
            attemtps = 0;
            p = new Percolation(size);

            while (!p.percolates() && attemtps < size * size) {
                attemtps++;
                do {
                    randCol = StdRandom.uniform(1, size + 1);
                    randRow = StdRandom.uniform(1, size + 1);
                } while (p.isOpen(randRow, randCol));

                p.open(randRow, randCol);
            }

            results[i] = ((double) attemtps) / (size * size);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);
    }


    // test client (described below)
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        int size = Integer.valueOf(args[0]).intValue();
        int trials = Integer.valueOf(args[1]).intValue();

        PercolationStats ps = new PercolationStats(size, trials);

        System.out.print("mean = " + ps.mean());
        System.out.print("stddev = " + ps.stddev());
        System.out.print("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());

    }
}
