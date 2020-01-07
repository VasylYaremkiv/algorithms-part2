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
    private TrieSET dictionary;
    private Set<String> resultWords;

    // private final Set<String> dictionary;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] data) {
        // this.dictionary = new HashSet<String>();
        this.dictionary = new TrieSET();


        for (int i = 0; i < data.length; i++) {
            if (data[i].length() > 2) {
                this.dictionary.add(data[i]);
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

        // for (String word : this.dictionary) {
        //     if (this.trie.contains(word)) {
        //         result.add(word);                
        //     }
        // }

        return this.resultWords;
    }

    private void addWordsToTrie(BoggleBoard board, boolean[][] v, int i, int j, String pat) {
        String pattern = pat + board.getLetter(i, j);
        if (board.getLetter(i, j) == 'Q') {
            pattern += "U";
        }

        boolean[][] visited = v.clone();
        for (int vi = 0; vi < board.rows(); vi++) {
            visited[vi] = v[vi].clone();
        }

        visited[i][j] = true;

        if (pattern.length() > 2) {
            if (this.dictionary.contains(pattern) && !this.resultWords.contains(pattern)) {
                this.resultWords.add(pattern);
            }    
        }

        // this.trie.add(pattern);
        // if (pattern == "DIE") {
        //     StdOut.println(" pattern : " + pattern);
        // }

        // StdOut.println(" pattern : " + pattern);

        // StdOut.println(" pattern : " + pattern);

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
