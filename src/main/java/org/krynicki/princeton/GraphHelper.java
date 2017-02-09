package org.krynicki.princeton;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;

import java.util.LinkedList;

/**
 * Created by K on 2017-01-24.
 */
public class GraphHelper {
    static boolean[] visited;

    public static void main(String... args) {
        Digraph g = new Digraph(8);
        visited = new boolean[8];

        char[][] edges = {
                "E F B".toCharArray(),
                "F G".toCharArray(),
                "H G B D".toCharArray(),
                "H".toCharArray(),
                "".toCharArray(),
                "E".toCharArray(),
                "H F".toCharArray(),
                "".toCharArray()
        };
        char i = 'A';
        for (char[] edgeList : edges) {
            for (int j=edgeList.length-1;j>=0;j--) {
                if(edgeList[j]!=' ') {
                    g.addEdge(i - 'A', edgeList[j] - 'A');
                }
            }
            i++;
        }

        System.out.print(g);


        Topological t = new Topological(g);

        for(int a : t.order()) {
            System.out.print((char) (a + 'A')+" ");
        }
    }

    static void dfs(Digraph g, int start) {
        if (!visited[start]) {
            visited[start] = true;
            System.out.print((char) (start + 'A')+" ");
            for (int n : g.adj(start)) {
                dfs(g, n);
            }
        }
    }

    static void bfs(Digraph g, int start) {
        java.util.Deque<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print((char) (current + 'A')+" ");
            for (int n : g.adj(current)) {
                if(!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
}
