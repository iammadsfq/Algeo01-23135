import java.util.Scanner;
import java.io.File;

public class Matrix {
    public double[][] contents;
    public int rows;
    public int cols;

    public Matrix(int row, int col) { //Konstruktor
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Panjang baris dan kolom harus di atas 0!");
        }
        this.rows = row;
        this.cols = col;
        contents = new double[row][col];
    }
    public int getLastRow() { //Get this.rows
        return this.rows - 1;
    }
    public int getLastCol() { //Get this.cols
        return this.cols - 1;
    }

    public boolean isValidRow(int row) {
        return (row >= 0 && row <= this.getLastRow());
    }
    public boolean isValidCol(int col) {
        return (col >= 0 && col <= this.getLastCol());
    }
    //tidak ada setter untuk row dan col karena tidak berubah (bikin sendiri klo berubah)
    public void setElement(int row, int col, double val) { //set m.contents[i][j]
        if (this.isValidRow(row) && this.isValidCol(col)) { // row n col valid
            this.contents[row][col] = val;
        }
        else {
            throw new IndexOutOfBoundsException("Indeks baris atau kolom berada di luar batas!");
        }
    }
}