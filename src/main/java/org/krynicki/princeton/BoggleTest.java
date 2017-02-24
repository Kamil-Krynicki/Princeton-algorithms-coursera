package org.krynicki.princeton;

/**
 * Created by kamil.krynicki on 24/02/2017.
 */
public class BoggleTest {
    boolean[][] visited;
    char[][] board;
    private final int x;
    private final int y;
    private char[] text;

    public BoggleTest(char[][] board) {
        checkNotNull(board);
        checkArgument(board.length > 0);
        checkArgument(board[0].length > 0);

        x = board.length + 2;
        y = board[0].length + 2;

        this.visited = new boolean[x][y];

        for (int i = 0; i < x; i++) {
            this.visited[i][0] = true;
            this.visited[i][y - 1] = true;
        }

        for (int j = 0; j < y; j++) {
            this.visited[0][j] = true;
            this.visited[x - 1][j] = true;
        }

        this.board = board;
        this.text = new char[(x - 2) * (y - 2)];
    }

    public void search(int x, int y, int letter) {
        this.visited[x][y] = true;
        this.text[letter] = board[x-1][y-1];

        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if (!visited[i][j]) {
                    //System.out.println(Arrays.toString(text));
                    search(i, j, letter + 1);
                }

        this.text[letter] = 0;
        this.visited[x][y] = false;
    }

    private void checkArgument(boolean b) {
        if (!b) throw new IllegalArgumentException();
    }

    private void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException();
    }

    public static void main(String... args) {
        char[][] kamil = {{'a', 'a', 'c'}, {'d', 'k', 'm'}, {'g', 'l', 'i'}};

        long t1 = System.currentTimeMillis();
        BoggleTest boggleTest = new BoggleTest(kamil);
        for (int i = 0; i < 1E3; i++) {
            for (int x = 1; x < 4; x++)
                for (int y = 1; y < 4; y++) {
                    boggleTest.search(x, y, 0);
                }
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

    }
}
