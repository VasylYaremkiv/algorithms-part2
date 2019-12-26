import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
// import edu.princeton.cs.algs4.Topological;

import edu.princeton.cs.algs4.AcyclicSP;


import java.awt.Color;


public class SeamCarver {
    private Picture picture;
    private double[][] energies;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = picture;
        this.energies = calculateEnergies();
    }
 
    // current picture
    public Picture picture() {
        return picture;
    }
 
    // width of current picture
    public int width() {
        return picture.width();
    }
 
    // height of current picture
    public int height() {
        return picture.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= this.width()) {
            throw new IllegalArgumentException();
        }
        if (y < 0 || y >= this.height()) {
            throw new IllegalArgumentException();
        }

        return energies[x][y];
    }
 
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] result = new int[this.width()];

        int top = this.width() * this.height() - this.ignoredVerticles() + this.height() - 2;
        int bottom = top + 1;

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(bottom + 1);

        // StdOut.println("this.width() :" + this.width());
        // StdOut.println("this.height() :" + this.height());


        for (int i = 1; i < this.width() - 1; i++) {


            for (int j = 1; j < this.height() - 1; j++) {


                // StdOut.print(String.format("%8.2f  ", this.energies[i][j]) + " (" + convertToVerticle(i, j) + ")" );                

                G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j), energies[i][j]));

                if (j > 1) {
                    G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j - 1), energies[i][j]));
                }

                if (j < this.height() - 2) {
                    G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j + 1), energies[i][j]));
                }
            }
            // StdOut.println();
        }


        for (int j = 1; j < this.height() - 1; j++) {
            G.addEdge(new DirectedEdge(top, convertToVerticle(1, j), 0.0));
            G.addEdge(new DirectedEdge(convertToVerticle(this.width() - 2, j), bottom, 0.0));
        }
        


        // StdOut.println("G.V :" + G.V());
        // StdOut.println("G.E :" + G.E());

        AcyclicSP sp = new AcyclicSP(G, top);
        // StdOut.printf("%d to %d (%.2f)  ", top, bottom, sp.distTo(bottom));
        int i = 0;
        for (DirectedEdge e : sp.pathTo(bottom)) {
            // StdOut.print(e + "   " );

            if (e.from() == top) {
                result[i++] = this.convertToRow(e.to());
                continue;
            }
            // if (e.to() == bottom) {
            //     result[i++] = this.convertToRow(e.from());
            //     continue;
            // }
            result[i++] = this.convertToRow(e.from());



        }
        result[i] = result[i - 1];

        StdOut.println();


        // Topological topological = new Topological(G);
        // int x, y;
        // for (int v : topological.order()) {
        //     // for (DirectedEdge e : G.adj(v)) {
        //     //     relax(e);
        //     // }
        //     x = this.convertToColumn(v);
        //     y = this.convertToRow(v);
        //     StdOut.println("v (" + x + ", " + y + ") : " + v);
        // }


        return result;
    }

    private int convertToVerticle(int x, int y) {
        return (x - 1) * (this.height() - 2) + y - 1;
    }

    // private int convertToColumn(int v) {
    //     return (v + 0) / (this.height() - 2) + 1;
    // }

    private int convertToRow(int v) {
        return (v + 0) % (this.height() - 2) + 1;
    }

    private int ignoredVerticles() {
        return this.width() * 2 + this.height() * 2 - 4;
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] result = new int[this.width()];

        int top = this.width() * this.height() - this.ignoredVerticles() + this.height() - 2;
        int bottom = top + 1;

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(bottom + 1);

        // StdOut.println("this.width() :" + this.width());
        // StdOut.println("this.height() :" + this.height());


        for (int i = 1; i < this.width() - 1; i++) {


            for (int j = 1; j < this.height() - 1; j++) {


                // StdOut.print(String.format("%8.2f  ", this.energies[i][j]) + " (" + convertToVerticle(i, j) + ")" );                

                G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j), energies[i][j]));

                if (j > 1) {
                    G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j - 1), energies[i][j]));
                }

                if (j < this.height() - 2) {
                    G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j + 1), energies[i][j]));
                }
            }
            // StdOut.println();
        }


        for (int j = 1; j < this.height() - 1; j++) {
            G.addEdge(new DirectedEdge(top, convertToVerticle(1, j), 0.0));
            G.addEdge(new DirectedEdge(convertToVerticle(this.width() - 2, j), bottom, 0.0));
        }
        


        // StdOut.println("G.V :" + G.V());
        // StdOut.println("G.E :" + G.E());

        AcyclicSP sp = new AcyclicSP(G, top);
        // StdOut.printf("%d to %d (%.2f)  ", top, bottom, sp.distTo(bottom));
        int i = 0;
        for (DirectedEdge e : sp.pathTo(bottom)) {
            // StdOut.print(e + "   " );

            if (e.from() == top) {
                result[i++] = this.convertToRow(e.to());
                continue;
            }
            // if (e.to() == bottom) {
            //     result[i++] = this.convertToRow(e.from());
            //     continue;
            // }
            result[i++] = this.convertToRow(e.from());



        }
        result[i] = result[i - 1];

        StdOut.println();


        // Topological topological = new Topological(G);
        // int x, y;
        // for (int v : topological.order()) {
        //     // for (DirectedEdge e : G.adj(v)) {
        //     //     relax(e);
        //     // }
        //     x = this.convertToColumn(v);
        //     y = this.convertToRow(v);
        //     StdOut.println("v (" + x + ", " + y + ") : " + v);
        // }


        return result;
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (this.height() <= 1) {
            throw new IllegalArgumentException();
        }

        if (seam.length != this.width()) {
            throw new IllegalArgumentException();
        }
        int previous = seam[0];
        if (previous < 0 || previous >= this.width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= this.width()) {
                throw new IllegalArgumentException();
            }
            if (seam[i] < previous - 1 || seam[i] > previous + 1) {
                throw new IllegalArgumentException();
            } 

            previous = seam[i];
        }

        Picture newPicture = new Picture(this.width(), this.height() - 1);
        for (int i = 0; i < this.width(); i++) {
            for (int j = 0; j < seam[i]; j++) {
                newPicture.set(i, j, picture.get(i, j));
            }            

            for (int j = seam[i] + 1; j < this.height(); j++) {
                newPicture.set(i, j - 1, picture.get(i, j));
            }            
        }

        this.picture = newPicture;
        this.energies = calculateEnergies();
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (this.width() <= 1) {
            throw new IllegalArgumentException();
        }

        if (seam.length != this.height()) {
            throw new IllegalArgumentException();
        }
        int previous = seam[0];
        if (previous < 0 || previous >= this.height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= this.height()) {
                throw new IllegalArgumentException();
            }
            if (seam[i] < previous - 1 || seam[i] > previous + 1) {
                throw new IllegalArgumentException();
            } 

            previous = seam[i];
        }


        Picture newPicture = new Picture(this.width() - 1, this.height());
        for (int i = 0; i < this.height(); i++) {
            for (int j = 0; j < seam[i]; j++) {
                newPicture.set(j, i, picture.get(j, i));
            }            

            for (int j = seam[i] + 1; j < this.width(); j++) {
                newPicture.set(j - 1, i, picture.get(j, i));
            }            
        }

        this.picture = newPicture;
        this.energies = calculateEnergies();
    }

    private double[][] calculateEnergies() {
        double[][] result = new double[this.width()][this.height()];
        Color nextI, prevI, nextJ, prevJ;

        for (int i = 0; i < this.width(); i++) {
            for (int j = 0; j < this.height(); j++) {
                if (i == 0 || i == this.width() - 1 || j == 0 || j == this.height() - 1) {
                    result[i][j] = 1000.0;
                    continue;
                }

                prevI = picture.get(i - 1, j);
                nextI = picture.get(i + 1, j);
                prevJ = picture.get(i, j - 1);
                nextJ = picture.get(i, j + 1);
                
                result[i][j] = Math.sqrt(
                    Math.pow(prevI.getGreen() - nextI.getGreen(), 2) +
                    Math.pow(prevI.getRed() - nextI.getRed(), 2) +
                    Math.pow(prevI.getBlue() - nextI.getBlue(), 2) +

                    Math.pow(prevJ.getGreen() - nextJ.getGreen(), 2) +
                    Math.pow(prevJ.getRed() - nextJ.getRed(), 2) +
                    Math.pow(prevJ.getBlue() - nextJ.getBlue(), 2)
                );
            }
        }

        return result;
    }
 
    //  unit testing (optional)
    public static void main(String[] args) {
        Picture p = new Picture("/Users/vasyly/coursera/algorithms-part2/week2/6x5.png");
        SeamCarver s = new SeamCarver(p);

        for (int i = 0; i < s.width(); i++) {
            for (int j = 0; j < s.height(); j++) {
                StdOut.print(String.format("%8.2f  ", s.energy(i, j)));                
            }
            StdOut.println();
        }

        // for (int i = 1; i < s.width() - 1; i++) {
        //     for (int j = 1; j < s.height() - 1; j++) {
        //         StdOut.println( "(" + i + ", " + j + ") :" + s.convertToVerticle(i, j) + " => " + s.convertToColumn(s.convertToVerticle(i, j)) + ", " + s.convertToRow(s.convertToVerticle(i, j)));                
        //     }
        //     StdOut.println();
        // }

        for (int v: s.findHorizontalSeam()) {
            StdOut.println("*v : " + v);
        }


        // EdgeWeightedDigraph G = new EdgeWeightedDigraph(9);
        // G.addEdge(new DirectedEdge(0, 2, 5.0));
        // G.addEdge(new DirectedEdge(0, 3, 4.0));
        // G.addEdge(new DirectedEdge(1, 3, 3.0));
        // G.addEdge(new DirectedEdge(1, 4, 2.0));

        // G.addEdge(new DirectedEdge(2, 5, 1.0));
        // G.addEdge(new DirectedEdge(3, 5, 2.0));
        // G.addEdge(new DirectedEdge(3, 6, 4.0));
        // G.addEdge(new DirectedEdge(4, 6, 1.0));

        // G.addEdge(new DirectedEdge(7, 0, 0.0));
        // G.addEdge(new DirectedEdge(7, 1, 0.0));

        // G.addEdge(new DirectedEdge(6, 8, 0.0));
        // G.addEdge(new DirectedEdge(5, 8, 0.0));

        // StdOut.println("G.V :" + G.V());
        // StdOut.println("G.E :" + G.E());

        // AcyclicSP sp = new AcyclicSP(G, 7);
        // for (int v = 0; v < G.V(); v++) {
        //     if (sp.hasPathTo(v)) {
        //         StdOut.printf("%d to %d (%.2f)  ", 7, v, sp.distTo(v));
        //         for (DirectedEdge e : sp.pathTo(v)) {
        //             StdOut.print(e + "   ");
        //         }
        //         StdOut.println();
        //     }
        //     else {
        //         StdOut.printf("%d to %d         no path\n", 7, v);
        //     }
        // }

        // Topological topological = new Topological(G);
        // for (int v : topological.order()) {
        //     StdOut.println("item :" + v );
        // }


    }
 }
