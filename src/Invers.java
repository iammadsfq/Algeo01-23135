import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Invers {

    // Fungsi untuk mendapatkan invers matriks menggunakan metode adjoin
    public static void getInversByAdjoint(Matrix matrix) {
        int n = matrix.rows;

        // Memeriksa apakah matriks adalah matriks bersifat persegi
        if (matrix.rows != matrix.cols) {
            System.out.println("Invers hanya dapat dihitung untuk matriks persegi (NxN)");
            return;
        }

        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);
        if (determinant == 0) { // Jika determinan 0, matriks tidak memiliki invers
            System.out.println("Matriks tidak memiliki invers karena determinan adalah 0.");
            return;
        }

        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();

        Matrix inversMatrix = OperasiMatrix.returnInversByAdjoint(matrix);
        if (inversMatrix == null) {
            System.out.println("Matrix tidak memiliki invers");
        } else {
            System.out.println("Invers dari matriks dengan metode adjoin adalah:");
            inversMatrix.TulisMatrix();
            saveOutput(matrix, inversMatrix, "metode adjoin");
        }
    }

    // Fungsi Matriks untuk adjoint
    public static void getInversByRowReduction(Matrix matrix) {
        int n = matrix.rows;
        if (matrix.rows != matrix.cols) {
            System.out.println("Invers hanya dapat dihitung untuk matriks persegi (NxN)");
            return;
        }

        double determinant = OperasiMatrix.returnDetByRowReduction(matrix, matrix.rows);
        if (determinant == 0) {
            System.out.println("Matriks tidak memiliki invers karena determinan adalah 0.");
            return;
        }

        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();

        Matrix inversMatrix = OperasiMatrix.returnInversByGaussJordan(matrix, n);
        System.out.println("Invers dari matriks dengan metode Gauss-Jordan adalah:");
        inversMatrix.TulisMatrix();
        saveOutput(matrix, inversMatrix, "metode Gauss-Jordan");
    }

    private static void saveOutput(Matrix originalMatrix, Matrix inversMatrix, String method) {
        // Ask if the user wants to save the output
        Scanner scanner = new Scanner(System.in); // Create Scanner instance here
        System.out.print("Apakah Anda ingin menyimpan output? (ya/tidak): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if ("ya".equals(response)) {
            String filename;
            while (true) {
                System.out.print("Masukkan nama file untuk menyimpan output (misal: invers.txt): ");
                filename = scanner.nextLine().trim();

                // Validate filename (you can add more validation as needed)
                if (filename.isEmpty()) {
                    System.out.println("Nama file tidak boleh kosong. Silakan coba lagi.");
                } else {
                    // Save to file
                    saveInversToFile(originalMatrix, inversMatrix, filename, method);
                    break; // Exit loop if filename is valid
                }
            }
        } else {
            System.out.println("Output tidak disimpan.");
        }
    }

    private static void saveInversToFile(Matrix originalMatrix, Matrix inversMatrix, String filename, String method) {
        StringBuilder output = new StringBuilder();
        output.append("Matriks Asli:\n");

        // Append the original matrix
        for (int i = 0; i < originalMatrix.rows; i++) {
            for (int j = 0; j < originalMatrix.cols; j++) {
                output.append(originalMatrix.contents[i][j]).append("\t");
            }
            output.append("\n"); // next row
        }

        output.append("\nInvers dari matriks dengan ").append(method).append(" adalah:\n");

        // Append the inverse matrix
        for (int i = 0; i < inversMatrix.rows; i++) {
            for (int j = 0; j < inversMatrix.cols; j++) {
                output.append(inversMatrix.contents[i][j]).append("\t");
            }
            output.append("\n"); // next row
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(output.toString());
            System.out.println("Output disimpan ke " + filename);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menulis ke file: " + e.getMessage());
        }
    }
}
