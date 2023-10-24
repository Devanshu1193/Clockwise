package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Constants;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This scene controls the setup of the database, this is shown only on first launch
 * to allow users to provider a database connection to use throughout the app.
 * @author Santio Yousif
 * @date Oct. 24, 2023
 * @version 1.0
 */
public class DatabaseSetupScene extends BaseScene {

    @Override
    void start(BorderPane root) {
        Text text = new Text("Hello World");
        text.setFill(Constants.TEXT_COLOR);
        text.setFont(Font.font("Arial", 48));

        root.setCenter(text);
        BorderPane.setAlignment(text, Pos.CENTER);
    }

}
