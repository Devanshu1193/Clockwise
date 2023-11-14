package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Constants;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainScene extends BaseScene{
    @Override
    Pane start() {
        BorderPane root = new BorderPane();

        // Title for the Count
        Text countTitle = new Text("TIME CLOCK");
        countTitle.setFill(Constants.TEXT_COLOR);

        // Timer clock
        Text timer = new Text("00:00:00");
        timer.setFill(Constants.PRIMARY_COLOR);

        // Start button
        Button startButton = new Button("Start");
        startButton.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(4), null)));

        root.setCenter(timer);
        root.setTop(countTitle);
        root.setBottom(startButton);




        return root;
    }

}
