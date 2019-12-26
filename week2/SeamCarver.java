import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

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

        return result;
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
    }
 }
