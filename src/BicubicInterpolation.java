import java.util.Scanner;

public class BicubicInterpolation {
    public static void bacaKeyboardBicubicInterpolation() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bicubic Spline Interpolation menerima matriks 4x4\n");
        Main.delay(1000);
        System.out.println("Baris pertama adalah f(0,0), f(1,0), f(0,1), f(1,1)\n");
        Main.delay(1000);
        System.out.println("Baris kedua adalah nilai turunan terhadap x pada titik tadi\n");
        Main.delay(1000);
        System.out.println("Baris ketiga adalah nilai turunan terhadap y pada titik tadi\n");
        Main.delay(1000);
        System.out.println("Baris keempat adalah nilai turunan silang pada titik tadi\n");

        // Baca Matrix 4x4
        Matrix matrix = new Matrix(4, 4);
        System.out.println("Masukkan elemen matriks per baris");
        for (int i = 0; i < 4; i++) {
            System.out.print("Baris ke-" + (i+1) + ":");
            for (int j = 0; j < 4; j++) {
                double value = sc.nextDouble();
                matrix.setElement(i, j, value);
            }
        }
        // Baca Matrix 4x4 END

        // Baca a dan b, (a,b) adalah titik yang nilainya akan diinterpolasikan
        // valid jika 0 < a < 1 dan 0 < b < 1. Jika a atau b tidak valid, ulangi pembacaan a atau b
        double a = 0;
        double b = 0;
        // Input a
        while (true) {
            System.out.print("Masukkan nilai a (0 < a < 1): ");
            a = sc.nextDouble();
            if (a > 0 && a < 1) {
                break; // Nilai a valid
            } else {
                System.out.println("Nilai a tidak valid. Silakan masukkan lagi.");
            }
        }

        // Input b
        while (true) {
            System.out.print("Masukkan nilai b (0 < b < 1): ");
            b = sc.nextDouble();
            if (b > 0 && b < 1) {
                break; // Nilai b valid
            } else {
                System.out.println("Nilai b tidak valid. Silakan masukkan lagi.");
            }
        }
        //Baca a dan b END
        bicubicSplineInterpolation(matrix, a, b);
        sc.close();
    }
    public static void bicubicSplineInterpolation(Matrix input, double a, double b) {
        //bikin vektor_y
        Matrix vektor_y = new Matrix(16,1);
        int i,j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                vektor_y.contents[4*i+j][0] = input.contents[i][j];
            }
        }
        vektor_y.TulisMatrix();
        //vektor_y END

        //vektor_a = 00, 10, 20, 30, 01, 11, 21, 31, 02, 12, 22, 32, 03, 13 23 33
        //bikin matrix_x(x,y)
        Matrix matrix_x = new Matrix(16,16);
        int power_x, power_y;
        int value_x, value_y;
        for (i = 0; i < 4; i++) {
            value_x = i % 2;
            value_y = i / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaHasil(value_x,value_y,power_x,power_y);
            }
        } //nilai di (x,y)
        for (i = 4; i < 8; i++) {
            value_x = (i-4) % 2;
            value_y = (i-4) / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaTurunanX(value_x,value_y,power_x,power_y);
            }
        } //turunan thd x di (x,y)
        for (i = 8; i < 12; i++) {
            value_x = (i-8) % 2;
            value_y = (i-8) / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaTurunanY(value_x,value_y,power_x,power_y);
            }
        } //turunan thd y di (x,y)
        for (i = 12; i < 16; i++) {
            value_x = (i-12) % 2;
            value_y = (i-12) / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaTurunanSilang(value_x,value_y,power_x,power_y);
            }
        } //turunan silang di (x,y)
        matrix_x.TulisMatrix();
        System.out.println("\n");
        //matrix_x END

        //bikin invers_x
        Matrix invers_x = OperasiMatrix.returnInversByAdjoint(matrix_x);
        invers_x.TulisMatrix();
        System.out.println("\n");
        //vektor_a = kalikanMatrix(invers_x,vektor_y)
        Matrix vektor_a = SPL.kalikanMatriks(invers_x, vektor_y);
        vektor_a.TulisMatrix();
        System.out.println("\n");
        double result = returnBicubicInterpolation(vektor_a, a, b);
        //result = returnBicubicInterpolation(vektor_a, a, b)
        System.out.println(result);
    }
    public static void bacaFileBicubicInterpolation() {

    }
    public static double returnBicubicInterpolation(Matrix vektor_a, double x, double y) {
        double result = 0;
        int i,j;
        for (j = 0; j < 3; j++) {
            for (i = 0; i < 3; i++) {
                result += ekspansiSigmaHasil(x,y,i,j)*vektor_a.contents[i+4*j][0];
            }
        }
        return result;
    }
    public static double ekspansiSigmaHasil(double x, double y, int i, int j) {
        return Math.pow(x,i) * Math.pow(y,j);
    }
    public static double ekspansiSigmaTurunanX(double x, double y, int i, int j) {
        if (i == 0 ) {
            return 0;
        }
        return i * Math.pow(x,i-1) * Math.pow(y,j);
    }
    public static double ekspansiSigmaTurunanY(double x, double y, int i, int j) {
        if (j == 0) {
            return 0;
        }
        return j * Math.pow(x,i) * Math.pow(y,j-1);
    }
    public static double ekspansiSigmaTurunanSilang(double x, double y, int i, int j) {
        if (i == 0 || j == 0) {
            return 0;
        }
        return i*j*Math.pow(x,i-1)*Math.pow(y,j-1);
    }
}
