import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;


public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new NullPointerException();
        }

        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = 0;
        int iMax;
        String result = "";

        for (int i = 0; i < nouns.length; i++) {
            iMax = 0;

            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    iMax += this.wordnet.distance(nouns[i], nouns[j]);
                }
            }

            if (iMax > max) {
                result = nouns[i];
                max = iMax;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
