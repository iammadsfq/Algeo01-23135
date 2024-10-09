import java.util.Scanner;

public class IO {
    public static void tekanEnterUntukKembali() {
        System.out.println("\nTekan Enter untuk kembali ke menu utama...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}