import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {

    private final int[] suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException();

        Integer[] tmp = new Integer[s.length()];
        for (int i = 0; i < s.length(); i++)
            tmp[i] = i;

        Arrays.sort(tmp,
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer offset1, Integer offset2) {
                        for (int i = 0; i < s.length(); i++) {
                            if (charAt(i, offset2) < charAt(i, offset1))
                                return 1;
                            if (charAt(i, offset2) > charAt(i, offset1))
                                return -1;
                        }

                        return 0;
                    }

                    char charAt(int i, int offset) {
                        return s.charAt((i + offset) % s.length());
                    }
                });

        this.suffixes = toPrimitive(tmp);
    }

    private int[] toPrimitive(Integer[] tmp) {
        int[] result = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++)
            result[i] = tmp[i];

        return result;
    }

    // length of s
    public int length() {
        return suffixes.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IllegalArgumentException();

        return suffixes[i];
    }
}





















