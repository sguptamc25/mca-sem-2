import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertExample {

    // Database credentials and URL
    // Change "university" to your database name
    static final String DB_URL = "jdbc:mysql://localhost:3306/university";
    static final String USER = "root";       // Your MySQL username
    static final String PASSWORD = "password"; // Your MySQL password

    public static void main(String[] args) {
        
        // The SQL query with placeholders (?) for the values we want to insert
        String insertQuery = "INSERT INTO Students (id, name, age) VALUES (?, ?, ?)";

        // Using try-with-resources to automatically close the connection and statement
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            System.out.println("Connected to the database successfully!");

            // Set the values for the placeholders
            pstmt.setInt(1, 101);             // Sets the 1st '?' to 101 (id)
            pstmt.setString(2, "John Doe");   // Sets the 2nd '?' to "John Doe" (name)
            pstmt.setInt(3, 20);              // Sets the 3rd '?' to 20 (age)

            // Execute the insert statement
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("A new student was inserted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Database connection or insertion failed!");
            e.printStackTrace();
        }
    }
}