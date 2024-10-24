import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
        double a = 0;
        double b = 0;

        // Input a
        while (true) {
            System.out.print("Masukkan nilai a (0 <= a <= 1): ");
            a = sc.nextDouble();
            if (a >= 0 && a <= 1) {
                break; // Nilai a valid
            } else {
                System.out.println("Nilai a tidak valid. Silakan masukkan lagi.");
            }
        }

        // Input b
        while (true) {
            System.out.print("Masukkan nilai b (0 < b < 1): ");
            b = sc.nextDouble();
            if (b >= 0 && b <= 1) {
                break; // Nilai b valid
            } else {
                System.out.println("Nilai b tidak valid. Silakan masukkan lagi.");
            }
        }
        // Baca a dan b END

        // Lakukan interpolasi
        double result = bicubicSplineInterpolation(matrix, a, b);

        // Tanyakan apakah ingin menyimpan hasil
        System.out.print("Apakah Anda ingin menyimpan hasil? (ya/tidak): ");
        String saveResponse = sc.next();

        if (saveResponse.equalsIgnoreCase("ya")) {
            System.out.print("Masukkan nama file untuk menyimpan hasil: ");
            String fileName = sc.next();
            saveOutputToFile(fileName, result, a, b);
        }
    }

    public static double bicubicSplineInterpolation(Matrix input, double a, double b) {
        // bikin vektor_y
        Matrix vektor_y = new Matrix(16, 1);
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                vektor_y.contents[4 * i + j][0] = input.contents[i][j];
            }
        }
        // vektor_y END

        // bikin matrix_x(x,y)
        Matrix matrix_x = new Matrix(16, 16);
        int power_x, power_y;
        int value_x, value_y;

        for (i = 0; i < 4; i++) {
            value_x = i % 2;
            value_y = i / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaHasil(value_x, value_y, power_x, power_y);
            }
        } // nilai di (x,y)
        for (i = 4; i < 8; i++) {
            value_x = (i - 4) % 2;
            value_y = (i - 4) / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaTurunanX(value_x, value_y, power_x, power_y);
            }
        } // turunan thd x di (x,y)
        for (i = 8; i < 12; i++) {
            value_x = (i - 8) % 2;
            value_y = (i - 8) / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaTurunanY(value_x, value_y, power_x, power_y);
            }
        } // turunan thd y di (x,y)
        for (i = 12; i < 16; i++) {
            value_x = (i - 12) % 2;
            value_y = (i - 12) / 2;
            for (j = 0; j < 16; j++) {
                power_x = j % 4;
                power_y = j / 4;
                matrix_x.contents[i][j] = ekspansiSigmaTurunanSilang(value_x, value_y, power_x, power_y);
            }
        } // turunan silang di (x,y)
        // matrix_x END

        // bikin invers_x
        Matrix invers_x = OperasiMatrix.returnInversByGaussJordan(matrix_x, 16);
        // vektor_a = kalikanMatrix(invers_x, vektor_y)
        Matrix vektor_a = SPL.kalikanMatriks(invers_x, vektor_y);
        double result = returnBicubicInterpolation(vektor_a, a, b);

        // Output hasil
        System.out.print("f(" + a + "," + b + ") = ");
        System.out.println(result);
        Main.delay(1000);

        return result; // Return the result for file saving
    }

    public static void bacaFileBicubicSpline(String fileName) {
        try {
            File file = new File("test/" + fileName);
            Scanner sc = new Scanner(file);

            // Initialize a 4x4 matrix
            Matrix matrix = new Matrix(4, 4);

            // Read the 4x4 matrix
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (sc.hasNextDouble()) {
                        double value = sc.nextDouble();
                        matrix.setElement(i, j, value);
                    } else {
                        System.out.println("Error: Not enough elements in the file for a 4x4 matrix.");
                        return;
                    }
                }
            }

            // Read values for a and b (the coordinates for interpolation)
            double a = 0;
            double b = 0;

            // Read a
            if (sc.hasNextDouble()) {
                a = sc.nextDouble();
            } else {
                System.out.println("Error: Missing value for a in the file.");
                return;
            }

            // Read b
            if (sc.hasNextDouble()) {
                b = sc.nextDouble();
            } else {
                System.out.println("Error: Missing value for b in the file.");
                return;
            }

            // Validate a and b
            if (a < 0 || a > 1 || b < 0 || b > 1) {
                System.out.println("Nilai a dan b tidak valid. Harus 0 <= a <= 1 dan 0 <= b <= 1.");
                return;
            }

            // Call the bicubic spline interpolation method
            double result = bicubicSplineInterpolation(matrix, a, b);

            // Tanyakan apakah ingin menyimpan hasil
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Apakah Anda ingin menyimpan hasil? (ya/tidak): ");
            String saveResponse = inputScanner.next();

            if (saveResponse.equalsIgnoreCase("ya")) {
                System.out.print("Masukkan nama file untuk menyimpan hasil: ");
                String fileNameToSave = inputScanner.next();
                saveOutputToFile(fileNameToSave, result, a, b);
            }
            inputScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + fileName);
        }
    }

    public static double returnBicubicInterpolation(Matrix vektor_a, double x, double y) {
        double result = 0;
        int i, j;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                result += ekspansiSigmaHasil(x, y, i, j) * vektor_a.contents[i + 4 * j][0];
            }
        }
        return result;
    }

    public static double ekspansiSigmaHasil(double x, double y, int i, int j) {
        return Math.pow(x, i) * Math.pow(y, j);
    }

    public static double ekspansiSigmaTurunanX(double x, double y, int i, int j) {
        if (i == 0) {
            return 0;
        }
        return i * Math.pow(x, i - 1) * Math.pow(y, j);
    }

    public static double ekspansiSigmaTurunanY(double x, double y, int i, int j) {
        if (j == 0) {
            return 0;
        }
        return j * Math.pow(x, i) * Math.pow(y, j - 1);
    }

    public static double ekspansiSigmaTurunanSilang(double x, double y, int i, int j) {
        if (i == 0 || j == 0) {
            return 0;
        }
        return i * j * Math.pow(x, i - 1) * Math.pow(y, j - 1);
    }

    private static void saveOutputToFile(String fileName, double result, double a, double b) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.printf("Hasil interpolasi bicubic f(%.2f, %.2f) = %.6f%n", a, b, result);
            System.out.println("Hasil berhasil disimpan ke " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Gagal menyimpan ke file: " + fileName);
        }
    }
}
