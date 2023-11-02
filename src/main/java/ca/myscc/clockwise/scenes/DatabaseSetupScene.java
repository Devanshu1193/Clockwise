package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This scene controls the setup of the database, this is shown only on first launch
 * to allow users to provider a database connection to use throughout the app.
 * @author Devanshu Suthar
 * @date Oct. 31, 2023
 * @version 1.0
 */
public class DatabaseSetupScene extends BaseScene {

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
        root.setCenter(text);
        BorderPane.setAlignment(text, Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        BorderPane.setAlignment(text, Pos.CENTER);

        gridPane.add(root,0,0,2,1);

        // First Row
        Label dbHost = new Label("HOST : ");
        dbHost.setTextFill(Constants.TEXT_COLOR);
        dbHost.setFont(Font.font("Arial", FontWeight.BOLD,18));
        TextField hostTextField = new TextField();
        gridPane.add(dbHost,0,1);
        gridPane.add(hostTextField, 1,1);

        // Second Row
        Label dbUser = new Label("USER : ");
        dbUser.setTextFill(Constants.TEXT_COLOR);
        dbUser.setFont(Font.font("Arial", FontWeight.BOLD,18));
        TextField userTextField = new TextField();
        gridPane.add(dbUser, 0,2);
        gridPane.add(userTextField, 1,2);

        // Third Row
        Label dbPassword = new Label("PASSWORD : ");
        dbPassword.setTextFill(Constants.TEXT_COLOR);
        dbPassword.setFont(Font.font("Arial", FontWeight.BOLD,18));
        TextField passTextField = new TextField();
        gridPane.add(dbPassword, 0,3);
        gridPane.add(passTextField,1,3);

        // Fourth Row
        Label dbName = new Label("NAME : ");
        dbName.setTextFill(Constants.TEXT_COLOR);
        dbName.setFont(Font.font("Arial", FontWeight.BOLD,18));
        TextField nameTextField = new TextField();
        gridPane.add(dbName,0,4);
        gridPane.add(nameTextField, 1,4);

        // Buttons
        Button validateButton = new Button("Validate");
        Button continueButton = new Button("Continue");
        DropShadow shadow = new DropShadow();

        validateButton.setOnAction(event -> {
            validateButton.setEffect(shadow);
            validateButton.setText("Valid");
            continueButton.setDisable(false);
            validateButton.setDisable(true);
        });

        continueButton.setOnAction(event -> {
            continueButton.setEffect(shadow);
//            continueButton.setText("");
        });

        validateButton.setMaxWidth(Double.MAX_VALUE);
        continueButton.setMaxWidth(Double.MAX_VALUE);
        continueButton.setDisable(true);

        gridPane.add(validateButton,0,6);

        gridPane.add(continueButton,1,6);

        // Scene
        gridPane.setAlignment(Pos.CENTER);


        return gridPane;
    }

}
