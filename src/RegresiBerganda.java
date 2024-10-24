import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RegresiBerganda {

    public static void regresiKuadratikBerganda(int n, int m, Scanner sc) {
        // Menghitung total jumlah kolom dalam proses regresi kuadratik berganda
        int totalColumns = 1 + n + n + (n * (n - 1)) / 2;

        Matrix X = new Matrix(m,totalColumns);  // Matriks X
        Matrix y = new Matrix(m,1);         // Vektor y

        // Membaca nilai-nilai bagi setiap sampel
        for (int i = 0; i < m; i++) {
            X.contents[i][0] = 1; // Intercept
            System.out.println("Masukkan nilai untuk sampel ke-" + (i + 1) + ":");
            double[] xi = new double[n];
            for (int j = 0; j < n; j++) {
                System.out.print("x" + (i + 1) + (j + 1) + ": ");
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
            y.contents[i][0] = sc.nextDouble();  // Melihat nilai variabel y untuk setiap sampel
        }

        double[] variable = new double[n];
        for(int i = 0; i < m; i++){
            System.out.print("Masukkan variable x" + (i+1) + " untuk menaksir y:");
            variable[i] = sc.nextDouble();
        }

        // Fungsi untuk menyelesaikan regresi kuadratik
        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        // Menampilkan hasil nilai koefisien regresi
        for (int i = 0; i < solusi.length; i++) {
            System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
        }

        double[] solusiDouble = new double[n];
        for (int i = 0; i < n; i++) {
            solusiDouble[i] = Double.parseDouble(solusi[i]);
        }

        double Taksiran = solusiDouble[0];
        for (int i = 1; i < n; i++) {
            Taksiran += solusiDouble[i] * variable[i];
        }


        String taksiranString = Double.toString(Taksiran);

        System.out.printf("Hampiran (taksiran) nilai f(x): " + taksiranString);
    }
    public static void bacaFileRegresiKuadratikBerganda(String fileName) {
        try {
            File file = new File("test/" + fileName);
            Scanner fileScanner = new Scanner(file);
            ArrayList<double[]> dataList = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String[] lineData = fileScanner.nextLine().split(" ");
                double[] rowData = new double[lineData.length];
                for (int i = 0; i < lineData.length; i++) {
                    rowData[i] = Double.parseDouble(lineData[i]);
                }
                dataList.add(rowData);
            }
            fileScanner.close();

            int m = dataList.size();
            int n = dataList.get(0).length - 1;
            int totalColumns = 1 + n + n + (n * (n - 1)) / 2;
            Matrix X = new Matrix(m, totalColumns);
            Matrix y = new Matrix(m, 1);
            for (int i = 0; i < m; i++) {
                X.contents[i][0] = 1;
                double[] xi = new double[n];
                for (int j = 0; j < n; j++) {
                    xi[j] = dataList.get(i)[j];
                    X.contents[i][j + 1] = xi[j];
                }

                int index = n + 1;
                for (int j = 0; j < n; j++) {
                    X.contents[i][index] = xi[j] * xi[j];
                    index++;
                }

                for (int j = 0; j < n - 1; j++) {
                    for (int k = j + 1; k < n; k++) {
                        X.contents[i][index] = xi[j] * xi[k];
                        index++;
                    }
                }

                y.contents[i][0] = dataList.get(i)[n];
            }

            String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

            for (int i = 0; i < solusi.length; i++) {
                System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
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
        augmentedMatrix.TulisMatrix();

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

    public static void bacaFileRegresiLinear(String fileName) throws FileNotFoundException {
        // Membuka file dan menyiapkan scanner
        File file = new File("test/" + fileName);
        Scanner fileScanner = new Scanner(file);

        // Menggunakan ArrayList untuk menampung sementara data dari file
        ArrayList<double[]> dataList = new ArrayList<>();

        // Membaca semua data dari file
        while (fileScanner.hasNextLine()) {
            String[] lineData = fileScanner.nextLine().split(" ");
            double[] rowData = new double[lineData.length];
            for (int i = 0; i < lineData.length; i++) {
                rowData[i] = Double.parseDouble(lineData[i]);
            }
            dataList.add(rowData);
        }
        fileScanner.close();

        // Menghitung m dan n
        int m = dataList.size() - 1; // Jumlah baris untuk regresi, baris terakhir digunakan untuk variabel baru
        int n = dataList.get(0).length - 1; // Jumlah variabel bebas (tanpa y)

        // Mengambil baris terakhir sebagai variabelBaru (tanpa y)
        double[] variabelBaru = dataList.remove(dataList.size() - 1);

        // Menghitung total kolom yang dibutuhkan untuk regresi linier berganda
        int totalColumns = 1 + n; // 1 untuk intercept dan n untuk variabel bebas

        // Membuat matriks X dan vektor y
        Matrix X = new Matrix(m, totalColumns);  // Matriks X
        Matrix y = new Matrix(m, 1);             // Vektor y

        // Memasukkan nilai ke dalam matriks X dan y
        for (int i = 0; i < m; i++) {
            X.contents[i][0] = 1; // Intercept
            for (int j = 0; j < n; j++) {
                X.contents[i][j + 1] = dataList.get(i)[j]; // Nilai variabel bebas
            }
            // Menyimpan nilai y (variabel respon)
            y.contents[i][0] = dataList.get(i)[n];
        }

        // Fungsi untuk menyelesaikan regresi linier
        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        // Menampilkan hasil nilai koefisien regresi
        for (int i = 0; i < solusi.length; i++) {
            System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
        }

        // Menghitung dan menampilkan hasil taksiran (prediksi) berdasarkan variabelBaru
        String taksiran = hampiran(n, solusi, variabelBaru);
        System.out.printf("Hampiran (taksiran) nilai f(x): %s\n", taksiran);
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