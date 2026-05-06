import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeDemo {

    public static void main(String[] args) {

        System.out.println("--- 1. Demonstrating LocalDate vs LocalDateTime ---");

        // LocalDate: Only the date components
        LocalDate todayDate = LocalDate.now();
        LocalDate specificDate = LocalDate.of(2026, 1, 1);

        System.out.println("Current LocalDate:   " + todayDate);
        System.out.println("Specific LocalDate:  " + specificDate);

        System.out.println();

        // LocalDateTime: Date + Time components
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime specificDateTime = LocalDateTime.of(2026, 1, 1, 14, 30, 0); // 2:30 PM

        System.out.println("Current LocalDateTime:  " + nowDateTime);
        System.out.println("Specific LocalDateTime: " + specificDateTime);

        System.out.println("\n--- 2. Calculating Difference in Days ---");

        // Define two dates to compare
        LocalDate startDate = LocalDate.of(2026, 2, 10);
        LocalDate endDate = LocalDate.now(); // Current date

        // Call our custom function
        long daysBetween = calculateDaysDifference(startDate, endDate);

        System.out.println("Start Date: " + startDate);
        System.out.println("End Date:   " + endDate);
        System.out.println("Difference: " + daysBetween + " days");
    }

    /**
     * Function to calculate the difference in days between two LocalDates.
     * Uses ChronoUnit.DAYS for an accurate total count.
     * * @param start The starting date
     * 
     * @param end The ending date
     * @return The total number of days between the two dates
     */
    public static long calculateDaysDifference(LocalDate start, LocalDate end) {
        // ChronoUnit is the most robust way to get exact differences in Java 8+
        return ChronoUnit.DAYS.between(start, end);
    }
}