package org.krynicki.princeton;

/**
 * Created by K on 2016-09-29.
 */
public class Solver {
    public Solver(Board initial)           {}// find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()            {return false;}// is the initial board solvable?
    public int moves()                     {return -1;}// min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()      {return null;}// sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {}// solve a slider puzzle (given below)
}
