package ca.myscc.clockwise;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Holds constants relating to the project, anywhere from screen size
 * to the colors.
 * @date Oct. 24, 2023
 * @author Santio Yousif
 * @version 1.0
 */
@SuppressWarnings("MissingJavadoc")
public final class Constants {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 1024;

    public static final Color TEXT_COLOR = Color.color(
        200 / 255.0,
        220 / 255.0,
        230 / 255.0
    );

    public static final Color PRIMARY_COLOR = Color.color(
        20 / 255.0,
        130 / 255.0,
        200 / 255.0
    );

    public static final Color BACKGROUND_COLOR = Color.color(
        20 / 255.0,
        20 / 255.0,
        30 / 255.0
    );

    public static final Background BACKGROUND = new Background(new BackgroundFill(
        BACKGROUND_COLOR, null, null
    ));

}
