package ca.myscc.clockwise.charts;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import ca.myscc.clockwise.database.Database;
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * A bar chart that shows the history of the user's sessions
 * @date Dec. 3, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class HistoryBarChart extends BarChart<String, Number> {
    
    public HistoryBarChart() {
        super(new CategoryAxis(), new NumberAxis());
        
        String css = """
            .chart {
                -fx-padding: 10px;
                -fx-background: #""" + Constants.BACKGROUND_COLOR.toString().substring(2) + """
            }
            
            .chart-content {
                -fx-padding: 30px;
            }
            
            .chart-legend {
               -fx-background-color:  transparent;
               -fx-padding: 20px;
            }
            
            .chart-legend-item-symbol{
               -fx-background-radius: 0;
            }
            
            .chart-legend-item{
                -fx-text-fill: #""" + Constants.TEXT_COLOR.toString().substring(2) + """
            }
        """;
        
        this.getXAxis().setLabel("Date");
        this.getYAxis().setLabel("Hours Worked");
        this.setLegendVisible(false);
        
        final XYChart.Series series = new XYChart.Series();
        this.getData().add(series);
        this.setStyle(css);
        
        // Set minimum and maximum values
        NumberAxis yAxis = (NumberAxis) this.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(12);
        
        for (int i = 0; i < 7; i++) {
            String key = switch (i) {
                case 0 -> "Today";
                case 1 -> "Yesterday";
                default -> String.format("%d days ago", i);
            };
            
            series.getData().add(new XYChart.Data(key, 0.01));
            
            int finalI = i;
            Executors.newSingleThreadExecutor().execute(() -> {
                long time = Database.getInstance().sessions.getTotalTimeAtDay(Clockwise.getUser().getId(), finalI);
                double hours = Duration.ofSeconds(time).toMinutes() / 60.0;
                
                if (hours == 0) return;
                Platform.runLater(() -> {
                    series.getData().set(finalI, new XYChart.Data(key, hours));
                });
            });
        }
    }
    
}
