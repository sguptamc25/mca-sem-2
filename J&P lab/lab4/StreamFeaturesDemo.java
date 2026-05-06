import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// 1. A simple class to represent our data
class Employee {
    private String name;
    private String department;
    private double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + " (" + department + " - $" + salary + ")";
    }
}

public class StreamFeaturesDemo {

    public static void main(String[] args) {
        // Create a sample list of employees
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 75000),
                new Employee("Bob", "HR", 55000),
                new Employee("Charlie", "IT", 82000),
                new Employee("Diana", "Finance", 90000),
                new Employee("Eve", "IT", 60000));

        System.out.println("--- Original Employee List ---");
        employees.forEach(System.out::println); // Method reference (a type of Lambda)

        // ==========================================
        // DEMO 1: Stream, Filter, and Map
        // ==========================================
        System.out.println("\n--- 1. IT Department Employee Names (Filter & Map) ---");

        List<String> itEmployeeNames = employees.stream()
                // FILTER: Keep only employees where department is "IT" using a Lambda
                // expression
                .filter(emp -> emp.getDepartment().equals("IT"))
                // MAP: Transform the Employee object into just their name (String)
                .map(emp -> emp.getName())
                // Collect the results back into a List
                .collect(Collectors.toList());

        System.out.println(itEmployeeNames);

        // ==========================================
        // DEMO 2: Stream, Map, and Reduce
        // ==========================================
        System.out.println("\n--- 2. Total Salary of All Employees (Map & Reduce) ---");

        double totalSalary = employees.stream()
                // MAP: Extract just the salary from each employee
                .map(Employee::getSalary)
                // REDUCE: Combine all salaries into a single sum.
                // 0.0 is the starting value, (a, b) -> a + b is the Lambda to add them up.
                .reduce(0.0, (sum, salary) -> sum + salary);

        System.out.println("Total Salary: $" + totalSalary);

        // ==========================================
        // DEMO 3: Optional and Reduce
        // ==========================================
        System.out.println("\n--- 3. Highest Paid Employee (Reduce & Optional) ---");

        // We use Optional because the list might be empty, meaning there is no highest
        // earner.
        Optional<Employee> highestEarner = employees.stream()
                // REDUCE: Compare two employees and keep the one with the higher salary
                .reduce((emp1, emp2) -> emp1.getSalary() > emp2.getSalary() ? emp1 : emp2);

        // OPTIONAL: Safely check if a value is present before printing
        if (highestEarner.isPresent()) {
            System.out.println("Highest Earner: " + highestEarner.get().getName());
        } else {
            System.out.println("No employees found.");
        }

        // Alternative, more modern way to use Optional:
        // highestEarner.ifPresent(emp -> System.out.println("Highest Earner: " +
        // emp.getName()));
    }
}