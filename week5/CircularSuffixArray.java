import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final int length;
    private final Integer[] indexes;
    private final String s;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        this.length = s.length();
        this.s = s;

        this.indexes = new Integer[this.length];
        for (int i = 0; i < this.length; i++) {
            this.indexes[i] = i;
        }

        Arrays.sort(this.indexes, new CompararCircularSuffix());
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

    private class CompararCircularSuffix implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            if (o1.equals(o2)) {
                return 0;
            }

            char currentItem, otherItem;
            for (int i = 0; i < length; i++) {
                currentItem = s.charAt((o1 + i) % length);
                otherItem = s.charAt((o2 + i) % length);

                if (currentItem > otherItem) {
                    return 1;
                }
                if (currentItem < otherItem) {
                    return -1;
                }
            }

            return 0;
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