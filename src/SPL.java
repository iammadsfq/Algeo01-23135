public class SPL {
    public static void solveCramerRule(Matrix m) {
        Matrix matrix_a = OperasiMatrix.getMatrixKoefisien(m);
        Matrix vektor_b = OperasiMatrix.getVektorKonstanta(m);
        //asumsi matrix persegi
        double determinan_a = OperasiMatrix.returnDetBySarrus(matrix_a);

        //bikin array solusi
        String[] solutions = new String[m.cols];
        //iterasi, masukin hasil ke solutions
        int i;
        Matrix matrix_i;
        for (i = 0; i < matrix_a.cols; i++) {
            matrix_i = OperasiMatrix.replaceColWithVector(matrix_a, vektor_b, i);
            double determinan_i = OperasiMatrix.returnDetBySarrus(matrix_i);
            solutions[i] = String.valueOf(determinan_i/determinan_a);
        }
        IO.tulisSolusiSPL(solutions);
        IO.tekanEnterUntukKembali();
    }
}