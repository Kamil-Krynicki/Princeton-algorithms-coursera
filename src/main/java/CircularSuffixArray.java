import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CircularSuffixArray {

    private final List<LightWeightString> suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if(s == null)
            throw new IllegalArgumentException();

        suffixes = new ArrayList<>(s.length());

        for (int i = 0; i < s.length(); i++)
            suffixes.add(new LightWeightString(s.toCharArray(), i));

        Collections.sort(suffixes);
    }

    // length of s
    public int length() {
        return suffixes.size();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if(i < 0 || i >= length())
            throw new IllegalArgumentException();

        return suffixes.size() - suffixes.get(i).size();
    }

    private static class LightWeightString implements Comparable<LightWeightString> {
        private char[] data;
        private int offset;

        LightWeightString(char[] data, int offset) {
            this.data = data;
            this.offset = offset;
        }

        int size() {
            return data.length - offset;
        }

        char charAt(int i) {
            return data[(i + offset) % data.length];
        }

        @Override
        public String toString() {
            char[] result = new char[data.length];
            System.arraycopy(data, offset, result, 0, data.length - offset);
            System.arraycopy(data, 0, result, data.length - offset, offset);

            return new String(result);
        }

        @Override
        public int compareTo(LightWeightString that) {
            for (int i = 0; i < data.length; i++) {
                if (that.charAt(i) < this.charAt(i))
                    return 1;
                if (that.charAt(i) > this.charAt(i))
                    return -1;
            }

            return 0;
        }
    }
}





















