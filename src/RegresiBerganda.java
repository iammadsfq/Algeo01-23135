import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegresiBerganda {

    public static void regresiKuadratikBerganda(int n, int m, Scanner sc) {
        // Menghitung total jumlah kolom dalam proses regresi kuadratik berganda
        int totalColumns = 1 + n + n + (n * (n - 1)) / 2;

        Matrix X = new Matrix(m, totalColumns);  // Matriks X
        Matrix y = new Matrix(m, 1);              // Vektor y

        // Membaca nilai-nilai bagi setiap sampel
        for (int i = 0; i < m; i++) {
            X.contents[i][0] = 1; // Intercept
            System.out.println("Masukkan nilai untuk sampel ke-" + (i + 1) + ":");
            double[] xi = new double[n];
            for (int j = 0; j < n; j++) {
                System.out.print("x" + (j + 1) + ": "); // Perbaikan di sini
                xi[j] = sc.nextDouble();
                X.contents[i][j + 1] = xi[j]; // Memasukkan nilai variabel bebas linier ke matriks X
            }

            // Menambahkan variabel kuadrat dari setiap variabel
            int index = n + 1;
            for (int j = 0; j < n; j++) {
                X.contents[i][index] = xi[j] * xi[j]; // Nilai kuadrat dari variabel
                index++;
            }

            // Menambahkan interaksi antar variabel
            for (int j = 0; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    X.contents[i][index] = xi[j] * xi[k];
                    index++;
                }
            }

            System.out.print("y" + (i + 1) + ": ");
            y.contents[i][0] = sc.nextDouble();  // Membaca nilai variabel y untuk setiap sampel
        }

        // Hanya meminta nilai taksiran sebanyak n variabel
        double[] variable = new double[n];
        System.out.println("Masukkan nilai untuk menaksir y:");
        for (int i = 0; i < n; i++) {
            System.out.print("x" + (i + 1) + ": ");
            variable[i] = sc.nextDouble();
        }

        // Fungsi untuk menyelesaikan regresi kuadratik
        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        // Menampilkan hasil nilai koefisien regresi
        for (int i = 0; i < solusi.length; i++) {
            System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
        }

        // Menghitung taksiran
        double[] solusiDouble = new double[solusi.length]; // Perbaikan array ini
        for (int i = 0; i < solusi.length; i++) {
            solusiDouble[i] = Double.parseDouble(solusi[i]);
        }

        double Taksiran = solusiDouble[0]; // Mulai dari intercept
        for (int i = 1; i <= n; i++) { // Menggunakan <= n untuk memastikan loop sampai n
            Taksiran += solusiDouble[i] * variable[i - 1]; // i-1 untuk indeks array
        }

        String taksiranString = Double.toString(Taksiran);

        System.out.printf("Hampiran (taksiran) nilai f(x): " + taksiranString);
    }
    public static void bacaFileRegresiKuadratik(String filename) {
        try {
            // Membuka file untuk dibaca
            File file = new File("test/" + filename);
            Scanner sc = new Scanner(file);

            // Menghitung jumlah baris dan kolom dari file
            int m = 0; // Jumlah baris (sampel)
            int n = -1; // Jumlah kolom (variabel bebas) + 1 untuk y
            List<double[]> data = new ArrayList<>(); // Menyimpan semua data dari file

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] values = line.split("\\s+");
                    double[] row = new double[values.length];
                    for (int i = 0; i < values.length; i++) {
                        row[i] = Double.parseDouble(values[i]);
                    }
                    data.add(row); // Menambahkan setiap baris ke list
                    m++; // Menambah jumlah baris
                    if (n == -1) {
                        n = values.length - 1; // Set jumlah variabel (kolom) tanpa y
                    }
                }
            }

            // Memisahkan baris terakhir sebagai variableTaksiran
            double[] variableTaksiran = new double[n];
            for (int i = 0; i < n; i++) {
                variableTaksiran[i] = data.get(m - 1)[i]; // Mengambil baris terakhir sebagai variableTaksiran
            }

            // Jumlah sampel adalah total baris dikurangi 1 (untuk baris taksiran)
            m--;

            // Menghitung total jumlah kolom dalam proses regresi kuadratik berganda
            int totalColumns = 1 + n + n + (n * (n - 1)) / 2;

            // Membuat matriks X dan vektor y
            Matrix X = new Matrix(m, totalColumns);  // Matriks X
            Matrix y = new Matrix(m, 1);             // Vektor y

            for (int i = 0; i < m; i++) {
                X.contents[i][0] = 1; // Intercept
                double[] xi = new double[n];
                for (int j = 0; j < n; j++) {
                    xi[j] = data.get(i)[j]; // Mengambil nilai variabel bebas linier
                    X.contents[i][j + 1] = xi[j]; // Mengisi nilai variabel bebas linier ke X
                }

                // Menambahkan variabel kuadrat dari setiap variabel
                int index = n + 1;
                for (int j = 0; j < n; j++) {
                    X.contents[i][index] = xi[j] * xi[j]; // Nilai kuadrat dari variabel
                    index++;
                }

                // Menambahkan interaksi antar variabel
                for (int j = 0; j < n - 1; j++) {
                    for (int k = j + 1; k < n; k++) {
                        X.contents[i][index] = xi[j] * xi[k];
                        index++;
                    }
                }

                y.contents[i][0] = data.get(i)[n]; // Mengisi nilai y
            }

            sc.close();

            // Fungsi untuk menyelesaikan regresi kuadratik
            String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

            // Menampilkan hasil nilai koefisien regresi
            for (int i = 0; i < solusi.length; i++) {
                System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
            }

            // Melakukan perhitungan prediksi berdasarkan variableTaksiran
            double[] solusiDouble = new double[solusi.length];
            for (int i = 0; i < solusi.length; i++) {
                solusiDouble[i] = Double.parseDouble(solusi[i]);
            }

            double taksiran = solusiDouble[0]; // Mulai dari intercept
            int index = 1;

            // Menghitung kontribusi variabel linear
            for (int i = 0; i < n; i++) {
                taksiran += solusiDouble[index] * variableTaksiran[i];
                index++;
            }

            // Menghitung kontribusi kuadrat
            for (int i = 0; i < n; i++) {
                taksiran += solusiDouble[index] * variableTaksiran[i] * variableTaksiran[i];
                index++;
            }

            // Menghitung kontribusi interaksi antar variabel
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    taksiran += solusiDouble[index] * variableTaksiran[i] * variableTaksiran[j];
                    index++;
                }
            }

            System.out.printf("Hampiran (taksiran) nilai f(x): %f\n", taksiran);

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + filename);
        }
    }

    // Fungsi untuk mencari nilai hasil regresi kuadratik berganda
    public static String[] multipleRegressionSolution(Matrix X, Matrix y, int m, int totalColumns) {
        // Menghitung nilai transpose dari matriks X
        Matrix XT = OperasiMatrix.transpose(X);
        // Menghitung nilai hasil perkalian X^T * X
        Matrix XTX = SPL.kalikanMatriks(XT, X);
        // Menghitung nilai hasil perkalian X^T * y
        Matrix XTy = SPL.kalikanMatriks(XT, y);

        // Matriks augmented dari nilai  XTX dengan nilai XTy
        Matrix augmentedMatrix = OperasiMatrix.toAugmented(XTX, XTy);

        // Menggunakan metode eliminasi SPL Gauss dan mengembalikan solusi berupa nilai koefisien regresi
        return OperasiMatrix.SolveSPLGaussJordan(OperasiMatrix.ReductionREF(augmentedMatrix));
    }

    public static void regresiLinierBerganda(int n, int m, Scanner sc) {
        // Menghitung total jumlah kolom dalam proses regresi linier berganda
        int totalColumns = 1 + n;

        Matrix X = new Matrix(m, totalColumns);  // Matriks X
        Matrix y = new Matrix(m, 1);             // Vektor y

        // Membaca nilai-nilai bagi setiap sampel
        for (int i = 0; i < m; i++) {
            X.contents[i][0] = 1; // Intercept
            System.out.println("Masukkan nilai untuk sampel ke-" + (i + 1) + ":");
            for (int j = 0; j < n; j++) {
                System.out.print("x" + (i + 1) + (j + 1) + ": ");
                X.contents[i][j + 1] = sc.nextDouble(); // Memasukkan nilai variabel bebas linier ke matriks X
            }

            System.out.print("y" + (i + 1) + ": ");
            y.contents[i][0] = sc.nextDouble();  // Membaca nilai variabel y untuk setiap sampel
        }

        double[] variableTaksiran = new double[n];
        for(int i = 0; i < n; i++){
            System.out.print("Masukkan variable x" + (i + 1) + " untuk menaksir y:");
            variableTaksiran[i] = sc.nextDouble();
        }

        // Fungsi untuk menyelesaikan regresi linier
        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        // Menampilkan hasil nilai koefisien regresi
        for (int i = 0; i < solusi.length; i++) {
            System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
        }

        String taksiran = hampiran(n, solusi, variableTaksiran);
        System.out.printf("Hampiran (taksiran) nilai f(x): " + taksiran);



    }

    public static void bacaFileRegresiLinear(String filename) {
        try {
            // Membuka file untuk dibaca
            File file = new File("test/" + filename);
            Scanner sc = new Scanner(file);

            // Menghitung jumlah baris dan kolom dari file
            int m = 0; // Jumlah baris (sampel)
            int n = -1; // Jumlah kolom (variabel bebas) + 1 untuk y
            List<double[]> data = new ArrayList<>(); // Menyimpan semua data dari file

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] values = line.split("\\s+");
                    double[] row = new double[values.length];
                    for (int i = 0; i < values.length; i++) {
                        row[i] = Double.parseDouble(values[i]);
                    }
                    data.add(row); // Menambahkan setiap baris ke list
                    m++; // Menambah jumlah baris
                    if (n == -1) {
                        n = values.length - 1; // Set jumlah variabel (kolom) tanpa y
                    }
                }
            }

            // Memisahkan baris terakhir sebagai variableTaksiran
            double[] variableTaksiran = new double[n];
            for (int i = 0; i < n; i++) {
                variableTaksiran[i] = data.get(m - 1)[i]; // Mengambil baris terakhir sebagai variableTaksiran
            }

            // Jumlah sampel adalah total baris dikurangi 1 (untuk baris taksiran)
            m--;

            // Membuat matriks X dan vektor y
            Matrix X = new Matrix(m, n + 1); // Matrix X (1 kolom untuk intercept)
            Matrix y = new Matrix(m, 1); // Vektor y

            for (int i = 0; i < m; i++) {
                X.contents[i][0] = 1; // Intercept
                for (int j = 1; j <= n; j++) {
                    X.contents[i][j] = data.get(i)[j - 1]; // Mengisi nilai variabel ke X
                }
                y.contents[i][0] = data.get(i)[n]; // Mengisi nilai y
            }

            sc.close();

            // Menghitung total jumlah kolom (variabel bebas + 1 untuk intercept)
            int totalColumns = 1 + n;

            // Menyelesaikan regresi linier
            String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

            // Menampilkan hasil nilai koefisien regresi
            for (int i = 0; i < solusi.length; i++) {
                System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
            }

            // Menggunakan baris terakhir untuk prediksi nilai f(x)
            String taksiran = hampiran(n, solusi, variableTaksiran);
            System.out.printf("Hampiran (taksiran) nilai f(x): " + taksiran);

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + filename);
        }
    }

    public static String hampiran(int n, String[] solusi, double[] variable){

        double[] solusiDouble = new double[n+1];
        for (int i = 0; i <= n; i++) {
            solusiDouble[i] = Double.parseDouble(solusi[i]);
        }

        double Taksiran = solusiDouble[0];

        for (int i = 1; i <= n; i++) {
            Taksiran += solusiDouble[i] * variable[i-1];
        }

        return Double.toString(Taksiran);
    }



}