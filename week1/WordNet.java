// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import java.util.HashMap; 
// import java.util.Map; 
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;


public class WordNet {
    private final ST<Integer, String> synsetsMap;
    private final ST<String, Integer> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        synsetsMap = new ST<Integer, String>();
        nouns = new ST<String, Integer>();
        int size  = 0;


        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] tokens = in.readLine().split(",");
            int id = Integer.parseInt(tokens[0]);
            String synset = tokens[1];
            synsetsMap.put(id, synset);

            // String[] lineNouns = synset.split(" ");
            for (String noun : synset.split(" ")) {
                // if (!nouns.containsKey(noun)) {
                nouns.put(noun, id);
                // }
            }
            size++;
        }

        Digraph graph = new Digraph(size);
        In inHypernyms = new In(hypernyms);
        while (inHypernyms.hasNextLine()) {
            String[] tokens = inHypernyms.readLine().split(",");
            int v = Integer.parseInt(tokens[0]);

            if (v < 0 || v >= size) {
                throw new NullPointerException();
            }
    
            for (int i = 1; i < tokens.length; i++) {
                int w = Integer.parseInt(tokens[i]);

                if (w < 0 || w >= size) {
                    throw new NullPointerException();
                }

                graph.addEdge(v, w);
            }
        }

        DirectedCycle cycleFinder = new DirectedCycle(graph);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException();
        }

       sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nouns();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int v = nouns.get(nounA);
        int w = nouns.get(nounB);
        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int v = nouns.get(nounA);
        int w = nouns.get(nounB);
        return synsetsMap.get(sap.ancestor(v, w));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        StdOut.println("Start");
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(w.distance("Junior", "Adam"));
        StdOut.println(w.distance("Adam", "Junior"));
    }
}