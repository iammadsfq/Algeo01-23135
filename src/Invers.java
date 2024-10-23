public class Invers {
    // Fungsi untuk mendapatkan invers matriks menggunakan metode adjoin
    public static void getInversByAdjoint(Matrix matrix) {
        int n = matrix.rows;

        // Memeriksa apakah matriks adalah matriks bersifat persegi
        if (matrix.rows != matrix.cols) {
            // Kembalikan null atau hanya keluar dari metode jika matriks tidak persegi
            System.out.println("Invers hanya dapat dihitung untuk matriks persegi (NxN)");
            return; // atau bisa juga menggunakan break untuk keluar dari metode
        }

        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);
        if (determinant == 0) { // Jika determinan 0, matriks tidak memiliki invers
            System.out.println("Matriks tidak memiliki invers karena determinan adalah 0.");
            return; // Kembali ke main tanpa melakukan lebih lanjut
        }

        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();

        Matrix inversMatrix = OperasiMatrix.returnInversByAdjoint(matrix);
        if (inversMatrix == null) {
            System.out.println("Matrix tidak memiliki invers");
        }
        else {
            System.out.println("Invers dari matriks dengan metode adjoin adalah:");
            inversMatrix.TulisMatrix();
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
    }



}
