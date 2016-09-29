package org.krynicki.princeton;

import java.util.Arrays;

/**
 * Created by K on 2016-09-29.
 */
public class Board {
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column
    private int[][] blocks;
    private int blancI;
    private int blancJ;

    private int moves = 0;
    private Board parent = null;

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];

        for(int i=0;i<blocks.length;i++){
            for (int j=0;j<blocks[i].length;j++){
                if(blocks[i][j]==0){
                    blancI = i;
                    blancJ = j;
                }
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        int value = 1;
        for(int i=0;i<blocks.length;i++){
            for (int j=0;j<blocks[i].length;j++){
                if(blocks[i][j]!=value && blocks[i][j]!=0){
                    result++;
                }
                value++;
            }
        }
        return result+moves;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        int value = 1;
        for(int i=0;i<blocks.length;i++){
            for (int j=0;j<blocks[i].length;j++){
                if(blocks[i][j]!=value && blocks[i][j]!=0){
                    result+=Math.abs((blocks[i][j]-1)/dimension() - i) + Math.abs((blocks[i][j]-1)%dimension() - j);
                }
                value++;
            }
        }
        return result+moves;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int value = 1;
        for(int i=0;i<blocks.length;i++){
            for (int j=0;j<blocks[i].length;j++){
                if(blocks[i][j]!=value && blocks[i][j]!=0){
                    return false;
                }
                value++;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuffer value = new StringBuffer();
        for(int i=0;i<blocks.length;i++){
            for (int j=0;j<blocks[i].length;j++){
                value.append(blocks[i][j]);
                if(j<blocks[i].length-1) {
                    value.append(" ");
                }
            }
            if(i<blocks.length-1) {
                value.append('\n');
            }
        }
        return value.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Board b2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b2.isGoal());

        System.out.println(b.toString());
        System.out.println(b2.toString());

    }
}
