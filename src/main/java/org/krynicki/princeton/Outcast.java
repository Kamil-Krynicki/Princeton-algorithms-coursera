package org.krynicki.princeton;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by K on 2017-01-31.
 */
public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // see test client below
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
        long t2 = System.currentTimeMillis();
        System.out.print(t2 - t1);
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];

        for (int i = 0; i < nouns.length; i++) {
            for (int j = i + 1; j < nouns.length; j++) {
                int distance = wordNet.distance(nouns[i], nouns[j]);
                distances[i] += distance;
                distances[j] += distance;
            }
        }

        return nouns[maxIndex(distances)];
    }

    private int maxIndex(int[] array) {
        int maxVal = array[0];
        int maxIndex = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxVal) {
                maxVal = array[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}
