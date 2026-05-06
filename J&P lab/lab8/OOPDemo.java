// 1. Base Class (Demonstrates Classes)
class Shape {
    protected String name;

    // Constructor
    public Shape(String name) {
        this.name = name;
    }

    // Method meant to be overridden by child classes
    public double calculateArea() {
        System.out.println("Area calculation is not defined for a generic shape.");
        return 0.0;
    }

    // --- OVERLOADING DEMONSTRATION ---

    // Version 1: No parameters
    public void printInfo() {
        System.out.println("Shape: " + name);
    }

    // Version 2: Overloaded with one String parameter
    public void printInfo(String color) {
        System.out.println("Shape: " + name + " | Color: " + color);
    }
}

// 2. Child Class 1 (Demonstrates Inheritance)
class Circle extends Shape {
    private double radius;

    public Circle(String name, double radius) {
        super(name); // Calls the parent class constructor
        this.radius = radius;
    }

    // --- OVERRIDING DEMONSTRATION ---
    // Replaces the parent's version of calculateArea()
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// 3. Child Class 2 (Demonstrates Inheritance)
class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(String name, double length, double width) {
        super(name);
        this.length = length;
        this.width = width;
    }

    // --- OVERRIDING DEMONSTRATION ---
    @Override
    public double calculateArea() {
        return length * width;
    }
}

// Main class to run the program
public class OOPDemo {

    public static void main(String[] args) {

        System.out.println("--- 1. Testing the Base Shape ---");
        Shape genericShape = new Shape("Generic Polygon");
        // Testing Overloading
        genericShape.printInfo();
        genericShape.printInfo("Blue");
        // Testing parent method
        genericShape.calculateArea();

        System.out.println("\n--- 2. Testing the Circle ---");
        Circle myCircle = new Circle("Perfect Circle", 5.0);
        myCircle.printInfo("Red"); // Inherited from Shape
        // Testing Overridden method
        System.out.printf("Area of Circle: %.2f\n", myCircle.calculateArea());

        System.out.println("\n--- 3. Testing the Rectangle ---");
        Rectangle myRectangle = new Rectangle("Golden Rectangle", 4.0, 6.0);
        myRectangle.printInfo("Green"); // Inherited from Shape
        // Testing Overridden method
        System.out.printf("Area of Rectangle: %.2f\n", myRectangle.calculateArea());
    }
}