
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {
    
    // Text field to display the expression and result
    private JTextField displayField;
    // String to keep track of the current mathematical expression
    private String currentExpression = "";
    // Flag to check if a calculation was just completed
    private boolean isCalculated = false;

    public SimpleCalculator() {
        // 1. Set up the main window (JFrame)
        setTitle("Lab 2: Simple Calculator");
        setSize(320, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Set up the display text field
        displayField = new JTextField();
        displayField.setEditable(false); // Make it read-only
        displayField.setFont(new Font("Monospaced", Font.BOLD, 20));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE);
        // Add some padding
        displayField.setBorder(BorderFactory.createCompoundBorder(
                displayField.getBorder(), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        add(displayField, BorderLayout.NORTH);

        // 3. Set up the Button Panel with GridLayout (4 rows, 4 columns)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); // 5px horizontal and vertical gaps

        // Array of button labels matching standard layout + the requested operators
        String[] buttonLabels = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "C", "0", "=", "%"
        };

        // 4. Create and add buttons to the panel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.addActionListener(this); // Register the current class as the listener
            buttonPanel.add(button);
        }

        // Add the button panel to the center of the window
        add(buttonPanel, BorderLayout.CENTER);
    }

    // 5. Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Clear button resets everything
        if (command.equals("C")) {
            currentExpression = "";
            displayField.setText("");
            isCalculated = false;
            return;
        }

        // If a calculation was just finished, start a fresh expression on the next key press
        if (isCalculated) {
            currentExpression = "";
            isCalculated = false;
        }

        // Equals button calculates the result
        if (command.equals("=")) {
            calculateResult();
        } 
        // Operators (+, -, *, %) get spaces around them for easier parsing and readability
        else if (command.equals("+") || command.equals("-") || 
                 command.equals("*") || command.equals("%")) {
            currentExpression += " " + command + " ";
            displayField.setText(currentExpression);
        } 
        // Digits just get appended
        else {
            currentExpression += command;
            displayField.setText(currentExpression);
        }
    }

    // Helper method to evaluate a basic "Operand1 Operator Operand2" expression
    private void calculateResult() {
        try {
            // Split the expression by spaces (e.g., "5 + 3" becomes ["5", "+", "3"])
            String[] parts = currentExpression.trim().split(" ");
            
            if (parts.length == 3) {
                double num1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double num2 = Double.parseDouble(parts[2]);
                double result = 0;

                // Perform the requested math operation
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "%": 
                        if (num2 == 0) {
                            displayField.setText("Error: Mod by zero");
                            isCalculated = true;
                            return;
                        }
                        result = num1 % num2; 
                        break;
                }

                // Append the result to the expression so the user sees both
                currentExpression += " = " + result;
                displayField.setText(currentExpression);
                isCalculated = true;
            } else {
                displayField.setText("Invalid Expression");
                isCalculated = true;
            }
        } catch (Exception ex) {
            displayField.setText("Error");
            isCalculated = true;
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        // Ensure GUI creation is thread-safe
        SwingUtilities.invokeLater(() -> {
            SimpleCalculator calc = new SimpleCalculator();
            calc.setVisible(true);
        });
    }
}