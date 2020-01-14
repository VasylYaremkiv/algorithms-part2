import edu.princeton.cs.algs4.StdOut;



public class CircularSuffixArray {
    private final String s;
    private final int length;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        this.s = s;
        this.length = s.length();
    }

    // length of s
    public int length() {
        return this.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= this.length) {
            throw new IllegalArgumentException();
        }

        return 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");

        StdOut.println("csa.length = " + csa.length());
        StdOut.println("csa.index(5) = " + csa.index(5));
        StdOut.println("csa.index(11) = " + csa.index(11));
    }
}