public class SPL {
    public static void solveCramerRule(Matrix m) {
        Matrix matrix_a = OperasiMatrix.getMatrixKoefisien(m);
        Matrix vektor_b = OperasiMatrix.getVektorKonstanta(m);
        //asumsi matrix persegi
        double determinan_a = OperasiMatrix.returnDetByRowReduction(matrix_a, matrix_a.rows);
        if (determinan_a == 0) {
            System.out.println("Determinan matriks adalah 0.");
            return;
        }
        //bikin array solusi
        String[] solutions = new String[m.cols];
        //iterasi, masukin hasil ke solutions
        int i;
        Matrix matrix_i;
        for (i = 0; i < matrix_a.cols; i++) {
            matrix_i = OperasiMatrix.replaceColWithVector(matrix_a, vektor_b, i);
            matrix_i.TulisMatrix();
            double determinan_i = OperasiMatrix.returnDetByRowReduction(matrix_i, matrix_i.rows);
            solutions[i] = String.valueOf(determinan_i/determinan_a);
        }
        IO.tulisSolusiSPL(solutions);
    }
    // Fungsi untuk menyelesaikan SPL menggunakan metode invers matriks
    public static void SolveInverseMatrix(Matrix matrix) {
        Matrix matrix_A = OperasiMatrix.getMatrixKoefisien(matrix);
        Matrix vektor_B = OperasiMatrix.getVektorKonstanta(matrix);
        Matrix hasil = SPLByInverseMatrix(matrix_A, vektor_B);
        if (hasil == null) {
            return;
        }
        String[] solutions = new String[hasil.rows];
        //Iterasi untuk hasil solution
        for (int i = 0; i < hasil.rows; i++) {
            solutions[i] = String.valueOf(hasil.contents[i][0]);
        }
        IO.tulisSolusiSPL(solutions);
    }

    public static Matrix SPLByInverseMatrix(Matrix matrixKoefisien, Matrix vektorKonstanta) {
        if (matrixKoefisien.rows != matrixKoefisien.cols) {
            return null;
        }
        // Invers dari matriks koefisien dihitung, lalu dikalikan dengan konstanta
        Matrix inversMatrix = OperasiMatrix.returnInversByAdjoint(matrixKoefisien);
        if (inversMatrix == null) {
            return null;
        }
        Matrix hasil = kalikanMatriks(inversMatrix, vektorKonstanta);
        if (hasil == null) {
            return null;
        }
        return hasil;
    }

    // Fungsi untuk mengalikan dua matriks
    public static Matrix kalikanMatriks(Matrix m1, Matrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("Jumlah kolom matriks pertama harus sama dengan jumlah baris matriks kedua.");
        }

        Matrix hasil = new Matrix(m1.rows, m2.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {
                hasil.contents[i][j] = 0;
                for (int k = 0; k < m1.cols; k++) {
                    hasil.contents[i][j] += m1.contents[i][k] * m2.contents[k][j];
                }
            }
        }

        return hasil;
    }

    //Fungsi menyelesaikan SPL dengan Eliminasi Gauss
    public static void solveGaussianElimination(Matrix m) {
        Matrix result = OperasiMatrix.REF(m);
        String[] Solutions = OperasiMatrix.SolveSPLGaussJordan(result);

        IO.tulisSolusiSPL(Solutions);

    }

    //Fungsi menyelesaikan SPL dengan Eliminasi Gauss-Jordan
    public static void solveGaussianJordanElimination(Matrix m){
        Matrix result = OperasiMatrix.ReductionREF(m);
        String[] Solutions = OperasiMatrix.SolveSPLGaussJordan(result);

        IO.tulisSolusiSPL(Solutions);
    }

    public static boolean isVariable(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}

