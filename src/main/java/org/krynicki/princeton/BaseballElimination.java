import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by K on 2/21/2017.
 */
public class BaseballElimination {
    private final int N;

    private final Map<String, Integer> teams;
    private final Map<String, Set<String>> eliminators;

    private final int[] w;
    private final int[] l;
    private final int[] r;

    private final int[][] g;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        this.N = in.readInt();

        this.teams = new HashMap<>();
        this.eliminators = new HashMap<>();

        this.w = new int[N];
        this.l = new int[N];
        this.r = new int[N];
        this.g = new int[N][N];

        int team = 0;
        while (!in.isEmpty()) {
            teams.put(in.readString(), team);

            w[team] = in.readInt();
            l[team] = in.readInt();
            r[team] = in.readInt();
            g[team] = new int[N];

            for (int i = 0; i < N; i++)
                g[team][i] = in.readInt();

            team++;
        }
    }

    private int id(String team) {
        if (!teams.containsKey(team))
            throw new IllegalArgumentException();

        return teams.get(team);
    }

    // number of teams
    public int numberOfTeams() {
        return N;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
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
        return !eliminators(team).isEmpty();
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return eliminators(team).isEmpty() ? null : eliminators(team);
    }

    private Set<String> eliminators(String team) {
        if (eliminators.containsKey(team)) {
            return eliminators.get(team);
        }

        List<Function<Integer, Set<String>>> u = Arrays.asList(new EliminatorsTrivial(), new EliminatorsFlow());

        int id = id(team);
        for(Function<Integer, Set<String>> f : u) {
            Set<String> e = f.apply(id);
            if(!e.isEmpty()) {
                eliminators.put(team, e);
                return e;
            }
        }

        eliminators.put(team, Collections.emptySet());
        return Collections.emptySet();
    }

    private class EliminatorsTrivial implements Function<Integer, Set<String>> {
        @Override
        public Set<String> apply(Integer x) {
            return teams.keySet().stream().filter(team -> w[x] + r[x] < w[id(team)]).collect(Collectors.toSet());
        }
    }

    private class EliminatorsFlow implements Function<Integer, Set<String>> {
        @Override
        public Set<String> apply(Integer x) {
            FlowNetwork network = flowNetwork(x);
            FordFulkerson flow = new FordFulkerson(network, network.V() - 2, network.V() - 1);

            return teams.keySet().stream().filter(team -> flow.inCut(id(team))).collect(Collectors.toSet());
        }
    }

    private FlowNetwork flowNetwork(int x) {
        FlowNetwork network = new FlowNetwork((N + 1) * N / 2 + 2);

        int sourceNode = network.V() - 2;
        int targetNode = network.V() - 1;

        int matchRoot = N - 1;
        int teamRoot = 0;

        for (int i = 0; i < N; i++) {
            if (x == i) continue;

            network.addEdge(new FlowEdge(teamRoot + i, targetNode, w[x] + r[x] - w[i]));

            for (int j = i + 1; j < N; j++) {
                matchRoot++;

                if (x == j) continue;

                network.addEdge(new FlowEdge(sourceNode, matchRoot, g[i][j]));
                network.addEdge(new FlowEdge(matchRoot, teamRoot + i, Integer.MAX_VALUE));
                network.addEdge(new FlowEdge(matchRoot, teamRoot + j, Integer.MAX_VALUE));
            }
        }

        return network;
    }

    public static void main(String... args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
