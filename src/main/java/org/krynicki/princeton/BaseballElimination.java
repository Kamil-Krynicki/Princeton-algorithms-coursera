package org.krynicki.princeton;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by K on 2/21/2017.
 */
public class BaseballElimination {
    private final int N;

    private final Map<String, Integer> names;

    private final int[] w;
    private final int[] l;
    private final int[] r;

    private final int[][] g;


    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(new File(filename));

        this.N = Integer.valueOf(in.readLine());

        this.names = new HashMap<>();
        this.w = new int[N];
        this.l = new int[N];
        this.r = new int[N];

        this.g = new int[N][N];

        String line;
        int team = 0;
        while ((line = in.readLine()) != null) {
            String[] split = line.split("[\\s]+");

            names.put(split[0], team);

            w[team] = Integer.valueOf(split[1]);
            l[team] = Integer.valueOf(split[2]);
            r[team] = Integer.valueOf(split[3]);
            g[team] = against(split);

            team++;
        }

        System.out.println();

        for(String t: names.keySet()) {
            System.out.println(t + ", " + isEliminated(t));
        }
    }

    private int[] against(String[] split) {
        int[] result = new int[N];

        for (int i = 0; i < N; i++)
            result[i] = Integer.valueOf(split[i + 4]);

        return result;
    }

    private boolean isEliminatedTrivial(int x) {
        return w[x] + r[x] < w[0];
    }

    private boolean isEliminated(int x) {
        FlowNetwork flowNetwork = flowNetwork(x);


        StdOut.println(flowNetwork);

        // compute maximum flow and minimum cut
        FordFulkerson ff = new FordFulkerson(flowNetwork, 0, flowNetwork.V()-1);
        //StdOut.println("Max flow from " + 0 + " to " + (N-1));
        //for (int v = 0; v < flowNetwork.V(); v++) {
        //    for (FlowEdge e : flowNetwork.adj(v)) {
        //        if ((v == e.from()) && e.flow() > 0)
        //            StdOut.println("   " + e);
        //    }
        //}

        // print min-cut

        Set<Integer> dupa = new HashSet<>();
        for (int v = 1; v < flowNetwork.V() - N - 2; v++) {
            if(v == x) continue;
            if (ff.inCut(v)) {
                dupa.add(v-1);
            }
        }

        System.out.println(dupa);

        return !dupa.isEmpty();
    }

    private FlowNetwork flowNetwork(int x) {
        int nodeCount = (N + 1) * N / 2;

        FlowNetwork matches = new FlowNetwork(nodeCount + 2);

        int sourceNode = 0;
        int targetNode = matches.V() - 1;

        int matchNode = 1;
        int teamNode = matches.V() - N - 1;

        for (int team1 = 0; team1 < N; team1++)
            for (int team2 = team1 + 1; team2 < N; team2++) {
                int capacity = (team1 == x || team2 == x)?0:g[team1][team2];
                matches.addEdge(new FlowEdge(sourceNode, matchNode, capacity));
                matches.addEdge(new FlowEdge(matchNode, teamNode + team1, Integer.MAX_VALUE));
                matches.addEdge(new FlowEdge(matchNode, teamNode + team2, Integer.MAX_VALUE));
                matchNode++;
            }

        for(int i = 0; i < N; i++) {
            int capacity = w[x] + r[x] - w[i];
            matches.addEdge(new FlowEdge(teamNode + i, targetNode, capacity>0?capacity:0));
        }

        return matches;
    }

    private int id(String team) {
        if (names.containsKey(team)) {
            return names.get(team);
        } else {
            throw new IllegalArgumentException();
        }
    }

    // number of teams
    public int numberOfTeams() {
        return N;
    }

    // all teams
    public Iterable<String> teams() {
        return names.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return w[id(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return l[id(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return r[id(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return g[id(team1)][id(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return isEliminated(id(team));
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String... args) {
        new BaseballElimination(args[0]);
    }
}
