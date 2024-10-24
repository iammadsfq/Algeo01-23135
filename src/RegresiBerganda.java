import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegresiBerganda {

    public static void saveOutputToFile(String filename, String content) {
        try (PrintWriter writer = new PrintWriter(new File("output/" + filename))) {
            writer.println(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static boolean askToSaveOutput(Scanner sc) {
        System.out.print("Apakah Anda ingin menyimpan output? (y/n): ");
        String response = sc.next().trim().toLowerCase();
        return response.equals("y");
    }

    public static String getFilename(Scanner sc) {
        System.out.print("Masukkan nama file untuk menyimpan output (misal: output.txt): ");
        return sc.next().trim();
    }

    public static void regresiKuadratikBerganda(int n, int m, Scanner sc) {
        int totalColumns = 1 + n + n + (n * (n - 1)) / 2;

        Matrix X = new Matrix(m, totalColumns);
        Matrix y = new Matrix(m, 1);

        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 0; i < m; i++) {
            X.contents[i][0] = 1;
            System.out.println("Masukkan nilai untuk sampel ke-" + (i + 1) + ":");
            double[] xi = new double[n];
            for (int j = 0; j < n; j++) {
                System.out.print("x" + (j + 1) + ": ");
                xi[j] = sc.nextDouble();
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

            System.out.print("y" + (i + 1) + ": ");
            y.contents[i][0] = sc.nextDouble();
        }

        double[] variable = new double[n];
        System.out.println("Masukkan nilai untuk menaksir y:");
        for (int i = 0; i < n; i++) {
            System.out.print("x" + (i + 1) + ": ");
            variable[i] = sc.nextDouble();
        }

        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        for (int i = 0; i < solusi.length; i++) {
            outputBuilder.append(String.format("Koefisien Regresi β%d = %s\n", i, solusi[i]));
        }

        double[] solusiDouble = new double[solusi.length];
        for (int i = 0; i < solusi.length; i++) {
            solusiDouble[i] = Double.parseDouble(solusi[i]);
        }

        double Taksiran = solusiDouble[0];
        for (int i = 1; i <= n; i++) {
            Taksiran += solusiDouble[i] * variable[i - 1];
        }

        outputBuilder.append(String.format("Hampiran (taksiran) nilai f(x): %f\n", Taksiran));

        // Ask user if they want to save output
        if (askToSaveOutput(sc)) {
            String filename = getFilename(sc);
            saveOutputToFile(filename, outputBuilder.toString());
        }
    }

    public static void bacaFileRegresiKuadratik(String filename, Scanner sc) {
        try {
            File file = new File("test/" + filename);
            Scanner fileScanner = new Scanner(file);
            int m = 0;
            int n = -1;
            List<double[]> data = new ArrayList<>();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] values = line.split("\\s+");
                    double[] row = new double[values.length];
                    for (int i = 0; i < values.length; i++) {
                        row[i] = Double.parseDouble(values[i]);
                    }
                    data.add(row);
                    m++;
                    if (n == -1) {
                        n = values.length - 1;
                    }
                }
            }

            double[] variableTaksiran = new double[n];
            for (int i = 0; i < n; i++) {
                variableTaksiran[i] = data.get(m - 1)[i];
            }

            m--;
            int totalColumns = 1 + n + n + (n * (n - 1)) / 2;
            Matrix X = new Matrix(m, totalColumns);
            Matrix y = new Matrix(m, 1);

            for (int i = 0; i < m; i++) {
                X.contents[i][0] = 1;
                double[] xi = new double[n];
                for (int j = 0; j < n; j++) {
                    xi[j] = data.get(i)[j];
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

                y.contents[i][0] = data.get(i)[n];
            }

            fileScanner.close();
            StringBuilder outputBuilder = new StringBuilder();

            String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

            for (int i = 0; i < solusi.length; i++) {
                outputBuilder.append(String.format("Koefisien Regresi β%d = %s\n", i, solusi[i]));
            }

            double[] solusiDouble = new double[solusi.length];
            for (int i = 0; i < solusi.length; i++) {
                solusiDouble[i] = Double.parseDouble(solusi[i]);
            }

            double taksiran = solusiDouble[0];
            int index = 1;

            for (int i = 0; i < n; i++) {
                taksiran += solusiDouble[index] * variableTaksiran[i];
                index++;
            }

            for (int i = 0; i < n; i++) {
                taksiran += solusiDouble[index] * variableTaksiran[i] * variableTaksiran[i];
                index++;
            }

            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    taksiran += solusiDouble[index] * variableTaksiran[i] * variableTaksiran[j];
                    index++;
                }
            }

            outputBuilder.append(String.format("Hampiran (taksiran) nilai f(x): %f\n", taksiran));

            // Ask user if they want to save output
            if (askToSaveOutput(sc)) {
                String filenameToSave = getFilename(sc);
                saveOutputToFile(filenameToSave, outputBuilder.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + filename);
        }
    }

    public static String[] multipleRegressionSolution(Matrix X, Matrix y, int m, int totalColumns) {
        Matrix XT = OperasiMatrix.transpose(X);
        Matrix XTX = SPL.kalikanMatriks(XT, X);
        Matrix XTy = SPL.kalikanMatriks(XT, y);
        Matrix augmentedMatrix = OperasiMatrix.toAugmented(XTX, XTy);
        return OperasiMatrix.SolveSPLGaussJordan(OperasiMatrix.ReductionREF(augmentedMatrix));
    }

    public static void regresiLinierBerganda(int n, int m, Scanner sc) {
        int totalColumns = 1 + n;

        Matrix X = new Matrix(m, totalColumns);
        Matrix y = new Matrix(m, 1);

        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 0; i < m; i++) {
            X.contents[i][0] = 1;
            System.out.println("Masukkan nilai untuk sampel ke-" + (i + 1) + ":");
            for (int j = 0; j < n; j++) {
                System.out.print("x" + (j + 1) + ": ");
                X.contents[i][j + 1] = sc.nextDouble();
            }
            System.out.print("y" + (i + 1) + ": ");
            y.contents[i][0] = sc.nextDouble();
        }

        double[] variable = new double[n];
        System.out.println("Masukkan nilai untuk menaksir y:");
        for (int i = 0; i < n; i++) {
            System.out.print("x" + (i + 1) + ": ");
            variable[i] = sc.nextDouble();
        }

        String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

        for (int i = 0; i < solusi.length; i++) {
            outputBuilder.append(String.format("Koefisien Regresi β%d = %s\n", i, solusi[i]));
        }

        double[] solusiDouble = new double[solusi.length];
        for (int i = 0; i < solusi.length; i++) {
            solusiDouble[i] = Double.parseDouble(solusi[i]);
        }

        double Taksiran = solusiDouble[0];
        for (int i = 1; i <= n; i++) {
            Taksiran += solusiDouble[i] * variable[i - 1];
        }

        outputBuilder.append(String.format("Hampiran (taksiran) nilai f(x): %f\n", Taksiran));

        // Ask user if they want to save output
        if (askToSaveOutput(sc)) {
            String filename = getFilename(sc);
            saveOutputToFile(filename, outputBuilder.toString());
        }
    }

    public static void bacaFileRegresiLinear(String filename, Scanner sc) {
        try {
            File file = new File("test/" + filename);
            Scanner fileScanner = new Scanner(file);
            int m = 0;
            int n = -1;
            List<double[]> data = new ArrayList<>();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] values = line.split("\\s+");
                    double[] row = new double[values.length];
                    for (int i = 0; i < values.length; i++) {
                        row[i] = Double.parseDouble(values[i]);
                    }
                    data.add(row);
                    m++;
                    if (n == -1) {
                        n = values.length - 1;
                    }
                }
            }

            double[] variableTaksiran = new double[n];
            for (int i = 0; i < n; i++) {
                variableTaksiran[i] = data.get(m - 1)[i];
            }

            m--;
            int totalColumns = 1 + n;
            Matrix X = new Matrix(m, totalColumns);
            Matrix y = new Matrix(m, 1);

            for (int i = 0; i < m; i++) {
                X.contents[i][0] = 1;
                for (int j = 0; j < n; j++) {
                    X.contents[i][j + 1] = data.get(i)[j];
                }
                y.contents[i][0] = data.get(i)[n];
            }

            fileScanner.close();
            StringBuilder outputBuilder = new StringBuilder();

            String[] solusi = multipleRegressionSolution(X, y, m, totalColumns - 1);

            for (int i = 0; i < solusi.length; i++) {
                outputBuilder.append(String.format("Koefisien Regresi β%d = %s\n", i, solusi[i]));
            }

            double[] solusiDouble = new double[solusi.length];
            for (int i = 0; i < solusi.length; i++) {
                solusiDouble[i] = Double.parseDouble(solusi[i]);
            }

            double taksiran = solusiDouble[0];
            for (int i = 0; i < n; i++) {
                taksiran += solusiDouble[i + 1] * variableTaksiran[i];
            }

            outputBuilder.append(String.format("Hampiran (taksiran) nilai f(x): %f\n", taksiran));

            // Ask user if they want to save output
            if (askToSaveOutput(sc)) {
                String filenameToSave = getFilename(sc);
                saveOutputToFile(filenameToSave, outputBuilder.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + filename);
        }
    }
}
