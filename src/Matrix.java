import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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

    public static Matrix readMatrixFromKeyboard() {
        Scanner sc = new Scanner(System.in);

        // Minta Dimensi Matrix
        System.out.print("Jumlah Baris: ");
        int rows = sc.nextInt();

        System.out.print("Jumlah Kolom: ");
        int cols = sc.nextInt();

        // Buat Matrix kosong
        Matrix matrix = new Matrix(rows, cols);

        // Masukin value
        System.out.println("Masukkan elemen matriks per baris");
        for (int i = 0; i < rows; i++) {
            System.out.print("Baris ke-" + (i+1) + ":");
            for (int j = 0; j < cols; j++) {

                double value = sc.nextDouble();
                matrix.setElement(i, j, value);
            }
        }
        return matrix;
    }
    public static Matrix readMatrixFromFile(String fileName) {
        Matrix matrix = null;

        try {
            // Create a File object to open the file
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            // Temporary list to store rows of matrix values
            List<double[]> tempMatrix = new ArrayList<>();
            int cols = -1;

            // Read each line from the file
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.trim().split("\\s+"); // Split by whitespace

                // Convert the string values to doubles
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Double.parseDouble(values[i]);
                }

                // Check if the number of columns is consistent
                if (cols == -1) {
                    cols = values.length; // Set the number of columns for the first row
                } else if (cols != values.length) {
                    throw new IllegalArgumentException("Jumlah Kolom Tidak Konsisten!");
                }
                // Add the row to the temp matrix list
                tempMatrix.add(row);
            }

            // Now create the matrix with calculated rows and cols
            int rows = tempMatrix.size();
            matrix = new Matrix(rows, cols);

            // Populate the matrix with the values from the temp list
            for (int i = 0; i < rows; i++) {
                double[] row = tempMatrix.get(i);
                for (int j = 0; j < cols; j++) {
                    matrix.setElement(i, j, row[j]);
                }
            }

            sc.close();  // Close the scanner after reading
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + fileName + "' tidak ditemukan.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Terdapat kesalahan format angka dalam konten file.");
        }

        return matrix;  // Return the populated matrix
    }
    public static Matrix readNxNMatrixFromKeyboard() {
        Scanner sc = new Scanner(System.in);

        // Minta ukuran matriks NxN
        System.out.print("Masukkan N (ukuran matriks NxN): ");
        int n = sc.nextInt();

        // Buat Matrix kosong NxN
        Matrix matrix = new Matrix(n, n);

        // Masukkan nilai-nilai elemen matriks
        System.out.println("Masukkan elemen matriks per baris");
        for (int i = 0; i < n; i++) {
            System.out.print("Baris ke-" + (i+1) + ": ");
            for (int j = 0; j < n; j++) {
                double value = sc.nextDouble();
                matrix.setElement(i, j, value);
            }
        }
        return matrix;
    }
    public static Matrix readNxNMatrixFromFile(String fileName) {
        Matrix matrix = null;

        try {
            // Create a File object to open the file
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            // Temporary list to store rows of matrix values
            List<double[]> tempMatrix = new ArrayList<>();
            int n = -1;  // This will represent both rows and columns for an NxN matrix

            // Read each line from the file
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.trim().split("\\s+"); // Split by whitespace

                // Convert the string values to doubles
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Double.parseDouble(values[i]);
                }

                // Check if the matrix is square (N x N)
                if (n == -1) {
                    n = values.length; // Set N from the first row's length
                } else if (n != values.length) {
                    throw new IllegalArgumentException("Matrix bukan NxN. Kolom tidak konsisten!");
                }

                // Add the row to the temp matrix list
                tempMatrix.add(row);
            }

            // Validate if the matrix is square
            if (tempMatrix.size() != n) {
                throw new IllegalArgumentException("Matrix bukan NxN. Baris tidak konsisten!");
            }

            // Create the NxN matrix
            matrix = new Matrix(n, n);

            // Populate the matrix with the values from the temp list
            for (int i = 0; i < n; i++) {
                double[] row = tempMatrix.get(i);
                for (int j = 0; j < n; j++) {
                    matrix.setElement(i, j, row[j]);
                }
            }

            sc.close();  // Close the scanner after reading
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + fileName + "' tidak ditemukan.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Terdapat kesalahan format angka dalam konten file.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return matrix;  // Return the populated matrix
    }



    public void TulisMatrix() {
        // Menampilkan Matrix ke layar
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                // \t (tab space) untuk alignment
                System.out.print(this.contents[i][j] + "\t");
            }
            System.out.println(); // next row
        }
    }


}