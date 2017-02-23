package org.krynicki.princeton;

import java.util.Arrays;

/**
 * Created by kamil.krynicki on 20/02/2017.
 */
public class RadixSortHelper {
    private static final int BITS_PER_BYTE =   8;
    private static final int BITS_PER_INT  =  32;   // each Java int is 32 bits
    private static final int R             = 256;   // extended ASCII alphabet size
    private static final int CUTOFF        =  10;   // cutoff to insertion sort

    public static void main(String... args) {
        //2134 3244 3424 1421 4344 4331 2331 4124 4321 2433 (LSD, 2nd)
        //{"2134", "3244", "3424", "1421", "4344", "4331", "2331", "4124", "4321", "2433"};
        //2114 1341 3244 3341 4324 1242 1431 1314 1132 2432 4211 3313 2124 2132 2142 (MSC, 3rd)
        //String[] a = {"1224", "4424", "3313", "1331", "1122", "4343", "1123", "4423", "3243", "4234", "2311", "1214", "1114", "3331", "1312"};
        //4163 3245 4334 3662 6124 1432 6264 4341 6532 4464 (3wRadix, 1st)


        String[] a = {"2114","1341","3244","3341","4324","1242","1431","1314","1132","2432","4211","3313","2124","2132","2142"};
        System.out.println(Arrays.toString(a));
        sort(a, 1);
        System.out.println(Arrays.toString(a));
        String[] b = {"341", "242", "431", "314", "132"};
        System.out.println(Arrays.toString(b));
        sort(b, 1);
        System.out.println(Arrays.toString(b));
        //LSD.sort(a, 4);
        //Quick3string.sort(a);
        //System.out.println(Arrays.toString(a));
    }

    public static void sort(String[] a, int depth) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n-1, 0, aux, depth);
    }

    // return dth character of s, -1 if d = length of string
    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private static void sort(String[] a, int lo, int hi, int d, String[] aux, int depth) {

        // cutoff to insertion sort for small subarrays
        if (depth <= 0) {
            //insertion(a, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c+2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c+1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];


        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1, aux, depth - 1);
    }


    // insertion sort a[lo..hi], starting at dth character
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }


    // insertion sort a[lo..hi], starting at dth character
    private static void insertion(int[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && a[j] < a[j-1]; j--)
                exch(a, j, j-1);
    }

    // exchange a[i] and a[j]
    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


}
