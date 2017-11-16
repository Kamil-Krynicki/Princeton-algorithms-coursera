

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kamil.krynicki on 24/02/2017.
 */
public class BoggleSolver {

    private final TrieNode dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        checkNotNull(dictionary);
        this.dictionary = new TrieNode();
        for (String word : dictionary) {
            if (word.length() > 2) {
                if (word.indexOf('Q') > -1) {
                    if (word.contains("QU")) {
                        this.dictionary.add(word.replaceAll("QU", "Q"), word);
                    }
                } else {
                    this.dictionary.add(word, word);
                }
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        checkNotNull(board);
        return search(dictionary, new boolean[board.rows() * board.cols()], board);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        checkNotNull(word);
        return dictionary.contains(word.replaceAll("QU", "Q")) ? score(word) : 0;
    }

    private int score(String word) {
        if (word.length() <= 2) return 0;
        if (word.length() <= 4) return 1;
        if (word.length() <= 5) return 2;
        if (word.length() <= 6) return 3;
        if (word.length() <= 7) return 5;

        return 11;
    }

    private List<String> search(TrieNode node, boolean[] visited, BoggleBoard board) {
        List<String> result = new ArrayList<>();

        for (int x = 0; x < board.rows(); x++)
            for (int y = 0; y < board.cols(); y++) {
                if (node.hasChild(board.getLetter(x, y))) {
                    TrieNode child = node.child(board.getLetter(x, y));
                    TrieNode newChild = search(x, y, child, visited, board, result);

                    if (child != newChild) {
                        node = node.mod(board.getLetter(x, y), newChild);
                    }
                }
            }

        return result;
    }

    private TrieNode search(int x, int y, TrieNode node, boolean[] visited, BoggleBoard board, List<String> result) {
        if (node.hasValue()) {
            result.add(node.value());
            node = node.remove();

            if (node == null)
                return null;
        }

        visited[x * board.cols() + y] = true;

        int iMax = x + 1 > board.rows() - 1 ? x : x + 1;
        int jMax = y + 1 > board.cols() - 1 ? y : y + 1;

        for (int i = x - 1 < 0 ? 0 : x - 1; i <= iMax; i++) {
            for (int j = y - 1 < 0 ? 0 : y - 1; j <= jMax; j++) {
                if (!visited[i * board.cols() + j]) {
                    if (node.hasChild(board.getLetter(i, j))) {
                        TrieNode child = node.child(board.getLetter(i, j));
                        TrieNode newChild = search(i, j, child, visited, board, result);

                        if (child != newChild) {
                            node = node.mod(board.getLetter(i, j), newChild);

                            if (node == null)
                                return null;
                        }
                    }
                }
            }
        }

        visited[x * board.cols() + y] = false;

        return node;
    }

    private void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException();
    }

    public static void main(String... args) {
        char[][] kamil = {{'A', 'A', 'C', 'A', 'C'}, {'D', 'K', 'M', 'A', 'C'}, {'G', 'L', 'M', 'A', 'I'}, {'G', 'L', 'M', 'A', 'I'}, {'G', 'L', 'M', 'A', 'I'}};
        //char[][] kamil = {{'M', 'A', 'M', 'B', 'O'}};

        In in = new In(args[0]);

        BoggleSolver boggleSolver = new BoggleSolver(in.readAllLines());
        //System.out.print(boggleSolver.scoreOf("WAS"));
        long t1 = System.currentTimeMillis();
        BoggleBoard boggleBoard = new BoggleBoard(kamil);
        for (int i = 0; i < 1E5; i++) {
            Iterable<String> allValidWords = boggleSolver.getAllValidWords(boggleBoard);
            //System.out.println(allValidWords);
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

    }

    private static class TrieNode implements Cloneable {
        static final int SIZE = 'Z' - 'A' + 1;
        private String value;

        private TrieNode[] children;

        private int count;

        TrieNode() {
            this.value = null;
            this.children = null;
            this.count = 0;
        }

        TrieNode(TrieNode node) {
            this.value = node.value;
            this.count = node.count;
            this.children = Arrays.copyOf(node.children, node.children.length);
        }

        public void add(String keys, String value) {
            if (!hasChildren()) {
                children = new TrieNode[SIZE];
            }

            if (keys.isEmpty()) {
                this.value = value;
            } else {
                int label = id(keys.charAt(0));

                if (children[label] == null) {
                    children[label] = new TrieNode();
                    count++;
                }

                children[label].add(keys.substring(1), value);
            }
        }

        public boolean contains(String word) {
            if (word.isEmpty())
                return hasValue();

            if (!hasChildren() || !hasChild(word.charAt(0)))
                return false;

            return child(word.charAt(0)).contains(word.substring(1));
        }

        public boolean hasValue() {
            return value != null;
        }

        public String value() {
            return this.value;
        }

        public boolean hasChild(char key) {
            return children != null && children[key - 'A'] != null;
        }

        public TrieNode child(char key) {
            return children[key - 'A'];
        }

        private int id(char c) {
            return c - 'A';
        }

        private boolean hasChildren() {
            return this.count > 0;
        }

        public TrieNode remove() {
            if (hasValue()) {
                TrieNode result = new TrieNode(this);
                result.value = null;
                return result;
            }

            if (hasChildren())
                return this;

            return null;
        }

        public TrieNode mod(char key, TrieNode newChild) {
            if (newChild == null && this.count == 1)
                return null;

            TrieNode result = new TrieNode(this);

            result.children[key - 'A'] = newChild;

            if (newChild == null) {
                result.count--;
            }

            return result;
        }
    }
}
