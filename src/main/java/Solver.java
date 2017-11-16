

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

/**
 * Created by K on 2016-09-29.
 */
public class Solver {

    private SearchNode solution = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("initial == null");

        MinPQ<SearchNode> mainSearch = new MinPQ<>();
        MinPQ<SearchNode> twinSearch = new MinPQ<>();

        mainSearch.insert(new SearchNode(initial));
        twinSearch.insert(new SearchNode(initial.twin()));

        while (!hasReachedGoal(mainSearch) && !hasReachedGoal(twinSearch)) {
            step(mainSearch);
            step(twinSearch);
        }

        if(hasReachedGoal(mainSearch))
            this.solution = mainSearch.min();
    }

    private boolean hasReachedGoal(MinPQ<SearchNode> nodes) {
        return nodes.min().board().isGoal();
    }

    private void step(MinPQ<SearchNode> nodes) {
        SearchNode current = nodes.delMin();

        for (Board newBoard : current.board().neighbors()) {
            if (!current.hasVisited(newBoard)) {
                nodes.insert(new SearchNode(newBoard, current));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solution.moves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable() ? solution.history : null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private LinkedList<Board> history;

        public SearchNode(Board thisBoard) {
            history = new LinkedList<>();
            history.add(thisBoard);
        }

        public SearchNode(Board thisBoard, SearchNode parent) {
            history = new LinkedList<>(parent.history);
            history.add(thisBoard);
        }

        public boolean hasVisited(Board board) {
            return history.contains(board);
        }

        public Board board() {
            return history.getLast();
        }

        public int moves() {
            return history.size();
        }

        @Override
        public int compareTo(SearchNode that) {
            return (this.board().manhattan() + this.moves()) - (that.board().manhattan() + that.moves());
        }
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
}
