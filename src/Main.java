import java.io.FileNotFoundException;
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
                                solveByGaussJordanElimination();
                                break;
                            case 3:
                                solveByInverseMatrix();
                                break;
                            case 4:
                                solveByCramerRule();
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
                                determinantByCofactorExpansion();
                                break;
                            case 2:
                                determinantByRowReduction();
                                break;
                            case 3:
                                determinantBySarrus();
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
                    System.out.println("PILIH METODE INVERS");
                    System.out.println("1. Metode Gauss Jordan [A|I]");
                    System.out.println("2. Metode Adjoin");
                    System.out.println("3. Kembali");
                    System.out.print("Pilih menu (1-3): ");
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

                    while (methodChoice < 1 || methodChoice > 3) {
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        System.out.print("Pilih menu (1-3): ");
                        methodChoice = scanner.nextInt();
                    }

                    switch (methodChoice) {
                        case 1:
                            inversByRowReduction();
                            break;
                        case 2:
                            inversByAdjoint();
                            break;
                        case 3:
                            System.out.println("Kembali ke menu utama.");
                            break;
                    }
                }
                    break;
                case 4:
                    System.out.println("Anda memilih: Interpolasi Polinom");
                    delay(1000);
                    startInterpolasiPolinomial();
                    break;
                case 5:
                    System.out.println("Anda memilih: Interpolasi Bicubic Spline");
                    delay(1000);
                    startBicubicSpline();
                    break;
                case 6:
                    System.out.println("Anda memilih: Regresi Berganda");
                    delay(1000);
                    System.out.println("PILIH REGRESI");
                    System.out.println("1. Regresi Linear Berganda");
                    System.out.println("2. Regresi Kuadratik Berganda");
                    System.out.println("3. Kembali");
                    System.out.print("Pilih menu (1-3): ");
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

                    while (methodChoice < 1 || methodChoice > 3) {
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        System.out.print("Pilih menu (1-3): ");
                        methodChoice = scanner.nextInt();
                    }

                    switch (methodChoice) {
                        case 1:
                            startLinearRegression();
                            break;
                        case 2:
                            startQuadraticRegression();
                            break;
                        case 3:
                            System.out.println("Kembali ke menu utama.");
                            break;
                    }
                }
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
        System.out.println("Memilih Metode Eliminasi Gauss.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
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

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed with Gaussian Elimination
        if (matrix != null) {
            SPL.solveGaussianElimination(matrix); //Realisasi di file SPL.java
            IO.tekanEnterUntukKembali();
            // ^Memproses matriks sampai menulis solusi
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void solveByGaussJordanElimination() {
        System.out.println("Memilih Metode Eliminasi Gauss Jordan.\n");
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
             SPL.solveGaussianJordanElimination(matrix);
            IO.tekanEnterUntukKembali();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void solveByInverseMatrix() {
        System.out.println("Memilih Metode Matrix Balikan.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        System.out.println("*Penyelesaian SPL menggunakan matriks balikan hanya berfungsi untuk SPL dengan matriks koefisien simetris");
                        System.out.println("*Masukkan Augmented Matrix berdimensi n*(n+1)");
                        delay(2000);
                        matrix = Matrix.readMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.println("*Penyelesaian SPL menggunakan matriks balikan hanya berfungsi untuk SPL dengan jumlah persamaan sama dengan jumlah variabel");
                        System.out.println("*Masukkan Augmented Matrix berdimensi n*(n+1)");
                        delay(2000);
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed
        if (matrix != null) {
            if (matrix.cols == matrix.rows +1) {
                SPL.SolveInverseMatrix(matrix);
                IO.tekanEnterUntukKembali();
            } else {
                System.out.println("Dimensi " + matrix.rows + "x" + matrix.cols + " tidak valid untuk penyelesaian SPL dengan matriks balikan.");
                delay(2000);
            }

        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void solveByCramerRule() {
        System.out.println("Memilih Metode Kaidah Cramer.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        System.out.println("*Penyelesaian SPL menggunakan kaidah cramer hanya berfungsi untuk SPL dengan jumlah persamaan sama dengan jumlah variabel");
                        System.out.println("*Masukkan Augmented Matrix berdimensi n*(n+1)");
                        delay(2000);
                        matrix = Matrix.readMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.println("*Penyelesaian SPL menggunakan kaidah cramer hanya berfungsi untuk SPL dengan jumlah persamaan sama dengan jumlah variabel");
                        System.out.println("*Masukkan Augmented Matrix berdimensi n*(n+1)");
                        delay(2000);
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed
        if (matrix != null) {
            if (matrix.cols == matrix.rows +1) {
                SPL.solveCramerRule(matrix); // NAH BIKIN YG INI
                IO.tekanEnterUntukKembali();
            } else {
                System.out.println("Dimensi " + matrix.rows + "x" + matrix.cols + " tidak valid untuk penyelesaian SPL dengan kaidah cramer.");
                delay(2000);
            }

        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    //END OF SPL

    //START OF DETERMINAN
    public static void determinantByCofactorExpansion() {
        System.out.println("Memilih Metode Ekspansi Kofaktor.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        matrix = Matrix.readNxNMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readNxNMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed
        if (matrix != null) {
            Determinan.calculateDetByCofactorExpansion(matrix);
            IO.tekanEnterUntukKembali();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }

    }
    public static void determinantByRowReduction() {
        System.out.println("Memilih Metode Reduksi Baris.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        matrix = Matrix.readNxNMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readNxNMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed
        if (matrix != null) {
            Determinan.calculateDetByRowReduction(matrix);
            IO.tekanEnterUntukKembali();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void determinantBySarrus() {
        System.out.println("Memilih Metode Sarrus.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        matrix = Matrix.readNxNMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readNxNMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }
                if (matrix.rows != 3) {
                    System.out.println("Metode Sarrus hanya menerima matrix 3x3!");
                } else {
                    break;  // Keluar kalo input valid
                }

            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }
        Determinan.calculateDetBySarrus(matrix);
        IO.tekanEnterUntukKembali();
    }
    //END OF DETERMINAN

    //START OF INVERS
    public static void inversByAdjoint() {
        System.out.println("Memilih Metode Adjoint.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        matrix = Matrix.readNxNMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readNxNMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed
        if (matrix != null) {
            Invers.getInversByAdjoint(matrix);
            IO.tekanEnterUntukKembali();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    public static void inversByRowReduction() {
        System.out.println("Memilih Metode Gauss-Jordan.\n");
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
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        matrix = Matrix.readNxNMatrixFromKeyboard(); //read matrix from keyboard input
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        matrix = Matrix.readNxNMatrixFromFile(fileName);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }

                break;  // Keluar kalo input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
        }

        // Proceed
        if (matrix != null) {
            Invers.getInversByRowReduction(matrix);
            IO.tekanEnterUntukKembali();
        } else {
            System.out.println("Terjadi kesalahan dalam membaca matrix.");
        }
    }
    //END OF INVERS

    //Interpolasi Polinomial
    public static void startInterpolasiPolinomial() {
        System.out.println();
        // minta pilih metode input
        System.out.println("PILIH METODE INPUT");
        System.out.println("1. Keyboard Input");
        System.out.println("2. File Input");
        Scanner sc = new Scanner(System.in);
        int inputChoice = -1;

        while (true) {
            try {
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        InterpolasiPolinomial.bacaKeyboardInterpolasiPolinomial(); //read matrix from keyboard input
                        IO.tekanEnterUntukKembali();
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        InterpolasiPolinomial.bacaFileInterpolasiPolinomial(fileName);
                        IO.tekanEnterUntukKembali();
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
            break;
        }
    }

    //Bicubic Spline
    public static void startBicubicSpline() {
        System.out.println();
        // minta pilih metode input
        System.out.println("PILIH METODE INPUT");
        System.out.println("1. Keyboard Input");
        System.out.println("2. File Input");
        Scanner sc = new Scanner(System.in);
        int inputChoice = -1;

        while (true) {
            try {
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        BicubicInterpolation.bacaKeyboardBicubicInterpolation(); //read matrix from keyboard input
                        IO.tekanEnterUntukKembali();
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        BicubicInterpolation.bacaFileBicubicSpline(fileName);
                        IO.tekanEnterUntukKembali();
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
            break;
        }
    }

    //Regresi Berganda
    public static void startLinearRegression() {
        System.out.println();
        // minta pilih metode input
        System.out.println("PILIH METODE INPUT");
        System.out.println("1. Keyboard Input");
        System.out.println("2. File Input");
        Scanner sc = new Scanner(System.in);
        int inputChoice = -1;

        while (true) {
            try {
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        // Proses input jumlah peubah (n) dan sampel (m)
                        System.out.print("Masukkan jumlah peubah (n): ");
                        int n = sc.nextInt();
                        System.out.print("Masukkan jumlah sampel (m): ");
                        int m = sc.nextInt();
                        RegresiBerganda.regresiLinierBerganda(n, m, sc);
                        IO.tekanEnterUntukKembali();
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        RegresiBerganda.bacaFileRegresiLinear(fileName);
                        IO.tekanEnterUntukKembali();
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            } catch (FileNotFoundException e) {
                System.out.println("File tidak ditemukan");
            }
            break;
        }
    }
    public static void startQuadraticRegression() {
        System.out.println();
        // minta pilih metode input
        System.out.println("PILIH METODE INPUT");
        System.out.println("1. Keyboard Input");
        System.out.println("2. File Input");
        Scanner sc = new Scanner(System.in);
        int inputChoice = -1;

        while (true) {
            try {
                System.out.print("Masukkan pilihan Anda (1-2): ");
                inputChoice = sc.nextInt();

                switch (inputChoice) {
                    case 1:
                        // Proses input jumlah peubah (n) dan sampel (m)
                        System.out.print("Masukkan jumlah peubah (n): ");
                        int n = sc.nextInt();
                        System.out.print("Masukkan jumlah sampel (m): ");
                        int m = sc.nextInt();
                        RegresiBerganda.regresiKuadratikBerganda(n, m, sc);
                        IO.tekanEnterUntukKembali();
                        break;

                    case 2:
                        System.out.print("Masukkan nama file (contoh: matrix.txt): ");
                        String fileName = sc.next();  // Capture file name input
                        RegresiBerganda.bacaFileRegresiKuadratikBerganda(fileName);
                        IO.tekanEnterUntukKembali();
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                        continue; // Input lagi
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka 1 atau 2.");
                sc.next();  // Clear the invalid input from the scanner buffer
            }
            break;
        }
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms); // Pause for specified milliseconds
        } catch (InterruptedException e) {
            System.out.println("Thread terganggu.");
        }
    }
}
