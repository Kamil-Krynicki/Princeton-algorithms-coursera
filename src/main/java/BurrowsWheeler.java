import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] input = BinaryStdIn.readString().toCharArray();

        CircularSuffixArray csa = new CircularSuffixArray(String.valueOf(input));

        char[] result = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            if (csa.index(i) == 0) {
                result[i] = input[input.length - 1];
                BinaryStdOut.write(i);
            } else {
                result[i] = input[csa.index(i) - 1];
            }
        }

        BinaryStdOut.write(new String(result));
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        char[] t = BinaryStdIn.readString().toCharArray();
        char[] f = linearSort(t);

        int[] next = generateNext(t, f);


        int current = first;
        for (int i = 0; i < next.length; i++) {
            BinaryStdOut.write(f[current]);
            current = next[current];
        }

        BinaryStdOut.close();
    }

    private static int[] generateNext(char[] t, char[] f) {
        Map<Character, Deque<Integer>> positions = invertIndex(t);
        int[] next = new int[t.length];

        for (int i = 0; i < next.length; i++)
            next[i] = positions.get(f[i]).pop();

        return next;
    }

    private static Map<Character, Deque<Integer>> invertIndex(char[] t) {
        Map<Character, Deque<Integer>> result = new HashMap<>();

        for (int i = 0; i < t.length; i++) {
            if (!result.containsKey(t[i]))
                result.put(t[i], new LinkedList<>());

            result.get(t[i]).addLast(i);
        }

        return result;
    }

    private static char[] linearSort(char[] input) {
        int[] counts = counts(input);

        char[] result = new char[input.length];
        int letter = 0;
        for (int i = 0; i < counts.length; i++)
            while (counts[i]-- > 0)
                result[letter++] = (char) i;

        return result;
    }

    private static int[] counts(char[] input) {
        int[] result = new int[2 << 8];
        for (char c : input)
            result[c]++;

        return result;
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0].charAt(0)) {
                case '-':
                    encode();
                    break;
                case '+':
                    decode();
                    break;
            }
        }
    }
}