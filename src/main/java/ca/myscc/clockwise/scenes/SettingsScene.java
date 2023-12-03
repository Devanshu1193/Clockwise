package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This scene allows the user to customize their wage and
 * other settings.
 * @author Santio Yousif
 * @date Nov. 28, 2023
 * @version 1.0
 */
public class SettingsScene extends BaseScene {

    @Override
    Pane start() {
        BorderPane root = new BorderPane();

        Button backButton = new Button("Back");
        backButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(4), null)));
        backButton.setOnAction((e) -> {
            new MainScene().open();
        });

        Text settingsTitle = new Text("Settings");
        settingsTitle.setFill(Constants.TEXT_COLOR);
        settingsTitle.setFont(Font.font(32));
        settingsTitle.setTextAlignment(TextAlignment.CENTER);

        Label wageLabel = new Label("Your Hourly Wage");
        TextField wageField = new TextField();
        Text wageError = new Text("");
        wageError.setFill(Color.RED);

        VBox settings = new VBox(
            settingsTitle,
            new HBox(wageLabel, wageField),
            wageError
        );

        VBox rootBox = new VBox(backButton, settings);
        rootBox.setPadding(new Insets(10));
        root.setCenter(rootBox);

        return root;
    }

}
