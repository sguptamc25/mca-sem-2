import java.util.Scanner;

public class PatternPrinter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Display the menu exactly as requested
        System.out.println("Select Pattern Type:");
        System.out.println("1. Right Triangle");
        System.out.println("2. Inverted Triangle");
        System.out.println("3. Pyramid");
        System.out.println("4. Diamond");

        System.out.print("\nEnter pattern type: ");
        int type = sc.nextInt();

        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();

        System.out.println("\n--- Result ---");

        // Execute the chosen pattern
        switch (type) {
            case 1:
                printRightTriangle(rows);
                break;
            case 2:
                printInvertedTriangle(rows);
                break;
            case 3:
                printPyramid(rows);
                break;
            case 4:
                printDiamond(rows);
                break;
            default:
                System.out.println("Invalid pattern type selected. Please choose between 1 and 4.");
        }

        sc.close();
    }

    // 1. Right Triangle
    private static void printRightTriangle(int rows) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    // 2. Inverted Triangle
    private static void printInvertedTriangle(int rows) {
        for (int i = rows; i >= 1; i--) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    // 3. Pyramid
    private static void printPyramid(int rows) {
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = i; j < rows; j++) {
                System.out.print(" ");
            }
            // Print stars with spaces
            for (int k = 1; k <= i; k++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    // 4. Diamond
    private static void printDiamond(int rows) {
        // Top half (Pyramid)
        for (int i = 1; i <= rows; i++) {
            for (int j = i; j < rows; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= i; k++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // Bottom half (Inverted Pyramid)
        // Starts at rows - 1 so the middle row isn't duplicated
        for (int i = rows - 1; i >= 1; i--) {
            for (int j = rows; j > i; j--) {
                System.out.print(" ");
            }
            for (int k = 1; k <= i; k++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}