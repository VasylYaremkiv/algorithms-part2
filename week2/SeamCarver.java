import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.AcyclicSP;

import java.util.Arrays;
import java.awt.Color;


public class SeamCarver {
    private Picture picture;
    private double[][] energies;

    // create a seam carver object based on the given picture
    public SeamCarver(final Picture p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(p.width(), p.height());
        for (int i = 0; i < p.width(); i++) {
            for (int j = 0; j < p.height(); j++) {
                this.picture.set(i, j, p.get(i, j));
            }            
        }

        this.energies = calculateEnergies();
    }
 
    // current picture
    public Picture picture() {
        Picture result = new Picture(this.width(), this.height());

        for (int i = 0; i < this.width(); i++) {
            for (int j = 0; j < this.height(); j++) {
                result.set(i, j, picture.get(i, j));
            }            
        }

        return result;
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

        if (this.width() < 3 || this.height() < 3) {
            Arrays.fill(result, 0);
            return result;
        }

        int top = (this.width() - 1) * (this.height() - 2);
        int bottom = top + 1;

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(top + 2);

        int level = this.height() - 2;
        int v = 0;
        double energy = 0;
        for (int i = 1; i < this.width() - 1; i++) {
            for (int j = 1; j < this.height() - 1; j++) {
                energy = energies[i][j];
                G.addEdge(new DirectedEdge(v, v + level, energy));
                if (j > 1) {
                    G.addEdge(new DirectedEdge(v, v + level - 1, energy));
                }
                if (j < this.height() - 2) {
                    G.addEdge(new DirectedEdge(v, v + level + 1, energy));
                }
                v++;
            }
        }

        for (int i = 0; i < level; i++) {
            G.addEdge(new DirectedEdge(top, i, 0.0));
            G.addEdge(new DirectedEdge(bottom - i - 2, bottom, 0.0));
        }

        AcyclicSP sp = new AcyclicSP(G, top);
        int i = 0;
        for (DirectedEdge e : sp.pathTo(bottom)) {
            if (e.from() == top) {
                result[i++] = e.to() + 1;
                continue;
            }
            result[i++] = e.from() % level + 1;
        }

        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] result = new int[this.height()];

        if (this.width() < 3 || this.height() < 3) {
            Arrays.fill(result, 0);
            return result;
        }

        int top = (this.width() - 2) * (this.height() - 1);
        int bottom = top + 1;

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(top + 2);

        int level = this.width() - 2;
        int v = 0;
        for (int i = 1; i < this.height() - 1; i++) {
            for (int j = 1; j < this.width() - 1; j++) {
                G.addEdge(new DirectedEdge(v, v + level, energies[j][i]));
                if (j > 1) {
                    G.addEdge(new DirectedEdge(v, v + level - 1, energies[j][i]));
                }
                if (j < this.width() - 2) {
                    G.addEdge(new DirectedEdge(v, v + level + 1, energies[j][i]));
                }
                v++;
            }
        }

        for (int i = 0; i < level; i++) {
            G.addEdge(new DirectedEdge(top, i, 0.0));
            G.addEdge(new DirectedEdge(bottom - i - 2, bottom, 0.0));
        }
        
        AcyclicSP sp = new AcyclicSP(G, top);
        int i = 0;
        for (DirectedEdge e : sp.pathTo(bottom)) {
            if (e.from() == top) {
                result[i++] = e.to() + 1;
                continue;
            }
            result[i++] = e.from() % level + 1;
        }

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
        // Picture p = new Picture("/Users/vasyly/coursera/algorithms-part2/week2/6x5.png");
        // // Picture p = new Picture("/Users/vasyly/coursera/algorithms-part2/week2/3x4.png");
        // SeamCarver s = new SeamCarver(p);

        // for (int i = 0; i < s.height(); i++) {
        //     for (int j = 0; j < s.width(); j++) {
        //         StdOut.print(String.format("%8.2f  ", s.energy(j, i)));                
        //     }
        //     StdOut.println();
        // }


        // for (int v: s.findHorizontalSeam()) {
        //     StdOut.println("*v : " + v);
        // }

    }
 }
