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

public class LoginScene extends BaseScene {

    @Override
    Pane start() {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        // Create components
        Label loginUserIdLabel = new Label("User ID:");
        Label loginPasswordLabel = new Label("Password:");
        loginUserIdLabel.setTextFill(Constants.TEXT_COLOR);
        loginPasswordLabel.setTextFill(Constants.TEXT_COLOR);

        TextField loginUserIdField = new TextField();
        PasswordField loginPasswordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        // Add components to the frame
        root.add(loginUserIdLabel, 0, 0);
        root.add(loginUserIdField, 1, 0);
        root.add(loginPasswordLabel, 0, 1);
        root.add(loginPasswordField, 1, 1);
        root.add(loginButton, 1, 2);
        root.add(createAccountButton, 1, 3);

        // Add action listeners
        loginButton.setOnMouseClicked((e) -> {
            // TODO: Handle login button
        });

        createAccountButton.setOnMouseClicked((e) -> {
            new RegisterScene().open();
        });

        return root;
    }
}