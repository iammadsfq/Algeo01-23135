import java.util.Scanner;

public class RegresiBerganda {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Proses input jumlah peubah (n) dan sampel (m)
        System.out.print("Masukkan jumlah peubah (n): ");
        int n = sc.nextInt();
        System.out.print("Masukkan jumlah sampel (m): ");
        int m = sc.nextInt();

        // Memanggil metode untuk menghitung regresi kuadratik berganda
        // regresiLinierBerganda(n,m,sc);
        // regresiKuadratikBerganda(n,m,sc);
    }

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

        // Fungsi untuk menyelesaikan regresi kuadratik
        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        // Menampilkan hasil nilai koefisien regresi
        for (int i = 0; i < solusi.length; i++) {
            System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
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

        // Fungsi untuk menyelesaikan regresi linier
        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        // Menampilkan hasil nilai koefisien regresi
        for (int i = 0; i < solusi.length; i++) {
            System.out.printf("Koefisien Regresi β%d = %s\n", i, solusi[i]);
        }
    }




}