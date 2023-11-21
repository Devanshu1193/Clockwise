package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * The registering screen for Clockwise project
 * for the app to function.
 * @date Nov. 16, 2023
 * @author nashwan Hanna
 * @version 1.5
 */

public class RegisterScene extends BaseScene {

    private TextField userIdField;
    private PasswordField passwordField, confirmPasswordField;

    @Override
    Pane start() {
        // Create components
        Label userIdLabel = new Label("User ID:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");
        userIdLabel.setTextFill(Constants.TEXT_COLOR);
        passwordLabel.setTextFill(Constants.TEXT_COLOR);
        confirmPasswordLabel.setTextFill(Constants.TEXT_COLOR);

        userIdField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        Button createButton = new Button("Create");
        Button backToLoginButton = new Button("Login");

        // Set layout
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        // Add components to the frame
        root.add(userIdLabel, 0, 0);
        root.add(userIdField, 1, 0);
        root.add(passwordLabel, 0, 1);
        root.add(passwordField, 1, 1);
        root.add(confirmPasswordLabel, 0, 2);
        root.add(confirmPasswordField, 1, 2);
        root.add(createButton, 1, 3);
        root.add(backToLoginButton, 1, 4);

        // Add action listeners
        createButton.setOnMouseClicked((e) -> {
            String userId = userIdField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (password.equals(confirmPassword)) {
                // TODO: Create user
            } else {
                // TODO: Show an error to the user
            }
        });

        backToLoginButton.setOnMouseClicked((e) -> {
            new LoginScene().open();
        });

        return root;
    }
}