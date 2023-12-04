package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import ca.myscc.clockwise.charts.HistoryBarChart;
import ca.myscc.clockwise.database.pojo.Session;
import ca.myscc.clockwise.database.pojo.Wage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    
    @Override
    Pane start() {
        BorderPane root = new BorderPane();

        Button backButton = new Button("Back");
        backButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(4), null)));
        backButton.setOnAction((e) -> {
            new MainScene().open();
        });
        
        HistoryBarChart historyBarChart = new HistoryBarChart();
        BorderPane.setAlignment(historyBarChart, Pos.CENTER);
        historyBarChart.setPrefSize(500, 500);
        historyBarChart.setPadding(new Insets(10));
        
        Text historyTitle = new Text("Your History");
        historyTitle.setFill(Constants.TEXT_COLOR);
        historyTitle.setFont(Font.font(32));
        historyTitle.setTextAlignment(TextAlignment.CENTER);
        
        VBox historyChartWrapper = new VBox(historyTitle, historyBarChart);
        BorderPane.setAlignment(historyChartWrapper, Pos.CENTER);
        historyChartWrapper.setAlignment(Pos.CENTER);

        VBox history = new VBox();
        history.setPadding(new Insets(30));
        history.setSpacing(10);

        VBox historyWrapper = new VBox(history);
        BorderPane.setAlignment(historyWrapper, Pos.CENTER);
        historyWrapper.setAlignment(Pos.CENTER);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Session> sessions = Database.getInstance().sessions.getSessions(Clockwise.getUser().getId());
            List<Session> firstFive = sessions.subList(0, Math.min(sessions.size(), 5));
            
            // TODO: Add this when the user can set their wage
//            Wage wage = Database.getInstance().wages.getWage(Clockwise.getUser().getId());
            Wage wage = new Wage(15.2);
            
            Platform.runLater(() -> {
                for (Session session : firstFive) {
                    Text title = new Text(dateFormat.format(session.getTimeStarted() * 1000));
                    HBox sessionBox = new HBox(title);
                    
                    if (wage != null) {
                        Text money = new Text(String.format("$%.2f", session.getMoneyEarned(wage)));
                        money.setFill(Color.GREEN);
                        money.setFont(Font.font(16));
                        money.setTextAlignment(TextAlignment.RIGHT);
                        
                        HBox.setMargin(money, new Insets(0, 0, 0, 10));
                        sessionBox.getChildren().add(money);
                    }
                    
                    sessionBox.setPadding(new Insets(10));
                    sessionBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
                    
                    history.getChildren().add(sessionBox);
                }
            });
        });

        VBox rootBox = new VBox(backButton, historyChartWrapper, historyWrapper);
        rootBox.setPadding(new Insets(10));
        root.setCenter(rootBox);
        return root;
    }

}
