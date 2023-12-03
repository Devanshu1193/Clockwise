package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import ca.myscc.clockwise.database.animations.WidthTransition;
import ca.myscc.clockwise.database.pojo.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.Executors;

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
        
        Background greenBg = new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(10), null));
        Background redBg = new Background(new BackgroundFill(Color.color(0.93333334f, 0.5647059f, 0.5647059f), new CornerRadii(10), null));
        Background cyan = new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), null));
        Background grayBg = new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), null));
        
        // Start button
        Button mainButton = new Button("START");

        mainButton.setBackground(greenBg);
        mainButton.setFont(Font.font(16));
        mainButton.setMinWidth(300);
        
        // Secondary button
        Button secondaryButton = new Button("Pause");
        secondaryButton.setBackground(cyan);
        secondaryButton.setFont(Font.font(16));
        secondaryButton.setMaxWidth(0);
        secondaryButton.setVisible(false);

        Button historyButton = new Button("View History");
        historyButton.setBackground(grayBg);
        historyButton.setFont(Font.font(14));
        historyButton.setMinWidth(175);

        Button settingsButton = new Button("Settings");
        settingsButton.setBackground(grayBg);
        settingsButton.setFont(Font.font(14));
        settingsButton.setMinWidth(115);

        HBox buttonBox = new HBox(historyButton, settingsButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        HBox mainButtonBox = new HBox(mainButton, secondaryButton);
        
        VBox buttonVBox = new VBox(
            mainButtonBox,
            buttonBox
        );

        VBox.setMargin(buttonVBox, new Insets(0,0,50,0));
        buttonVBox.setSpacing(20);
        buttonVBox.setAlignment(Pos.BOTTOM_LEFT);
        mainButtonBox.setPadding(new Insets(
            0,
            0,
            0,
            (Constants.SCREEN_WIDTH / 2.0) - 150
        ));
        
        mainButtonBox.setSpacing(10);

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

                Executors.newSingleThreadExecutor().execute(() -> {
                    Session session = new Session(
                        Clockwise.getUser().getId(),
                        Clockwise.getTimer().getTimeStarted(),
                        System.currentTimeMillis() / 1000
                    );

                    Database.getInstance().sessions.track(session);
                });

                Clockwise.getTimer().reset();
                
                WidthTransition mainAnimation = new WidthTransition(mainButton, 300);
                mainAnimation.setDuration(Duration.seconds(0.6));
                mainAnimation.play();
                
                WidthTransition secondaryAnimation = new WidthTransition(secondaryButton, 0);
                secondaryAnimation.setDuration(Duration.seconds(0.6));
                secondaryAnimation.play();
                
                secondaryAnimation.onFinished(() -> {
                    secondaryButton.setVisible(false);
                    secondaryButton.setText("Pause");
                });
                
            } else {
                mainButton.setBackground(redBg);
                mainButton.setText("FINISH");
                Clockwise.getTimer().start();
                
                WidthTransition mainAnimation = new WidthTransition(mainButton, mainButton.getWidth() / 4 * 2);
                mainAnimation.setDuration(Duration.seconds(0.6));
                
                mainAnimation.play();
                
                WidthTransition secondaryAnimation = new WidthTransition(secondaryButton, mainButton.getWidth() / 4 * 2 - 10);
                secondaryAnimation.setDuration(Duration.seconds(0.6));
                
                secondaryButton.setVisible(true);
                secondaryAnimation.play();
            }
        });
        
        secondaryButton.setOnAction((e) -> {
            if (!Clockwise.getTimer().isPaused()) {
                Clockwise.getTimer().pause();
                secondaryButton.setText("Resume");
            } else {
                Clockwise.getTimer().start();
                secondaryButton.setText("Pause");
            }
        });

        historyButton.setOnAction((e) -> {
            new HistoryScene().open();
        });

        settingsButton.setOnAction((e) -> {
            new SettingsScene().open();
        });

        return root;
    }

}
