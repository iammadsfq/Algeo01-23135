public class Determinan {
    public static void calculateDetBySarrus(Matrix m) {
        double result = OperasiMatrix.returnDetBySarrus(m);
        System.out.println("Determinan dari matrix");
        m.TulisMatrix();
        System.out.println("adalah " + result);

        IO.tekanEnterUntukKembali();
    }
}