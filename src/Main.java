import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            // Display the main menu
            System.out.println("MENU");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Linier dan Kuadratik Berganda");
            System.out.println("7. Keluar");
            System.out.print("Pilih menu (1-7): ");

            try {
                choice = scanner.nextInt();  // Input pilihan menu
            } catch (java.util.InputMismatchException e) {
                System.out.println("Input tidak valid! Silakan masukkan angka.");
                delay(1000);
                scanner.next(); // Clear the invalid input
                continue; // Skip to the next iteration of the loop
            }

            // Process based on choice
            switch (choice) {
                case 1:
                    System.out.println("Anda memilih: Sistem Persamaan Linier\n");
                    delay(1000);
                    System.out.println("PILIH METODE SPL");
                    System.out.println("1. Metode eliminasi Gauss");
                    System.out.println("2. Metode eliminasi Gauss-Jordan");
                    System.out.println("3. Metode matriks balikan");
                    System.out.println("4. Kaidah Cramer");
                    System.out.println("5. Kembali");
                    System.out.print("Pilih menu (1-5): ");
                    {
                        int methodChoice;

                        try {
                            methodChoice = scanner.nextInt();  // Input pilihan metode SPL
                        } catch (java.util.InputMismatchException er) {
                            System.out.println("Input tidak valid! Silakan masukkan angka.");
                            delay(1000);
                            scanner.next(); // Clear the invalid input
                            continue; // Skip to the next iteration of the loop
                        }

                        while (methodChoice < 1 || methodChoice > 5) {
                            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            System.out.print("Pilih menu (1-5): ");
                            methodChoice = scanner.nextInt();
                        }

                        switch (methodChoice) {
                            case 1:
                                solveByGaussElimination();
                                break;
                            case 2:
                                System.out.println("Memilih Metode Eliminasi Gauss-Jordan.\n");
                                // solveByGaussJordanElimination();
                                break;
                            case 3:
                                System.out.println("Memilih Metode Matriks Balikan.\n");
                                // solveByInverseMatrix();
                                break;
                            case 4:
                                System.out.println("Memilih Kaidah Cramer.\n");
                                // solveByCramerRule();
                                break;
                            case 5:
                                System.out.println("Kembali ke menu utama.\n");
                                break;
                        }
                    }
                    break;

                case 2:
                    System.out.println("Anda memilih: Determinan\n");
                    delay(1000);
                    System.out.println("PILIH METODE DETERMINAN");
                    System.out.println("1. Metode Ekspansi Kofaktor");
                    System.out.println("2. Metode Reduksi Baris");
                    System.out.println("3. Metode Sarrus");
                    System.out.println("4. Kembali");
                    System.out.print("Pilih menu (1-4): ");

                    { //supaya gak bentrok dgn case lain, ini di kurung kurawal
                        int methodChoice;

                        try {
                            methodChoice = scanner.nextInt();  // Input pilihan metode SPL
                        } catch (java.util.InputMismatchException er) {
                            System.out.println("Input tidak valid! Silakan masukkan angka.");
                            delay(1000);
                            scanner.next(); // Clear the invalid input
                            continue; // Skip to the next iteration of the loop
                        }

                        while (methodChoice < 1 || methodChoice > 4) {
                            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            System.out.print("Pilih menu (1-4): ");
                            methodChoice = scanner.nextInt();
                        }

                        switch (methodChoice) {
                            case 1:
                                System.out.println("Memilih Metode Ekspansi Kofaktor.");
                                // determinantByCofactorExpansion();
                                break;
                            case 2:
                                System.out.println("Memilih Metode Reduksi Baris");
                                // determinantByRowReduction();
                                break;
                            case 3:
                                System.out.println("Memilih Metode Sarrus");
                                // determinantBySarrus();
                                break;
                            case 4:
                                System.out.println("Kembali ke menu utama.");
                                break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Anda memilih: Matriks Balikan");
                    delay(1000);
                    break;
                case 4:
                    System.out.println("Anda memilih: Interpolasi Polinom");
                    delay(1000);
                    break;
                case 5:
                    System.out.println("Anda memilih: Interpolasi Bicubic Spline");
                    delay(1000);
                    break;
                case 6:
                    System.out.println("Anda memilih: Regresi Linier dan Kuadratik Berganda");
                    delay(1000);
                    break;
                case 7:
                    System.out.println("Keluar dari program.");
                    delay(1000);
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silakan pilih lagi.");
                    delay(1000);
            }

            System.out.println();
        } while (choice != 7);

        scanner.close(); // Close the scanner to prevent resource leaks
    }


    //START OF SPL
    public static void solveByGaussElimination() { //dari main
        System.out.println("Memilih Metode Eliminasi Gauss.");
        delay(1000);
        // minta pilih metode input
        System.out.println("PILIH METODE INPUT");
        System.out.println("1. Keyboard Input");
        System.out.println("2. File Input");
        Scanner sc = new Scanner(System.in);
        int inputChoice = -1;
        Matrix matrix = null;

        while (true) {
            try {
                System.out.print("Masukkan pilihan Anda (1/2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        // Read matrix from keyboard
                        matrix = Matrix.readMatrixFromKeyboard();
                        break;

                    case 2:
                        // Read matrix from file
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Exit loop if valid input method is selected
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed with Gaussian Elimination
        if (matrix != null) {
            // solveGaussianElimination(matrix); // NAH BIKIN YG INI
            matrix.TulisMatrix();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void solveByGaussJordanElimination() {
        System.out.println("Memilih Metode Eliminasi Gauss Jordan.");
        delay(1000);
        // minta pilih metode input
        System.out.println("PILIH METODE INPUT");
        System.out.println("1. Keyboard Input");
        System.out.println("2. File Input");
        Scanner sc = new Scanner(System.in);
        int inputChoice = -1;
        Matrix matrix = null;

        while (true) {
            try {
                System.out.print("Masukkan pilihan Anda (1/2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        // Read matrix from keyboard
                        matrix = Matrix.readMatrixFromKeyboard();
                        break;

                    case 2:
                        // Read matrix from file
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Exit loop if valid input method is selected
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed with Gaussian Elimination
        if (matrix != null) {
            // solveGaussianJordanElimination(matrix); // NAH BIKIN YG INI
            matrix.TulisMatrix();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void solveByInverseMatrix() {

    }
    public static void solveByCramerRule() {

    }
    //END OF SPL

    //START OF DETERMINAN
    public static void determinantByCofactorExpansion() {

    }
    public static void determinantByRowReduction() {

    }
    public static void determinantBySarrus() {

    }
    //END OF DETERMINAN

    //START OF INVERS
    //END OF INVERS

    public static void delay(int ms) {
        try {
            Thread.sleep(ms); // Pause for specified milliseconds
        } catch (InterruptedException e) {
            System.out.println("Thread terganggu.");
        }
    }
}
