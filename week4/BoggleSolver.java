import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

// import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver {
    // private static final char Q = 'Q';
    // private static final String QU = "Qu";
    private Set<String> dictionary;
    // private TrieSET dictionary;
    private Set<String> resultWords;
    // private TrieSET trie;
    private Trie trie;


    // private final Set<String> dictionary;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] data) {
        this.dictionary = new HashSet<String>();
        // this.dictionary = new TrieSET();
        this.trie = new Trie();


        for (int i = 0; i < data.length; i++) {
            if (data[i].length() > 2) {
                this.dictionary.add(data[i]);
                this.trie.put(data[i]);
            }
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.resultWords = new HashSet<String>();

        // this.trie = new TrieSET();
        boolean[][] visited;

        for (int i = 0; i < board.cols(); i++) {
            for (int j = 0; j < board.rows(); j++) {
                visited = new boolean[board.cols()][board.rows()];
                // StdOut.println(" i: " + i + " , j: " + j);
                addWordsToTrie(board, visited, i, j, "");
                // break;
            }
            // break;
        }
        // visited = new boolean[board.cols()][board.rows()];
        // addWordsToTrie(board, visited, 1, 2, "");
        // String prefix;
        // for (String word : this.dictionary) {
        //     prefix = this.trie.longestPrefixOf(word);
        //     if (prefix != null && prefix.length() == word.length()) {
        //         this.resultWords.add(word);                
        //     }
        // }

        return this.resultWords;
    }

    private void addWordsToTrie(BoggleBoard board, boolean[][] visited, int i, int j, String pat) {
        String pattern = pat + board.getLetter(i, j);
        if (board.getLetter(i, j) == 'Q') {
            pattern += "U";
        }

        // String dictionaryPattern = this.dictionary.longestPrefixOf(pattern);
        // StdOut.println(" pattern : " + pattern + ", dictionaryPattern : " + dictionaryPattern);
        // StdOut.println(" this.dictionary : " + this.dictionary.isEmpty());
        if (!this.trie.containsPrefix(pattern)) {
            return ;
        }

        // boolean[][] visited = v.clone();
        // for (int vi = 0; vi < board.rows(); vi++) {
        //     visited[vi] = v[vi].clone();
        // }

        visited[i][j] = true;

        if (pattern.length() > 2) {
            if (!this.resultWords.contains(pattern) && this.dictionary.contains(pattern)) {
                this.resultWords.add(pattern);
            }    
        }

        // boolean needToAdd = true;

        // this.trie.add(pattern);
        // if (pattern == "DIE") {
        //     StdOut.println(" pattern : " + pattern);
        // }

        // StdOut.println(" pattern : " + pattern);

        // StdOut.println(" pattern : " + pattern);

        if (i < board.cols() - 1 && !visited[i+1][j]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i + 1, j, pattern);
        }

        if (i > 0 && !visited[i - 1][j]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i - 1, j, pattern);
        }

        if (j < board.rows() - 1 && !visited[i][j + 1]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i, j + 1, pattern);
        }

        if (j > 0 && !visited[i][j - 1]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i, j - 1, pattern);
        }

        if (i > 0 && j > 0 && !visited[i - 1][j - 1]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i - 1, j - 1, pattern);
        }

        if (i > 0 && j < board.rows() - 1 && !visited[i - 1][j + 1]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i - 1, j + 1, pattern);
        }

        if (i < board.cols() - 1 && j > 0 && !visited[i + 1][j - 1]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i + 1, j - 1, pattern);
        }

        if (i < board.cols() - 1 && j < board.rows() - 1 && !visited[i + 1][j + 1]) {
            // needToAdd = false;
            addWordsToTrie(board, visited, i + 1, j + 1, pattern);
        }

        // if (needToAdd) {
        //     StdOut.println(" pattern : " + pattern);
        //     this.trie.add(pattern);
        // }

        visited[i][j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionary.contains(word)) {
            return 0;
        }

        // StdOut.println(" word : " + word + "word.length() = " + word.length());
        return convertLengthToScore(word.length());
    }

    private int convertLengthToScore(int length) {
        if (length < 3) {
            return 0;
        }

        switch (length) {
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
        }
    }
    
    private static class Trie {
        private TrieNode root = new TrieNode();
       
        private static class TrieNode {
            int R = 26;
            TrieNode[] next = new TrieNode[R];
            boolean word = false;
        }

        public void put(String key) { 
            root = put(root, key, 0);
        }

        private TrieNode put(TrieNode x, String key, int d) {
            if (x == null) x = new TrieNode();
            if (d == key.length()) { 
                x.word = true;
                return x;
            }
            int c = key.charAt(d) - 'A';
            x.next[c] = put(x.next[c], key, d + 1);
           return x;
        }


        public boolean contains(String key) { 
            return containsWord(key);
        }

        public boolean containsPrefix(String key) { 
            // return containsWord(key);
            TrieNode x = get(root, key, 0);
            return x != null;
        }

        public boolean containsWord(String key) {
            TrieNode x = get(root, key, 0);
            if (x == null) return false;
            return x.word;
        }

        private TrieNode get(TrieNode x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            int c = key.charAt(d) - 'A';
            return get(x.next[c], key, d+1);
        }
    }
    


    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);


        // TrieSET a = new TrieSET();
        // a.add("ABSC");
        // StdOut.println("Score = " + a.longestPrefixOf("ABe"));

    }
}
