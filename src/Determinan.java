public class Determinan {
    public static void calculateDetBySarrus(Matrix m) {
        double result = OperasiMatrix.returnDetBySarrus(m);
        System.out.println("Determinan dari matrix");
        m.TulisMatrix();
        System.out.println("adalah " + result);

        IO.tekanEnterUntukKembali();
    }
    public static void calculateDetByCofactorExpansion(Matrix matrix) {
        if (matrix.rows != matrix.cols) {
            throw new IllegalArgumentException("Determinant hanya dapat dihitung untuk matriks persegi (NxN)");
        }
        System.out.println("Nilai matriks yang telah dimasukkan:");
        matrix.TulisMatrix();
        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);
        System.out.println("Determinan dari matriks adalah: " + determinant);
    }
}