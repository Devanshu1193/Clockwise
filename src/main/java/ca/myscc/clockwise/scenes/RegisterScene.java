package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The registering screen for Clockwise project
 * for the app to function.
 * @date Nov. 16, 2023
 * @author nashwan Hanna
 * @version 1.5
 */

public class RegisterScene extends BaseScene {

    private TextField userIdField, firstNameField, lastNameField;
    private PasswordField passwordField, confirmPasswordField;

    @Override
    Pane start() {
        BorderPane root = new BorderPane();


        Text text = new Text("CLOCKWISE");
        DropShadow titleDS = new DropShadow(); // Created DropShadow effect
        titleDS.setOffsetY(2.0f);
        titleDS.setColor(Constants.PRIMARY_COLOR);

        text.setFill(Constants.TEXT_COLOR);
        text.setEffect(titleDS);
        text.setFont(Font.font("Arial", 48));
        BorderPane.setAlignment(text, Pos.CENTER);
        root.setTop(text);

        root.setPadding(new Insets(40));

        // Create components
        Label userIdLabel = new Label("Username:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");
        userIdLabel.setTextFill(Constants.TEXT_COLOR);
        firstNameLabel.setTextFill(Constants.TEXT_COLOR);
        lastNameLabel.setTextFill(Constants.TEXT_COLOR);
        passwordLabel.setTextFill(Constants.TEXT_COLOR);
        confirmPasswordLabel.setTextFill(Constants.TEXT_COLOR);
        
        userIdField = new TextField();
        firstNameField = new TextField();
        lastNameField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        Button createButton = new Button("Create");
        Button backToLoginButton = new Button("Login");

        // Set layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        root.setCenter(grid);
        
        VBox createButtonBox = new VBox(createButton);
        createButtonBox.setPadding(new Insets(20, 0, 0, 0));
        
        VBox backToLoginButtonBox = new VBox(backToLoginButton);
        backToLoginButtonBox.setPadding(new Insets(20, 0, 0, 0));
        
        VBox firstNameLabelBox = new VBox(firstNameLabel);
        firstNameLabelBox.setPadding(new Insets(10, 0, 0, 0));
        
        VBox firstNameFieldBox = new VBox(firstNameField);
        firstNameFieldBox.setPadding(new Insets(10, 0, 0, 0));
        
        VBox passwordLabelBox = new VBox(passwordLabel);
        passwordLabelBox.setPadding(new Insets(10, 0, 0, 0));
        
        VBox passwordFieldBox = new VBox(passwordField);
        passwordFieldBox.setPadding(new Insets(10, 0, 0, 0));
        
        // Add components to the frame
        grid.add(userIdLabel, 0, 0);
        grid.add(userIdField, 1, 0);
        grid.add(firstNameLabelBox, 0, 1);
        grid.add(firstNameFieldBox, 1, 1);
        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(passwordLabelBox, 0, 3);
        grid.add(passwordFieldBox, 1, 3);
        grid.add(confirmPasswordLabel, 0, 4);
        grid.add(confirmPasswordField, 1, 4);
        grid.add(createButtonBox, 1, 5);
        grid.add(backToLoginButtonBox, 0, 5);

        Text error = new Text("");
        error.setFill(Color.RED);
        grid.add(error, 0, 7, 2, 1);
        
        createButton.setMaxWidth(2000);
        createButton.setPadding(new Insets(4, 12, 4, 12));
        createButton.setBackground(Constants.GREEN_BUTTON_BACKGROUND);
        backToLoginButton.setMaxWidth(2000);
        backToLoginButton.setPadding(new Insets(4, 12, 4, 12));
        backToLoginButton.setBackground(Constants.GRAY_BUTTON_BACKGROUND);
        
        // Add action listeners
        createButton.setOnMouseClicked((e) -> {
            if (userIdField.getText().isEmpty()
                || firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || confirmPasswordField.getText().isEmpty()) {
                error.setText("Please fill out all fields");
                return;
            }
            
            String userId = userIdField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (password.equals(confirmPassword)) {
                Database.getInstance().users.create(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    userId,
                    password
                );

                new LoginScene().open();
            } else {
                error.setText("Password does not match");
                confirmPasswordField.setText("");
                passwordField.setText("");
            }
        });

        backToLoginButton.setOnMouseClicked((e) -> {
            new LoginScene().open();
        });
        
        return root;
    }
}