import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Random;
import java.lang.StringBuilder;

public class BoggleBoard {
    private static final char Q = "Q";
    private static final String QU = "Qu";

    private final char[][] board;
    private final int m;
    private final int n;

    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() {
        // this.m = 4;
        // this.n = 4;

        // this.board = new char[this.m][this.n];

        BoggleBoard(4, 4);
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n) {
        this.m = m;
        this.n = n;

        this.board = new char[this.m][this.n];

        Random r = new Random();

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                this.board[i][j] = (char)(r.nextInt(26) + 'A');
            }
        }
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) {
        In in = new In(filename);
        this.m = in.readInt();
        this.n = in.readInt();
        in.readLine();

        String[] splited;

        for (int i = 0; i < this.m; i++) {
            splited = in.readLine().trim().split("\\s+");

            for (int j = 0; j < this.n; j++) {
                this.board[i][j] = (char)splited[j][0];
            }
        }
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a) {
        this.m = a.length;
        this.n = a[0].length;

        this.board = new char[this.m][this.n];

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                this.board[i][j] = a[i][j];
            }
        }
    }

    // Returns the number of rows.
    public int rows() {
        return this.m;
    }

    // Returns the number of columns.
    public int cols() {
        return this.n;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        // if (this.board[i][j] == Q) {
        //     return QU;
        // }

        return this.board[i][j];
    }

    // Returns a string representation of the board.
    public String toString() {
        StringBuilder rev = new StringBuilder();

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.board[i][j] == Q) {
                    rev.append(QU);
                } else {
                    rev.append(this.board[i][j]);
                }
            }
        }

        return rev.toString();
    }
}
