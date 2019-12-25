import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
    private Picture p;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }


        p = picture;
    }
 
    // current picture
    public Picture picture() {
        return p;
    }
 
    // width of current picture
    public int width() {
        return p.width();
    }
 
    // height of current picture
    public int height() {
        return p.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= this.width()) {
            throw new IllegalArgumentException();
        }
        if (y < 0 || y >= this.height()) {
            throw new IllegalArgumentException();
        }



        return 0.0;
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




    }
 
    //  unit testing (optional)
    public static void main(String[] args) {

    }
 }
