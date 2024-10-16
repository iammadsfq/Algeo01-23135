import java.util.Scanner;

public class IO {
    public static void tekanEnterUntukKembali() {
        System.out.println("\nTekan Enter untuk kembali ke menu utama...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    public static void tulisSolusiSPL(String[] solutions) //Untuk Penyelesaian SPL
    {
        System.out.print("x" + 1 + " = " + solutions[0]);
        for (int i = 1; i < solutions.length; i++) {
            if (solutions[i] != null) {
                System.out.print(", x" + (i+1) + " = " + solutions[i]);
            }
        }
    }


}