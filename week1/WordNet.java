import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.HashMap; 
import java.util.Map; 


public class WordNet {
    private HashMap<int, String> hashMap;
    private HashMap<String, int> nouns;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        hashMap = new HashMap<int, String>();
        nouns = new HashMap<String, int>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] tokens = in.readLine().split(",");
            int id = Integer.parseInt(tokens[0]);
            String synset = tokens[1];
            hashMap.put(id, synset);

            String[] lineNouns = synset.split(" ")
            for (int i = 0; i < lineNouns.length; i++) {
                if (!nouns.containsKey(lineNouns[i])) {
                    lineNouns.put()

                }
            }

        }


    }

    // // returns all WordNet nouns
    // public Iterable<String> nouns() {
    // }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        StdOut.println("Start");
    }
}