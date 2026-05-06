import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator implements ActionListener {
    
    // Declare GUI Components
    JFrame frame;
    JTextField expressionField; // Shows the full expression (e.g., "5 + 3 =")
    JTextField resultField;     // Shows the typed numbers and the final result
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[6];
    JButton addButton, subButton, mulButton, modButton;
    JButton equButton, clrButton;
    JPanel buttonPanel;
    JPanel displayPanel;

    // Variables for calculation logic
    double num1 = 0, num2 = 0, result = 0;
    char operator;

    public SimpleCalculator() {
        // 1. Setup the main frame
        frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 420); // Made slightly taller to fit the extra text field
        frame.setLayout(new BorderLayout());

        // 2. Setup the display panel and text fields
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1)); // Stack 2 rows, 1 column

        expressionField = new JTextField();
        expressionField.setFont(new Font("Arial", Font.PLAIN, 14)); // Slightly smaller font
        expressionField.setEditable(false);
        expressionField.setHorizontalAlignment(JTextField.RIGHT);
        expressionField.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        resultField = new JTextField();
        resultField.setFont(new Font("Arial", Font.BOLD, 24));
        resultField.setEditable(false);
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        displayPanel.add(expressionField);
        displayPanel.add(resultField);
        frame.add(displayPanel, BorderLayout.NORTH);

        // 3. Initialize operation buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        modButton = new JButton("%");
        equButton = new JButton("=");
        clrButton = new JButton("C");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = modButton;
        functionButtons[4] = equButton;
        functionButtons[5] = clrButton;

        for (int i = 0; i < 6; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
        }

        // 4. Initialize digit buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
        }

        // 5. Setup the GridLayout Panel for the buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); // 4 rows, 4 cols, 5px gaps
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add some padding

        // Row 1
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(addButton);

        // Row 2
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        // Row 3
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(mulButton);

        // Row 4
        buttonPanel.add(clrButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equButton);
        buttonPanel.add(modButton);

        // 6. Add button panel to frame and make visible
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleCalculator();
    }

    // 7. Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Digits
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                resultField.setText(resultField.getText().concat(String.valueOf(i)));
            }
        }
        
        // Clear button
        if (e.getSource() == clrButton) {
            expressionField.setText("");
            resultField.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
        }
        
        // Helper block to handle operator button logic without repeating code
        if (e.getSource() == addButton || e.getSource() == subButton || 
            e.getSource() == mulButton || e.getSource() == modButton) {
            
            // Prevent crash if user clicks operator before entering a number
            if (!resultField.getText().isEmpty()) { 
                num1 = Double.parseDouble(resultField.getText());
                
                if (e.getSource() == addButton) operator = '+';
                if (e.getSource() == subButton) operator = '-';
                if (e.getSource() == mulButton) operator = '*';
                if (e.getSource() == modButton) operator = '%';
                
                // Show the first number and the operator in the top field
                expressionField.setText(formatNumber(num1) + " " + operator);
                resultField.setText(""); // Clear bottom field for the next number
            }
        }
        
        // Equals button
        if (e.getSource() == equButton) {
            if (!resultField.getText().isEmpty()) {
                num2 = Double.parseDouble(resultField.getText());
                
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '%': result = num1 % num2; break;
                }
                
                // Update expression field to show the full math equation
                expressionField.setText(formatNumber(num1) + " " + operator + " " + formatNumber(num2) + " =");
                
                // Display final result
                resultField.setText(formatNumber(result));
                
                // Store result in num1 in case the user wants to keep calculating from this result
                num1 = result; 
            }
        }
    }

    // Helper method to remove the ".0" if the number is an integer
    private String formatNumber(double num) {
        if (num % 1 == 0) {
            return String.valueOf((int)num);
        } else {
            return String.valueOf(num);
        }
    }
}