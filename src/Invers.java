public class Invers {
    // Fungsi untuk mendapatkan invers matriks menggunakan metode adjoin
    public static void getInversByAdjoint(Matrix matrix) {
        int n = matrix.rows;
        // Memeriksa apakah matriks adalah matriks bersifat persegi
        if (matrix.rows != matrix.cols) {
            throw new IllegalArgumentException("Invers hanya dapat dihitung untuk matriks persegi (NxN)");
        }
        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);
        if (determinant == 0) { // Jika determinan 0, matriks tidak memiliki invers
            throw new ArithmeticException("Matriks tidak memiliki invers karena determinan adalah 0.");
        }
        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();

        Matrix inversMatrix = OperasiMatrix.returnInversByAdjoint(matrix);

        System.out.println("Invers dari matriks dengan metode adjoin adalah:");
        inversMatrix.TulisMatrix();
    }

    // Fungsi Matriks untuk adjoint

    public static void main(String[] args) {
        Matrix matrix = Matrix.readNxNMatrixFromKeyboard();


    }
    public static void getInversByAdjoint(Matrix matrix){
        int n = matrix.rows;
        // Memeriksa apakah matriks adalah matriks bersifat persegi
        if (matrix.rows != matrix.cols) {
            throw new IllegalArgumentException("Invers hanya dapat dihitung untuk matriks persegi (NxN)");
        }
        double determinant = OperasiMatrix.returnDetbyRowReduction(matrix, matrix.rows);
        if (determinant == 0) { // Jika determinan 0, matriks tidak memiliki invers
            throw new ArithmeticException("Matriks tidak memiliki invers karena determinan adalah 0.");
        }
        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();

        Matrix inversMatrix = OperasiMatrix.returnInversByGaussJordan(matrix, n);

        System.out.println("Invers dari matriks dengan metode Gauss-Jordan adalah:");
        inversMatrix.TulisMatrix();

    }
}
