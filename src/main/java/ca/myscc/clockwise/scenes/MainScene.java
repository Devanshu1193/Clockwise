package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This scene is the main Scene or you can say main screen of Clockwise
 * allow the user to store their records and show up their history
 * @author Devanshu Suthar
 * @date Nov. 14, 2023
 * @version 1.0
 */

public class MainScene extends BaseScene{
    @Override
    Pane start() {
        BorderPane root = new BorderPane();

        DropShadow countTitleDS = new DropShadow(); // Created DropShadow effect
        countTitleDS.setOffsetY(2.0f);
        countTitleDS.setColor(Constants.PRIMARY_COLOR);

        // Title for the Count
        Text countTitle = new Text("CLOCKWISE");
        countTitle.setFill(Constants.TEXT_COLOR);
        countTitle.setFont(Font.font("Arial", FontWeight.BOLD,48));
        countTitle.setEffect(countTitleDS);

        VBox vBox = new VBox(countTitle);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Timer clock
        Text timer = new Text("00:00:00");
        timer.setFill(Constants.PRIMARY_COLOR);
        timer.setFont(Font.font(48));


        // Start button
        Button mainButton = new Button("START");

        Background greenBg = new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(10), null));
        Background redBg = new Background(new BackgroundFill(Color.color(0.93333334f, 0.5647059f, 0.5647059f), new CornerRadii(10), null));

        mainButton.setBackground(greenBg);
        mainButton.setFont(Font.font("Arial",FontWeight.BOLD,18));

        mainButton.setFont(Font.font(16));
        mainButton.setMinWidth(300);
        VBox buttonVBox = new VBox(mainButton);
        VBox.setMargin(buttonVBox, new Insets(0,0,50,0));
        buttonVBox.setAlignment(Pos.BOTTOM_CENTER);


        root.setCenter(timer);
        root.setTop(vBox);
        root.setBottom(buttonVBox);
        root.setPadding(new Insets(50,0,50,0));

        Clockwise.getTimer().listen(() -> {
            timer.setText(Clockwise.getTimer().getTime());
        });

        mainButton.setOnAction((e) -> {
            if (Clockwise.getTimer().isRunning()) {
                mainButton.setBackground(greenBg);
                mainButton.setText("START");
                Clockwise.getTimer().reset();
            } else {
                mainButton.setBackground(redBg);
                mainButton.setText("FINISH");
                Clockwise.getTimer().start();
            }
        });

        return root;
    }

}
