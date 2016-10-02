package org.krynicki.princeton;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

/**
 * Created by K on 2016-09-29.
 */
public class Solver {

    private static final Comparator<SearchNode> COMPARATOR = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return (o1.board().manhattan() + o1.moves()) - (o2.board().manhattan() + o2.moves());
        }
    };

    private SearchNode solution;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("initial == null");

        Board twin = initial.twin();

        MinPQ<SearchNode> mainSearch = new MinPQ<>(20, COMPARATOR);
        MinPQ<SearchNode> twinSearch = new MinPQ<>(20, COMPARATOR);

        mainSearch.insert(new SearchNode(initial));
        twinSearch.insert(new SearchNode(twin));

        SearchNode current;

        do {
            current = mainSearch.delMin();

            if (current.board().isGoal()) {
                solvable = true;
                solution = current;
                return;
            }

            for (Board newBoard : current.board().neighbors()) {
                if (!current.hasVisited(newBoard)) {
                    mainSearch.insert(new SearchNode(newBoard, current));
                }
            }

            current = twinSearch.delMin();

            if (current.board().isGoal()) {
                solvable = false;
                solution = null;
                return;
            }


            for (Board newBoard : current.board().neighbors()) {
                if (!current.hasVisited(newBoard)) {
                    twinSearch.insert(new SearchNode(newBoard, current));
                }
            }
        }
        while (true);
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable) {
            return solution.moves();
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable) {
            Stack<Board> result = new Stack<>();
            SearchNode sn = solution;
            while (sn != null) {
                result.push(sn.board());
                sn = sn.parent;
            }

            return result;
        } else {
            return null;
        }
    }

    private class SearchNode {
        private Board thisBoard;
        private SearchNode parent;

        private int moves;

        public SearchNode(Board thisBoard) {
            this.thisBoard = thisBoard;
            this.moves = 0;
            this.parent = null;
        }

        public SearchNode(Board thisBoard, SearchNode parent) {
            this.thisBoard = thisBoard;
            this.parent = parent;
            this.moves = parent.moves + 1;
        }

        public boolean hasVisited(Board board) {
            if (thisBoard.equals(board)) return true;
            if (parent == null) return false;

            return parent.hasVisited(board);
        }

        public Board board() {
            return thisBoard;
        }

        public int moves() {
            return moves;
        }
    }
}
