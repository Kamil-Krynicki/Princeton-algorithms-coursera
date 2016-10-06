package org.krynicki.princeton;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by K on 2016-09-29.
 */
public class Board {
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column
    private int[][] blocks;
    private int emptyI;
    private int emptyJ;

    private int manhattan = -1;
    private int hamming = -1;

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                }
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    private Board(Board other) {
        this(other.blocks);
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Board b2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        // System.out.println(b.hamming());
        // System.out.println(b.manhattan());
        // System.out.println(b2.isGoal());

        // System.out.println(b.toString());
        // System.out.println(b2.toString());

        System.out.println(b2.toString());
        System.out.println();
        for (Board tmp : b2.neighbors()) {
            System.out.println(tmp.toString());
            System.out.println();
        }
    }

    private void swap(int i1, int j1, int i2, int j2) {
        int tmp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = tmp;

        if (emptyI == i1 && emptyJ == j1) {
            emptyI = i2;
            emptyJ = j2;
        } else if (emptyI == i2 && emptyJ == j2) {
            emptyI = i1;
            emptyJ = j1;
        }
    }

    private Board up() {
        Board result = new Board(this);
        result.swap(result.emptyI, result.emptyJ, result.emptyI - 1, result.emptyJ);
        return result;
    }

    private Board down() {
        Board result = new Board(this);
        result.swap(result.emptyI, result.emptyJ, result.emptyI + 1, result.emptyJ);
        return result;
    }

    private Board left() {
        Board result = new Board(this);
        result.swap(result.emptyI, result.emptyJ, result.emptyI, result.emptyJ - 1);
        return result;
    }

    private Board right() {
        Board result = new Board(this);
        result.swap(result.emptyI, result.emptyJ, result.emptyI, result.emptyJ + 1);
        return result;
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int result = this.hamming;
        if (result < 0) {
            result = 0;
            int value = 1;
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[i].length; j++) {
                    if (blocks[i][j] != value && blocks[i][j] != 0) {
                        result++;
                    }
                    value++;
                }
            }
            this.hamming = result;
        }
        return result;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = this.manhattan;
        if (result < 0) {
            result = 0;
            int value = 1;
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[i].length; j++) {
                    if (blocks[i][j] != value && blocks[i][j] != 0) {
                        result += Math.abs((blocks[i][j] - 1) / dimension() - i)
                                + Math.abs((blocks[i][j] - 1) % dimension() - j);
                    }
                    value++;
                }
            }
            this.manhattan = result;
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int value = 1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != value && blocks[i][j] != 0) {
                    return false;
                }
                value++;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board result = new Board(this.blocks);
        if (this.dimension() > 1) {
            Random rand = new Random(System.currentTimeMillis());
            int i1, i2;
            int j1, j2;
            do {
                i1 = rand.nextInt(this.dimension());
                j1 = rand.nextInt(this.dimension());
            }
            while (this.blocks[i1][j1] == 0);

            do {
                i2 = rand.nextInt(this.dimension());
                j2 = rand.nextInt(this.dimension());
            }
            while (this.blocks[i2][j2] == 0 || (i2 == i1 && j2 == j1));

            result.swap(i1, j1, i2, j2);
        }
        return result;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;

        if (!(y.getClass().equals(Board.class))) return false;

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) return false;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Collection<Board> neighbors = new LinkedList<>();

        if (this.emptyI > 0) {
            neighbors.add(this.up());
        }

        if (this.emptyI < dimension() - 1) {
            neighbors.add(this.down());
        }

        if (this.emptyJ > 0) {
            neighbors.add(this.left());
        }

        if (this.emptyJ < dimension() - 1) {
            neighbors.add(this.right());
        }

        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = dimension();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
