package ca.myscc.clockwise.scenes;

import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The registering screen for Clockwise project
 * for the app to function.
 * @date Nov. 16, 2023
 * @author nashwan Hanna
 * @version 1.5
 */

class RegisterScreen extends BaseScene {

    private JTextField userIdField;
    private JPasswordField passwordField, confirmPasswordField;

    @Override
    Pane start() {
        // Create components
        JLabel userIdLabel = new JLabel("User ID:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        userIdField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        JButton createButton = new JButton("Create");
        JButton backToLoginButton = new JButton("Login");

        // Set layout
        setLayout(new GridLayout(5, 2, 10, 10));

        // Add components to the frame
        add(userIdLabel);
        add(userIdField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(new JLabel()); // Placeholder
        add(createButton);
        add(new JLabel()); // Placeholder
        add(backToLoginButton);

        // Add action listeners
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                char[] password = passwordField.getPassword();
                char[] confirmPassword = confirmPasswordField.getPassword();

                if (isPasswordMatch(password, confirmPassword)) {
                    // Handle registration logic here
                    JOptionPane.showMessageDialog(null, "User created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Password does not match!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                new LoginScreen().setVisible(true); // Open the login screen
            }
        });
        return null;
    }


    /**
     * The registering screen for Clockwise project
     * for the app to function.
     * @date Nov. 16, 2023
     * @author nashwan Hanna
     */
    private boolean isPasswordMatch(char[] password, char[] confirmPassword) {
        return new String(password).equals(new String(confirmPassword));
    }
}