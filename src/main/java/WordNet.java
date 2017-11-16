

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by K on 2017-01-30.
 */
public class WordNet {
    private final Digraph graph;
    private final Map<Integer, String> synsets;
    private final Map<String, Set<Integer>> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsetPath, String hypernymPath) {
        checkNotNull(synsetPath);
        checkNotNull(hypernymPath);

        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();

        loadSynsets(synsetPath);
        this.graph = new Digraph(synsets.size());

        loadHypernyms(hypernymPath);
        this.sap = new SAP(this.graph);

        if (new DirectedCycle(graph).hasCycle()) throw new IllegalArgumentException();
        if (!(new RootedDetector(graph).isRooted())) throw new IllegalArgumentException();

    }

    // do unit testing of this class
    public static void main(String[] args) {
        new WordNet(args[0], args[1]);
    }

    private void loadSynsets(String synsetPath) {
        for (String line : new In(synsetPath).readAllLines()) {
            String[] split = line.split(",");
            int id = Integer.parseInt(split[0]);

            synsets.put(id, split[1]);
            for (String noun : split[1].split(" ")) {
                if (!nouns.containsKey(noun))
                    nouns.put(noun, new HashSet<>());

                nouns.get(noun).add(id);
            }
        }
    }

    private void loadHypernyms(String hypernymPath) {
        for (String line : new In(hypernymPath).readAllLines()) {
            String[] split = line.split(",");
            int id = Integer.parseInt(split[0]);
            for (int to = 1; to < split.length; to++) {
                graph.addEdge(id, Integer.parseInt(split[to]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkNotNull(word);
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkNotNull(nounA);
        checkNotNull(nounB);

        checkIsKnown(nounA);
        checkIsKnown(nounB);

        return sap.length(getId(nounA), getId(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkNotNull(nounA);
        checkNotNull(nounB);

        checkIsKnown(nounA);
        checkIsKnown(nounB);

        return getSynset(sap.ancestor(getId(nounA), getId(nounB)));
    }

    private String getSynset(int id) {
        return synsets.get(id);
    }

    private Set<Integer> getId(String noun) {
        return nouns.get(noun);
    }

    private void checkIsKnown(String noun) {
        if (!nouns.containsKey(noun)) throw new IllegalArgumentException();
    }

    private void checkNotNull(String string) {
        if (string == null) throw new NullPointerException();
    }

    private class RootedDetector {
        private final Digraph graph;

        public RootedDetector(Digraph graph) {
            this.graph = graph;
        }

        public boolean isRooted() {
            int found = 0;
            for (int i = 0; i < graph.V(); i++) {
                if (graph.outdegree(i) == 0) {
                    if (++found > 1) return false;
                }
            }
            return found == 1;
        }
    }
}
