import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseApp {

    // Database credentials - UPDATE THESE to match your MySQL setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lab10db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password"; 

    public static void main(String[] args) {
        System.out.println("--- Starting Multithreaded Database Operations ---");

        // 1. Multithreading: Create multiple worker threads
        Thread worker1 = new Thread(new DatabaseWorker("Alice", "Engineering"));
        Thread worker2 = new Thread(new DatabaseWorker("Bob", "Marketing"));
        Thread worker3 = new Thread(new DatabaseWorker("Charlie", "HR"));

        // Start the threads so they run concurrently
        worker1.start();
        worker2.start();
        worker3.start();

        // 2. Exception Handling: Wait for all threads to finish safely
        try {
            worker1.join();
            worker2.join();
            worker3.join();
            System.out.println("\nAll workers finished inserting data.");
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted!");
            // Restore interrupted state
            Thread.currentThread().interrupt(); 
        }

        // 3. Read and display the final data from the database
        displayAllEmployees();
    }

    // Method to read data, demonstrating try-with-resources exception handling
    private static void displayAllEmployees() {
        System.out.println("\n--- Current Employees in Database ---");
        String query = "SELECT * FROM employees";

        // try-with-resources automatically closes the Connection, Statement, and ResultSet
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                System.out.printf("ID: %d | Name: %s | Dept: %s%n", id, name, dept);
            }

        } catch (SQLException e) {
            System.err.println("Database error while reading records:");
            e.printStackTrace();
        }
    }

    // A Runnable class that represents a single thread's task
    static class DatabaseWorker implements Runnable {
        private String empName;
        private String department;

        public DatabaseWorker(String empName, String department) {
            this.empName = empName;
            this.department = department;
        }

        @Override
        public void run() {
            String insertSQL = "INSERT INTO employees (name, department) VALUES (?, ?)";

            // Exception Handling: Catching SQL exceptions for database connections
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

                pstmt.setString(1, empName);
                pstmt.setString(2, department);
                pstmt.executeUpdate();

                System.out.println(Thread.currentThread().getName() + " successfully inserted: " + empName);

            } catch (SQLException e) {
                System.err.println(Thread.currentThread().getName() + " failed to insert data.");
                System.err.println("Error Message: " + e.getMessage());
            }
        }
    }
}