import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Determinan {
    public static void calculateDetBySarrus(Matrix m) {
        double result = OperasiMatrix.returnDetBySarrus(m);

        // Output matrix and determinant
        System.out.println("Determinan dari matrix:");
        m.TulisMatrix();
        System.out.println("adalah " + result);

        // Ask if the user wants to save the output
        saveOutput(m, result);
    }

    public static void calculateDetByCofactorExpansion(Matrix matrix) {
        if (matrix.rows != matrix.cols) {
            throw new IllegalArgumentException("Determinant hanya dapat dihitung untuk matriks persegi (NxN)");
        }

        // Output the matrix
        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();

        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);
        System.out.println("Determinan dari matriks adalah: " + determinant);

        // Ask if the user wants to save the output
        saveOutput(matrix, determinant);
    }

    private static void saveOutput(Matrix matrix, double result) {
        // Ask if the user wants to save the output
        Scanner scanner = new Scanner(System.in); // Create Scanner instance here
        System.out.print("Apakah Anda ingin menyimpan output? (ya/tidak): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if ("ya".equals(response)) {
            String filename;
            while (true) {
                System.out.print("Masukkan nama file untuk menyimpan output (misal: determinan.txt): ");
                filename = scanner.nextLine().trim();

                // Validate filename (you can add more validation as needed)
                if (filename.isEmpty()) {
                    System.out.println("Nama file tidak boleh kosong. Silakan coba lagi.");
                } else {
                    // Save to file
                    saveDetToFile(matrix, result, filename);
                    break; // Exit loop if filename is valid
                }
            }
        } else {
            System.out.println("Output tidak disimpan.");
        }
    }

    private static void saveDetToFile(Matrix matrix, double result, String filename) {
        StringBuilder output = new StringBuilder();
        output.append("Determinan dari matrix:\n");

        // Append the matrix
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                output.append(matrix.contents[i][j]).append("\t");
            }
            output.append("\n"); // next row
        }

        // Append the result
        output.append("adalah ").append(result).append("\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(output.toString());
            System.out.println("Output disimpan ke " + filename);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menulis ke file: " + e.getMessage());
        }
    }

    public static void calculateDetByRowReduction(Matrix matrix) {
        if (matrix.rows != matrix.cols) {
            throw new IllegalArgumentException("Determinant hanya dapat dihitung untuk matriks persegi (NxN)");
        }

        double result = OperasiMatrix.returnDetByRowReduction(matrix, matrix.rows);

        // Output matrix and determinant
        System.out.println("Determinan dari matrix:");
        matrix.TulisMatrix();
        System.out.println("adalah " + result);

        // Ask if the user wants to save the output
        Scanner scanner = new Scanner(System.in); // Create Scanner instance here
        System.out.print("Apakah Anda ingin menyimpan output? (ya/tidak): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if ("ya".equals(response)) {
            String filename;
            while (true) {
                System.out.print("Masukkan nama file untuk menyimpan output (misal: determinan.txt): ");
                filename = scanner.nextLine().trim();

                // Validate filename (you can add more validation as needed)
                if (filename.isEmpty()) {
                    System.out.println("Nama file tidak boleh kosong. Silakan coba lagi.");
                } else {
                    // Save to file
                    saveDetToFile(matrix, result, filename);
                    break; // Exit loop if filename is valid
                }
            }
        } else {
            System.out.println("Output tidak disimpan.");
        }

        // Close the scanner
    }
}