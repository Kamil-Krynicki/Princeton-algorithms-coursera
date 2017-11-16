import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularSuffixArray {

    private final List<String> suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if(s == null)
            throw new IllegalArgumentException();

        suffixes = new ArrayList<>(s.length());

        for (int i = 0; i < s.length(); i++)
            suffixes.add(s.substring(i));

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

        return suffixes.size() - suffixes.get(i).length();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");

        System.out.println(circularSuffixArray.index(11));
        System.out.println(circularSuffixArray.index(3));
    }
}
