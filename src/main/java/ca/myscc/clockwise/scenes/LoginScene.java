package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import ca.myscc.clockwise.database.pojo.User;
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

public class LoginScene extends BaseScene {

    @Override
    Pane start() {
        BorderPane root = new BorderPane();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        root.setCenter(grid);

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
        grid.setHgap(20);

        // Create components
        Label loginUserIdLabel = new Label("Username:");
        Label loginPasswordLabel = new Label("Password:");
        loginUserIdLabel.setTextFill(Constants.TEXT_COLOR);
        loginPasswordLabel.setTextFill(Constants.TEXT_COLOR);

        TextField loginUserIdField = new TextField();
        PasswordField loginPasswordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Register");
        Text error = new Text("");
        error.setFill(Color.RED);
        
        VBox loginButtonBox = new VBox(loginButton);
        loginButtonBox.setPadding(new Insets(10, 0, 0, 0));
        
        VBox createAccountButtonBox = new VBox(createAccountButton);
        createAccountButtonBox.setPadding(new Insets(10, 0, 0, 0));
        
        // Add components to the frame
        grid.add(loginUserIdLabel, 0, 0);
        grid.add(loginUserIdField, 1, 0);
        grid.add(loginPasswordLabel, 0, 1);
        grid.add(loginPasswordField, 1, 1);
        grid.add(loginButtonBox, 1, 2);
        grid.add(createAccountButtonBox, 0, 2);
        grid.add(error, 0, 3, 2, 1);
        
        grid.setHgap(3);
        
        // Add space in between
        loginButton.setMaxWidth(2000);
        loginButton.setBackground(Constants.GREEN_BUTTON_BACKGROUND);
        createAccountButton.setMaxWidth(2000);
        createAccountButton.setPadding(new Insets(4, 12, 4, 12));
        createAccountButton.setBackground(Constants.GRAY_BUTTON_BACKGROUND);

        // Add action listeners
        loginButton.setOnMouseClicked((e) -> {
            User user = Database.getInstance().users.login(loginUserIdField.getText(), loginPasswordField.getText());
            if (user == null) {
                error.setText("Invalid username or password");
                loginUserIdField.setText("");
                loginPasswordField.setText("");
            } else {
                Clockwise.setUser(user);
                new MainScene().open();
            }
        });

        createAccountButton.setOnMouseClicked((e) -> {
            new RegisterScene().open();
        });

        return root;
    }
}