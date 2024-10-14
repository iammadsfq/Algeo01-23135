import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Math.pow;

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
        // Bentuk vektor_y
        Matrix vektorY = arrayToVector(y, n);

        // Lakukan interpolasi
        interpolate(V, vektorY, xTarget);
    }
    public static void bacaFileInterpolasiPolinomial(String fileName) {
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            int n = 0;
            while (sc.hasNextLine()) {
                sc.nextLine();
                n++;
            }

            sc = new Scanner(file);  // Restart scanner
            n--;  // ga ngitung baris terakhir (krn baris terakhir itu buat x yg diinterpolasiin)

            double[] x = new double[n];
            double[] y = new double[n];
            for (int i = 0; i < n; i++) {
                if (sc.hasNextDouble()) {
                    x[i] = sc.nextDouble();
                    y[i] = sc.nextDouble();
                }
            }
            double xTarget = sc.nextDouble();

            sc.close(); //selesai scanner

            Matrix V = createVandermondeMatrix(x, n);
            Matrix vektorY = arrayToVector(y, n);

            interpolate(V, vektorY, xTarget);

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + fileName);
        }
    }

    public static void interpolate(Matrix V, Matrix vektorY, double xTarget) {
        if (OperasiMatrix.cofactorExpansion(V, V.cols) == 0) {
            System.out.println("Matriks Vandermonde singular (determinannya 0). Tidak bisa melakukan interpolasi.");
            return;
        }

        Matrix V_inv = OperasiMatrix.returnInversByAdjoint(V);
        Matrix a = SPL.kalikanMatriks(V_inv, vektorY);

        double hasilInterpolasi = 0;
        for (int i = 0; i < a.rows; i++) {
            hasilInterpolasi += a.contents[i][0]* Math.pow(xTarget, i);
        }

        // Output hasil interpolasi
        System.out.println("Hasil interpolasi untuk x = " + xTarget + " adalah: " + hasilInterpolasi);
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
        int i;
        for (i = 0; i < n; i++) {
            result.contents[i][0] = l[i];
        }
        return result;
    }


}
