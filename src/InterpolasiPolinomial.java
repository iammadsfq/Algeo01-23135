import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class InterpolasiPolinomial {
    public static void bacaKeyboardInterpolasiPolinomial() {
        Scanner sc = new Scanner(System.in);

        // Minta jumlah titik
        System.out.print("Masukkan jumlah titik data n: ");
        int n = sc.nextInt();

        // Inisialisasi array x dan y
        double[] x = new double[n];
        double[] y = new double[n];

        // Input nilai x dan y dari keyboard
        for (int i = 0; i < n; i++) {
            System.out.print("Masukkan nilai x" + i + ": ");
            x[i] = sc.nextDouble();

            System.out.print("Masukkan nilai y" + i + ": ");
            y[i] = sc.nextDouble();
        }

        // Minta input nilai x untuk interpolasi
        System.out.print("Masukkan nilai x yang ingin diinterpolasi: ");
        double xTarget = sc.nextDouble();

        // Bentuk Matriks Vandermonde
        Matrix V = createVandermondeMatrix(x, n);
        Matrix vektorY = arrayToVector(y, n);

        // Lakukan interpolasi
        double hasilInterpolasi = interpolate(V, vektorY, xTarget);

        // Tanyakan apakah ingin menyimpan hasil
        System.out.print("Apakah Anda ingin menyimpan hasil? (ya/tidak): ");
        String saveResponse = sc.next();

        if (saveResponse.equalsIgnoreCase("ya")) {
            System.out.print("Masukkan nama file untuk menyimpan hasil: ");
            String fileName = sc.next();
            saveOutputToFile(fileName, hasilInterpolasi, xTarget);
        }
    }

    public static void bacaFileInterpolasiPolinomial(String fileName) {
        try {
            File file = new File("test/" + fileName);
            Scanner sc = new Scanner(file);
            int n = 0;

            // Count the number of lines
            while (sc.hasNextLine()) {
                sc.nextLine();
                n++;
            }

            sc.close(); // Close the scanner before reopening
            sc = new Scanner(file); // Restart scanner
            n--; // Exclude the last line for interpolation target

            double[] x = new double[n];
            double[] y = new double[n];
            for (int i = 0; i < n; i++) {
                if (sc.hasNextDouble()) {
                    x[i] = sc.nextDouble();
                    y[i] = sc.nextDouble();
                }
            }
            double xTarget = sc.nextDouble(); // Read the target value

            Matrix V = createVandermondeMatrix(x, n);
            Matrix vektorY = arrayToVector(y, n);
            double hasilInterpolasi = interpolate(V, vektorY, xTarget);

            // Tanyakan apakah ingin menyimpan hasil
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Apakah Anda ingin menyimpan hasil? (ya/tidak): ");
            String saveResponse = inputScanner.next();

            if (saveResponse.equalsIgnoreCase("ya")) {
                System.out.print("Masukkan nama file untuk menyimpan hasil: ");
                String fileNameToSave = inputScanner.next();
                saveOutputToFile(fileNameToSave, hasilInterpolasi, xTarget);
            }
            inputScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + fileName);
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    public static double interpolate(Matrix V, Matrix vektorY, double xTarget) {
        if (OperasiMatrix.cofactorExpansion(V, V.cols) == 0) {
            System.out.println("Matriks Vandermonde singular (determinannya 0). Tidak bisa melakukan interpolasi.");
            return Double.NaN; // Return NaN if singular
        }

        Matrix V_inv = OperasiMatrix.returnInversByGaussJordan(V, V.rows);
        if (V_inv == null) {
            System.out.println("Matriks Vandermonde tidak memiliki invers. Tidak bisa melakukan interpolasi.");
            return Double.NaN; // Return NaN if no inverse
        }

        Matrix a = SPL.kalikanMatriks(V_inv, vektorY);

        double hasilInterpolasi = 0;
        System.out.print("f(x) = ");

        // Handle first term (a0)
        hasilInterpolasi += a.contents[0][0] * Math.pow(xTarget, 0);
        System.out.print(Math.round(a.contents[0][0] * 10000) / 10000);

        for (int i = 1; i < a.rows; i++) {
            if (a.contents[i][0] != 0) { // Skip zero coefficients
                double coef = a.contents[i][0];

                // Handle positive coefficients
                if (coef > 0) {
                    System.out.print("+");
                }

                System.out.print(Math.round(a.contents[i][0] * 10000) / 10000);
                if (i == 1) {
                    System.out.print("x"); // Print x for power 1
                } else {
                    System.out.print("x^" + i); // Print x^i for higher powers
                }

                hasilInterpolasi += a.contents[i][0] * Math.pow(xTarget, i);
            }
        }

        // Output hasil interpolasi
        System.out.println();
        System.out.println("Hasil interpolasi untuk x = " + xTarget + " adalah: " + Math.round(hasilInterpolasi * 10000) / 10000.0);

        return hasilInterpolasi; // Return the result for file saving
    }

    public static void saveOutputToFile(String fileName, double hasilInterpolasi, double xTarget) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.println("Hasil interpolasi untuk x = " + xTarget + " adalah: " + Math.round(hasilInterpolasi * 10000) / 10000.0);
            System.out.println("Hasil telah disimpan di " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }

    public static Matrix createVandermondeMatrix(double[] x, int n) {
        Matrix V = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                V.contents[i][j] = Math.pow(x[i], j);
            }
        }
        return V;
    }

    public static Matrix arrayToVector(double[] l, int n) {
        Matrix result = new Matrix(n, 1);
        for (int i = 0; i < n; i++) {
            result.contents[i][0] = l[i];
        }
        return result;
    }
}
