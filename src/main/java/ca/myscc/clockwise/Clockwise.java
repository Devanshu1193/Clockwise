package ca.myscc.clockwise;

import ca.myscc.clockwise.database.Database;
import ca.myscc.clockwise.database.pojo.User;
import ca.myscc.clockwise.scenes.BaseScene;
import ca.myscc.clockwise.scenes.DatabaseSetupScene;
import ca.myscc.clockwise.scenes.LoginScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * The main entrypoint for the Clockwise containing essential information
 * for the app to function.
 * @date Oct. 24, 2023
 */
public class Clockwise extends Application {

    // Constants
    private static Stage stage;
    private static User user;
    private static final Timer timer = new Timer();

    @Override
    public void start(Stage stage) throws ExecutionException, InterruptedException {
        Clockwise.stage = stage;
        stage.setTitle("Clockwise");

        Database.getInstance().pullData();
        Database.testConnection(Database.getInstance().getDetails()).thenAccept((error) -> {
            System.out.println("Successfully connected to the database: " + (error == null));

            Platform.runLater(() -> {
                if (error == null) {
                    Database.getInstance().connect();
                    new LoginScene().open();
                } else {
                    new DatabaseSetupScene().open();
                }
            });
        });

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
     * Gets the stopwatch used to time the user.
     * @return The timer used to track time
     * @date Nov. 28, 2023
     * @author Santio Yousif
     */
    public static Timer getTimer() {
        return timer;
    }

    /**
     * Gets the currently logged-in user.
     * @return The user object, or null if not logged in
     * @date Nov. 28, 2023
     * @author Santio Yousif
     */
    public static User getUser() {
        return user;
    }

    /**
     * Sets the currently logged-in user.
     * @param user The user to log in as
     * @date Nov. 28, 2023
     * @author Santio Yousif
     */
    public static void setUser(User user) {
        Clockwise.user = user;
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