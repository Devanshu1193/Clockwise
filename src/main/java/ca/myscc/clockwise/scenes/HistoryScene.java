package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import ca.myscc.clockwise.database.pojo.Session;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * This scene shows the user their history and some graphs relating
 * to their tracked time
 * @author Santio Yousif
 * @date Nov. 28, 2023
 * @version 1.0
 */
public class HistoryScene extends BaseScene {

    @Override
    Pane start() {
        BorderPane root = new BorderPane();

        Button backButton = new Button("Back");
        backButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(4), null)));
        backButton.setOnAction((e) -> {
            new MainScene().open();
        });

        Text historyTitle = new Text("Your History");
        historyTitle.setFill(Constants.TEXT_COLOR);
        historyTitle.setFont(Font.font(32));
        historyTitle.setTextAlignment(TextAlignment.CENTER);

        VBox history = new VBox();
        history.setPadding(new Insets(30));
        history.setSpacing(10);

        VBox historyWrapper = new VBox(historyTitle, history);
        BorderPane.setAlignment(historyWrapper, Pos.CENTER);
        historyWrapper.setAlignment(Pos.CENTER);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Session> sessions = Database.getInstance().sessions.getSessions(Clockwise.getUser().getId());

            Platform.runLater(() -> {
                for (Session session : sessions) {
                    Text title = new Text(session.asFormatted());
                    HBox sessionBox = new HBox(title);

                    sessionBox.setPadding(new Insets(10));
                    sessionBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

                    history.getChildren().add(sessionBox);
                }
            });
        });

        VBox rootBox = new VBox(backButton, historyWrapper);
        rootBox.setPadding(new Insets(10));
        root.setCenter(rootBox);
        return root;
    }

}
