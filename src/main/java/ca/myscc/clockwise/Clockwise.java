package ca.myscc.clockwise;

import ca.myscc.clockwise.scenes.BaseScene;
import ca.myscc.clockwise.scenes.DatabaseSetupScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The main entrypoint for the Clockwise containing essential information
 * for the app to function.
 * @date Oct. 24, 2023
 */
public class Clockwise extends Application {

    // Constants
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        Clockwise.stage = stage;
        stage.setTitle("Clockwise");
        new DatabaseSetupScene().open();

        stage.show();
    }

    /**
     * Called when the application is launched
     * @param args The arguments passed through in the command line.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Gets the main stage of the application, this allows the use of
     * {@link BaseScene#open()} to function.
     * @return The main stage for the application
     * @date Oct.24, 2023
     * @author Santio Yousif
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Gets a resource file as a UTF-8 string
     * @param file The file to get
     * @return The file content, or null if it doesn't exist or
     * can't be decoded.
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    public static String getResourceAsString(String file) {
        try {
            try (InputStream stream = Clockwise.class.getClassLoader().getResourceAsStream(file)) {
                if (stream == null) throw new IllegalStateException("Failed to find the setup script");

                return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}