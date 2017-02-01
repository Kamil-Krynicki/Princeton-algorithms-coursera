package org.krynicki.princeton;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by K on 2017-01-31.
 */
public class SAP {
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph graph) {
        checkNotNull(graph);
        this.graph = new Digraph(graph);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Arrays.asList(v), Arrays.asList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Arrays.asList(v), Arrays.asList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkNotNull(v);
        checkBounds(v);
        checkNotNull(w);
        checkBounds(w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        int sapLen = graph.V() + 1;

        for (int i = 0; i < graph.V(); i++) {
            int newSapLen = bfsV.distTo(i) + bfsW.distTo(i);

            if (newSapLen >= 0 && newSapLen < sapLen) {
                sapLen = newSapLen;
            }
        }

        return sapLen < graph.V() ? sapLen : -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkNotNull(v);
        checkBounds(v);
        checkNotNull(w);
        checkBounds(w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        int result = -1;
        int sapLen = graph.V() + 1;

        for (int i = 0; i < graph.V(); i++) {
            int newSapLen = bfsV.distTo(i) + bfsW.distTo(i);

            if (newSapLen >= 0 && newSapLen < sapLen) {
                sapLen = newSapLen;
                result = i;
            }
        }

        return result;
    }

    private void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException();
    }

    private void checkBounds(Iterable<Integer> v) {
        for (int n : v) if (n < 0 || n >= graph.V()) throw new IndexOutOfBoundsException();
    }
}
