import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;


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

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(this.width() * this.height() - this.ignoredVerticles());

        for (int i = 1; i < this.width() - 2; i++) {
            for (int j = 1; j < this.height() - 1; j++) {
                G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j), energies[i][j]));

                // if (j > 1) {
                //     G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j - 1), energies[i][j]));
                // }

                // if (j < this.height() - 2) {
                //     G.addEdge(new DirectedEdge(convertToVerticle(i, j), convertToVerticle(i + 1, j + 1), energies[i][j]));
                // }
            }
        }



        StdOut.println("G.V :" + G.V());
        StdOut.println("G.E :" + G.E());

        Topological topological = new Topological(G);
        int x, y;
        for (int v : topological.order()) {
            // for (DirectedEdge e : G.adj(v)) {
            //     relax(e);
            // }
            x = this.convertToColumn(v);
            y = this.convertToRow(v);
            StdOut.println("v (" + x + ", " + y + ") : " + v);
        }


        return result;
    }

    public int convertToVerticle(int x, int y) {
        return (x - 1) * (this.height() - 2) + y - 1;
    }

    public int convertToColumn(int v) {
        return (v + 0) / (this.height() - 2) + 1;
    }

    public int convertToRow(int v) {
        return (v + 0) % (this.height() - 2) + 1;
    }

    private int ignoredVerticles() {
        return this.width() * 2 + this.height() * 2 - 4;
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] result = new int[this.height()];

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
        Picture p = new Picture("6x5.png");
        SeamCarver s = new SeamCarver(p);

        for (int i = 0; i < s.width(); i++) {
            for (int j = 0; j < s.height(); j++) {
                StdOut.print(String.format("%8.2f  ", s.energy(i, j)));                
            }
            StdOut.println();
        }

        for (int i = 1; i < s.width() - 1; i++) {
            for (int j = 1; j < s.height() - 1; j++) {
                StdOut.println( "(" + i + ", " + j + ") :" + s.convertToVerticle(i, j) + " => " + s.convertToColumn(s.convertToVerticle(i, j)) + ", " + s.convertToRow(s.convertToVerticle(i, j)));                
            }
            StdOut.println();
        }


        // StdOut.println("v(1,1) : " + s.convertToVerticle(1, 1));
        // StdOut.println("v(1,2) : " + s.convertToVerticle(1, 2));
        // StdOut.println("v(2,1) : " + s.convertToVerticle(2, 1));
        // StdOut.println("v(2,3) : " + s.convertToVerticle(2, 3));

        // StdOut.println("v(*3) : " + s.convertToColumn(3));
        // StdOut.println("v(*5) : " + s.convertToColumn(5));

        for (int v: s.findHorizontalSeam()) {
            StdOut.println("*v : " + v);
        }
    }
 }
