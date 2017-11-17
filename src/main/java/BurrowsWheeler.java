import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] input = readInput();

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

        writeOutput(result);
    }

    private static void writeOutput(char[] result) {
        for (char c : result)
            BinaryStdOut.write(c, 8);

        BinaryStdOut.flush();
        BinaryStdOut.close();
        BinaryStdIn.close();
    }

    private static char[] readInput() {
        StringBuilder b = new StringBuilder();

        while (!BinaryStdIn.isEmpty())
            b.append(BinaryStdIn.readChar(8));

        return b.toString().toCharArray();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
//        char[] input = readInput();
        char[] input = "ABRACADABRA!".toCharArray();
        CircularSuffixArray csa = new CircularSuffixArray(String.valueOf(input));

        int[] next = new int[csa.length()];

        int[] inverse = new int[csa.length()];
        for (int i = 0; i < csa.length(); i++)
            inverse[csa.index(i)] = i;

        for (int i = 0; i < csa.length(); i++)
            next[i] = inverse[(csa.index(i) + 1) % next.length];

        System.out.println(Arrays.toString(next));
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