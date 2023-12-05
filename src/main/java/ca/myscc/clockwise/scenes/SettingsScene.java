package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import ca.myscc.clockwise.database.pojo.Wage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

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
        
        AtomicReference<Double> defaultWage = new AtomicReference<>(-1.0);
        
        Text settingsTitle = new Text("Settings");
        settingsTitle.setFill(Constants.TEXT_COLOR);
        settingsTitle.setFont(Font.font(32));
        settingsTitle.setTextAlignment(TextAlignment.CENTER);
        settingsTitle.setTranslateY(-15);

        Label wageLabel = new Label("Your Hourly Wage");
        wageLabel.setTextFill(Constants.TEXT_COLOR);
        wageLabel.setFont(Font.font(16));
        
        TextField wageField = new TextField();
        wageField.setFont(Font.font(16));
        
        Text wageError = new Text("");
        wageError.setFill(Color.RED);
        
        wageField.setDisable(true);
        Executors.newSingleThreadExecutor().execute(() -> {
            Wage wage = Database.getInstance().wages.getWage(Clockwise.getUser().getId());
            Platform.runLater(() -> {
                if (wage != null) {
                    wageField.setText("" + wage.getWage());
                } else {
                    wageField.setText("0.00");
                }
                
                defaultWage.set(Double.parseDouble(wageField.getText()));
                wageField.setDisable(false);
            });
        });

        HBox wageSettings = new HBox(wageLabel, wageField);
        wageSettings.setSpacing(10);
        
        VBox settings = new VBox(
            settingsTitle,
            wageSettings,
            wageError
        );
        
        Background greenBg = new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(10), null));
        Background grayBg = new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), null));
        
        Button saveButton = new Button("Save Changes");
        saveButton.setBackground(greenBg);
        saveButton.setFont(Font.font(16));
        saveButton.setMinWidth(300);
        saveButton.setDisable(true);
        
        Button backButton = new Button("Back");
        backButton.setBackground(grayBg);
        backButton.setFont(Font.font(16));
        backButton.setMinWidth(300);
        backButton.setOnAction((e) -> {
            new MainScene().open();
        });
        
        root.setPadding(new Insets(0, 0, 50,0));
        
        VBox rootBox = new VBox(settings);
        rootBox.setPadding(new Insets(50));
        root.setCenter(rootBox);
        rootBox.setFillWidth(true);
        
        VBox buttonBox = new VBox(saveButton, backButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        root.setBottom(buttonBox);
        
        BorderPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(rootBox, Pos.CENTER);
        
        settings.setAlignment(Pos.CENTER);
        wageSettings.setAlignment(Pos.CENTER);
        
        wageField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double wage = Double.parseDouble(newValue);
                if (wage < 0) {
                    wageError.setText("Wage must be positive");
                    saveButton.setDisable(true);
                } else {
                    wageError.setText("");
                    saveButton.setDisable(wage == defaultWage.get() || defaultWage.get() == -1);
                }
            } catch (NumberFormatException e) {
                saveButton.setDisable(true);
                wageError.setText("Invalid wage provided");
            }
        });
        
        saveButton.setOnAction((e) -> {
            double wage = Double.parseDouble(wageField.getText());
            Database.getInstance().wages.setWage(Clockwise.getUser().getId(), wage);
            
            Wage wageObj = new Wage(wage);
            wageField.setText("" + wageObj.getWage());
            
            defaultWage.set(wage);
            saveButton.setDisable(true);
        });
        
        return root;
    }

}
