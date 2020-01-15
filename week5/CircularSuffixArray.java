import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class CircularSuffixArray {
    private final String s;
    private final int length;
    private int[] indexes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        this.s = s;
        this.length = s.length();

        CircularSuffix[] array = new CircularSuffix[this.length ];
        for (int i = 0; i < this.length; i++) {
            array[i] = new CircularSuffix(s, i);
        }
        Arrays.sort(array);
        this.indexes = new int[this.length];
        for (int i = 0; i < this.length; i++) {
            this.indexes[i] = array[i].offset;
        }
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

        return this.indexes[i];
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private String s;
        private int offset;

        public CircularSuffix(String s, int offset) {
            this.s = s;
            this.offset = offset;
        }

        private char charAt(int pos) {
            return s.charAt((offset + pos) % s.length());
        }

        public int compareTo(CircularSuffix other) {
            if (this == other) {
                return 0;
            }

            for (int i = 0; i < length; i++) {
                if (this.charAt(i) > other.charAt(i)) {
                    return 1;
                }
                if (this.charAt(i) < other.charAt(i)) {
                    return -1;
                }
            }

            return 0;
        }
        public String toString() {
            return s.substring(offset, s.length()) + s.substring(0, offset);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");

        StdOut.println("csa.length = " + csa.length());
        StdOut.println("csa.index(5) = " + csa.index(5));
        StdOut.println("csa.index(11) = " + csa.index(11));
    }
}