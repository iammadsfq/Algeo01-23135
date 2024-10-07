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
                    System.out.println("Anda memilih: Sistem Persamaan Linier");
                    delay(1000);
                    System.out.println("PILIH METODE SPL");
                    System.out.println("1. Metode eliminasi Gauss");
                    System.out.println("2. Metode eliminasi Gauss-Jordan");
                    System.out.println("3. Metode matriks balikan");
                    System.out.println("4. Kaidah Cramer");
                    System.out.println("5. Kembali");
                    System.out.print("Pilih menu (1-5): ");
                    int methodChoice = 0;

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
                            System.out.println("Memilih Metode Eliminasi Gauss.");
                            // solveByGaussElimination();
                            break;
                        case 2:
                            System.out.println("Memilih Metode Eliminasi Gauss-Jordan.");
                            // solveByGaussJordanElimination();
                            break;
                        case 3:
                            System.out.println("Memilih Metode Matriks Balikan.");
                            // solveByInverseMatrix();
                            break;
                        case 4:
                            System.out.println("Memilih Kaidah Cramer.");
                            // solveByCramerRule();
                            break;
                        case 5:
                            System.out.println("Kembali ke menu utama.");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("Anda memilih: Determinan");
                    delay(1000);
                    // Panggil fungsi untuk menghitung determinan
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

    public static void delay(int ms) {
        try {
            Thread.sleep(ms); // Pause for specified milliseconds
        } catch (InterruptedException e) {
            System.out.println("Thread terganggu.");
        }
    }
}
