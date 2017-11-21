

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by kamil.krynicki on 16/11/2017.
 */
public class MoveToFront {
    private static final int R = 2 << 8;

    public static class DynamicAlphabetEncoding {
        char[] alphabet;

        DynamicAlphabetEncoding() {
            alphabet = new char[R];

            for (int i = 0; i < R; i++)
                alphabet[i] = (char) i;
        }

        int encode(char c) {
            int result = getCodeForChar(c);

            moveToFront(result);

            return result;
        }

        private void moveToFront(int code) {
            char result = alphabet[code];

            System.arraycopy(alphabet, 0, alphabet, 1, code);

            alphabet[0] = result;
        }

        private int getCodeForChar(char code) {
            int i = 0;
            while(alphabet[i]!=code) {
                i++;
            }
            return i;
        }
    }

    public static class DynamicAlphabetDecoding {
        private char[] alphabet;

        DynamicAlphabetDecoding() {
            alphabet = new char[R];

            for (int i = 0; i < R; i++)
                alphabet[i] = (char) i;
        }

        char decode(int code) {
            char result = getCharForCode(code);

            moveToFront(code);

            return result;
        }

        private void moveToFront(int code) {
            char result = alphabet[code];

            System.arraycopy(alphabet, 0, alphabet, 1, code);

            alphabet[0] = result;
        }

        private char getCharForCode(int code) {
            return alphabet[code];
        }
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        DynamicAlphabetEncoding alphabet = new DynamicAlphabetEncoding();

        while (!BinaryStdIn.isEmpty()) {
            int encode = alphabet.encode(BinaryStdIn.readChar(8));
            BinaryStdOut.write(encode, 8);
        }

        BinaryStdOut.close();
        BinaryStdIn.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        DynamicAlphabetDecoding alphabet = new DynamicAlphabetDecoding();

        while (!BinaryStdIn.isEmpty()) {
            char decode = alphabet.decode(BinaryStdIn.readChar(8));
            BinaryStdOut.write(decode, 8);
        }

        BinaryStdOut.close();
        BinaryStdIn.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
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
