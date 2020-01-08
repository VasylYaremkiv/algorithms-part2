import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private Set<String> resultWords;
    private Trie trie;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] data) {
        this.trie = new Trie();

        for (int i = 0; i < data.length; i++) {
            if (data[i].length() > 2) {
                this.trie.put(data[i]);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.resultWords = new HashSet<String>();
        boolean[][] visited;

        for (int i = 0; i < board.cols(); i++) {
            for (int j = 0; j < board.rows(); j++) {
                visited = new boolean[board.cols()][board.rows()];
                addWordsToTrie(board, visited, i, j, "");
            }
        }

        return this.resultWords;
    }

    private void addWordsToTrie(BoggleBoard board, boolean[][] visited, int i, int j, String pat) {
        String pattern = pat + board.getLetter(j, i);
        if (board.getLetter(j, i) == 'Q') {
            pattern += "U";
        }

        if (!this.trie.containsPrefix(pattern)) {
            return;
        }

        visited[i][j] = true;

        if (pattern.length() > 2) {
            if (!this.resultWords.contains(pattern) && this.trie.contains(pattern)) {
                this.resultWords.add(pattern);
            }    
        }

        if (i < board.cols() - 1 && !visited[i+1][j]) {
            addWordsToTrie(board, visited, i + 1, j, pattern);
        }

        if (i > 0 && !visited[i - 1][j]) {
            addWordsToTrie(board, visited, i - 1, j, pattern);
        }

        if (j < board.rows() - 1 && !visited[i][j + 1]) {
            addWordsToTrie(board, visited, i, j + 1, pattern);
        }

        if (j > 0 && !visited[i][j - 1]) {
            addWordsToTrie(board, visited, i, j - 1, pattern);
        }

        if (i > 0 && j > 0 && !visited[i - 1][j - 1]) {
            addWordsToTrie(board, visited, i - 1, j - 1, pattern);
        }

        if (i > 0 && j < board.rows() - 1 && !visited[i - 1][j + 1]) {
            addWordsToTrie(board, visited, i - 1, j + 1, pattern);
        }

        if (i < board.cols() - 1 && j > 0 && !visited[i + 1][j - 1]) {
            addWordsToTrie(board, visited, i + 1, j - 1, pattern);
        }

        if (i < board.cols() - 1 && j < board.rows() - 1 && !visited[i + 1][j + 1]) {
            addWordsToTrie(board, visited, i + 1, j + 1, pattern);
        }

        visited[i][j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!trie.contains(word)) {
            return 0;
        }

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
            TrieNode[] next = new TrieNode[26];
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
    }
}
