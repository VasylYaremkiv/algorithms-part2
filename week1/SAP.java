import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class SAP {
    private Digraph graph;
    private int size;
 
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }

        graph = new Digraph(G);
        size = graph.V();

    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > size || w < 0 || w > size) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        return calculateLenght(bfsV, bfsW);
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > size || w < 0 || w > size) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        return calculateAncestor(bfsV, bfsW);
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        return calculateLenght(bfsV, bfsW);
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }

        return calculateAncestor(bfsV, bfsW);
    }
 

    private int calculateLenght(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int length = Integer.MAX_VALUE;
        for (int i = 0, temp = 0; i < size; i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                temp = bfsV.distTo(i) + bfsW.distTo(i);
                if (temp < length) {
                    length = temp;
                }
            }
        }

        if (length == Integer.MAX_VALUE) {
            return -1;
        } else {
            return length;
        }
    }

    private int calculateAncestor(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int length = Integer.MAX_VALUE;
        int ancestor = 0;
        for (int i = 0, temp = 0; i < size; i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                temp = bfsV.distTo(i) + bfsW.distTo(i);
                if (temp < length) {
                    length = temp;
                    ancestor = i;
                }
            }
        }

        if (length == Integer.MAX_VALUE) {
            return -1;
        } else {
            return ancestor;
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
 }