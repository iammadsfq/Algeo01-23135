import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class IO {
    public static void tekanEnterUntukKembali() {
        System.out.println("\nTekan Enter untuk kembali ke menu utama...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    public static void tulisSolusiSPL(String[] solutions) { // Untuk Penyelesaian SPL
        StringBuilder output = new StringBuilder();

        if (Objects.equals(solutions[0], "Tidak ada solusi")) {
            output.append("Tidak ada solusi");
        } else {
            output.append("x1 = ").append(solutions[0]);
            for (int i = 1; i < solutions.length; i++) {
                if (solutions[i] != null) {
                    output.append(", x").append(i + 1).append(" = ").append(solutions[i]);
                }
            }
        }

        // Print to console (optional)
        System.out.println(output.toString());

        // Ask if the user wants to save the output
        Scanner scanner = new Scanner(System.in); // Create Scanner instance here
        System.out.print("Apakah Anda ingin menyimpan output? (ya/tidak): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if ("ya".equals(response)) {
            String filename;
            while (true) {
                System.out.print("Masukkan nama file untuk menyimpan output (misal: solusi_spl.txt): ");
                filename = scanner.nextLine().trim();

                // Validate filename (you can add more validation as needed)
                if (filename.isEmpty()) {
                    System.out.println("Nama file tidak boleh kosong. Silakan coba lagi.");
                } else {
                    // Save to file
                    saveToFile(output.toString(), filename);
                    break; // Exit loop if filename is valid
                }
            }
        } else {
            System.out.println("Output tidak disimpan.");
        }
    }

    private static void saveToFile(String output, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(output);
            System.out.println("Output disimpan ke " + filename);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menulis ke file: " + e.getMessage());
        }
    }



}