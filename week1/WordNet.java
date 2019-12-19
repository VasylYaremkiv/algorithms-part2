// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.HashMap; 
// import java.util.Map; 
import edu.princeton.cs.algs4.Digraph;


public class WordNet {
    private final HashMap<Integer, String> synsetsMap;
    private final HashMap<String, Integer> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        synsetsMap = new HashMap<Integer, String>();
        nouns = new HashMap<String, Integer>();
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
    
            for (int i = 1; i <= tokens.length; i++) {
                int w = Integer.parseInt(tokens[i]);

                if (w < 0 || w >= size) {
                    throw new NullPointerException();
                }

                graph.addEdge(v, w);
            }
       }

       sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.nouns.containsKey(word);
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
    }
}