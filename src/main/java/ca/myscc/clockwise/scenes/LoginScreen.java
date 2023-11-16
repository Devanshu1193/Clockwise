package ca.myscc.clockwise.scenes;

import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The registering screen for Clockwise project
 * for the app to function.
 * @date Nov. 16, 2023
 * @author nashwan Hanna
 * @version 1.5
 */

class LoginScreen extends BaseScene {

    private JTextField loginUserIdField;
    private JPasswordField loginPasswordField;

    
    Pane start() {
        setTitle("Login Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel loginUserIdLabel = new JLabel("User ID:");
        JLabel loginPasswordLabel = new JLabel("Password:");

        loginUserIdField = new JTextField();
        loginPasswordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        // Set layout
        setLayout(new GridLayout(4, 2, 10, 10));

        // Add components to the frame
        add(loginUserIdLabel);
        add(loginUserIdField);
        add(loginPasswordLabel);
        add(loginPasswordField);
        add(new JLabel()); // Placeholder
        add(loginButton);
        add(new JLabel()); // Placeholder
        add(createAccountButton);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login logic here
                JOptionPane.showMessageDialog(null, "Login successful!");
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                new RegisterScreen().setVisible(true); // Open the registration screen
            }
        });
        return null;
    }
}