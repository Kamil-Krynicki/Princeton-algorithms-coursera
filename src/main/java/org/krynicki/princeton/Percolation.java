package org.krynicki.princeton;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by K on 2016-09-06.
 */
public class Percolation {

    private boolean[][] state;
    private WeightedQuickUnionUF fullChecker;
    private WeightedQuickUnionUF uf;
    private int n;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n <= 0");

        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2); // two extra field for the percolation start and percolation end
        this.fullChecker = new WeightedQuickUnionUF(n * n + 2); // two extra field for the percolation start and percolation end
        this.state = new boolean[n][n];
    }

    private void connectUpNode(int column1) {
        uf.union(column1 - 1, n * n);
        fullChecker.union(column1 - 1, n * n);
    }

    private void connectDownNode(int column1) {
        uf.union(n * (n - 1) + (column1 - 1), n * n + 1);
    }

    private void connect(int row1, int column1, int row2, int column2) {
        uf.union(n * (row1 - 1) + (column1 - 1), n * (row2 - 1) + (column2 - 1));
        fullChecker.union(n * (row1 - 1) + (column1 - 1), n * (row2 - 1) + (column2 - 1));
    }

    private boolean isConnected(int row1, int column1, int row2, int column2) {
        return uf.connected(n * (row1 - 1) + (column1 - 1), n * (row2 - 1) + (column2 - 1));
    }

    // open site (row i, column j) if it is not open already
    public void open(int row, int column) {
        checkRange(row, column);

        if (!isOpen(row, column)) {
            setOpen(row, column);

            if (row == 1) {
                connectUpNode(column);
            }

            if (row == n) {
                connectDownNode(column);
            }

            if (row > 1) {
                if (isOpen(row - 1, column)) {
                    connect(row, column, row - 1, column);
                }
            }

            if (row < n) {
                if (isOpen(row + 1, column)) {
                    connect(row, column, row + 1, column);
                }
            }

            if (column > 1) {
                if (isOpen(row, column - 1)) {
                    connect(row, column, row, column - 1);
                }
            }

            if (column < n) {
                if (isOpen(row, column + 1)) {
                    connect(row, column, row, column + 1);
                }
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int row, int column) {
        checkRange(row, column);

        return state[row - 1][column - 1];
    }

    private void setOpen(int row, int column) {
        checkRange(row, column);

        state[row - 1][column - 1] = true;
    }


    // is site (row i, column j) full?
    public boolean isFull(int row, int column) {
        checkRange(row, column);

        return fullChecker.connected(n * (row - 1) + column - 1, n * n);
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(n * n + 1, n * n);
    }

    private void checkRange(int row, int column) {
        if (row < 1 || row > n || column < 1 || column > n) throw new IndexOutOfBoundsException();
    }

    public void printState() {
        for (int i = 1; i <= n; i++) {
            System.out.print("=");
        }
        System.out.println();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (!isOpen(i, j)) {
                    System.out.print("X");
                } else if (isFull(i, j)) {
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        for (int i = 1; i <= n; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
