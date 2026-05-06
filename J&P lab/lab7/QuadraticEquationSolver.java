public class QuadraticEquationSolver {

    public static void main(String[] args) {
        // Test Case 1: Discriminant > 0 (Two distinct real roots)
        // Equation: x^2 - 3x + 2 = 0
        System.out.println("--- Test Case 1: x^2 - 3x + 2 = 0 ---");
        findRoot(1, -3, 2);

        // Test Case 2: Discriminant == 0 (One real root)
        // Equation: x^2 - 2x + 1 = 0
        System.out.println("\n--- Test Case 2: x^2 - 2x + 1 = 0 ---");
        findRoot(1, -2, 1);

        // Test Case 3: Discriminant < 0 (Complex/Imaginary roots)
        // Equation: x^2 + 2x + 5 = 0
        System.out.println("\n--- Test Case 3: x^2 + 2x + 5 = 0 ---");
        findRoot(1, 2, 5);

        // Test Case 4: Edge case where 'a' is 0 (Not a quadratic equation)
        System.out.println("\n--- Test Case 4: 0x^2 + 2x + 5 = 0 ---");
        findRoot(0, 2, 5);
    }

    /**
     * Finds and prints the roots of a quadratic equation (ax^2 + bx + c = 0).
     * * @param a The coefficient of x^2
     * 
     * @param b The coefficient of x
     * @param c The constant term
     */
    public static void findRoot(double a, double b, double c) {
        // A standard quadratic equation requires 'a' to be non-zero
        if (a == 0) {
            System.out.println("Error: Coefficient 'a' cannot be zero in a quadratic equation.");
            return;
        }

        // Calculate the discriminant (b^2 - 4ac)
        double discriminant = (b * b) - (4 * a * c);

        // Scenario 1: Two distinct real roots
        if (discriminant > 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            System.out.printf("Result: Two distinct real roots.\nRoot 1: %.2f\nRoot 2: %.2f\n", root1, root2);
        }
        // Scenario 2: One real root (repeated)
        else if (discriminant == 0) {
            double root = -b / (2 * a);
            System.out.printf("Result: One real root.\nRoot: %.2f\n", root);
        }
        // Scenario 3: Two complex roots
        else {
            double realPart = -b / (2 * a);
            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
            System.out.printf("Result: Two complex roots.\nRoot 1: %.2f + %.2fi\nRoot 2: %.2f - %.2fi\n",
                    realPart, imaginaryPart, realPart, imaginaryPart);
        }
    }
}