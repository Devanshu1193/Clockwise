package ca.myscc.clockwise.database;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.database.queries.TimeQuery;
import ca.myscc.clockwise.database.queries.UserQuery;
import ca.myscc.clockwise.database.queries.WageQuery;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Handles the internal database connection along with the
 * fetching and saving functionality required
 * @date Oct. 31, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public final class Database {

    private Database() {}
    private static final Database INSTANCE = new Database();

    private ConnectionDetails details;
    private Connection connection;
    private final File saveFile = new File("./data.clockwise");

    public UserQuery users;
    public TimeQuery sessions;
    public WageQuery wages;

    /**
     * Gets the main database instance
     * @return The primary instance of the class
     * @date Oct. 31, 2023
     * @author Santio Yousif
     */
    public static Database getInstance() {
        return INSTANCE;
    }

    /**
     * Tests a connection detail to see if it can properly
     * connect or not, this does not set any values.
     * @param connection The connection details to test
     * @return A completable future holding the error message
     * in a string, or null if there are no errors
     * @author Santio Yousif
     * @date Nov. 7, 2023
     */
    public static CompletableFuture<String> testConnection(ConnectionDetails connection) {
        CompletableFuture<String> future = new CompletableFuture<>();
        if (connection == null) future.complete("Connection provided is null");

        // Run asynchronously, this chooses a thread from a pool that's available for us to run
        // code in
        Executors.newSingleThreadExecutor().execute(() -> {
            try (Connection database = Objects.requireNonNull(connection).toConnection()) {

                // Successful connection, make sure we aren't closed, and we can consider
                // this connection to be valid
                future.complete(database != null && !database.isClosed() ? null : "Database is closed");

            } catch (SQLException | NullPointerException | ClassNotFoundException exception) {
                future.complete(exception.getMessage());
            }
        });

        return future;
    }

    /**
     * Restores any previously saved data on the database, this doesn't
     * connect to the database.
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void pullData() {
        try {
            if (!saveFile.exists()) saveFile.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));

            String serialized = reader.lines().collect(Collectors.joining("\n"));
            this.details = ConnectionDetails.deserialize(serialized);
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Saves the current connection data for the database in a file.
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void pushData() {
        try {
            if (this.details == null) return;
            if (!saveFile.exists()) saveFile.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(this.details.serialize());
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the database connection
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    public void connect() {
        try {
            if (this.details == null) return;
            this.connection = this.details.toConnection();

            if (isConnected()) {
                if (!isSetup()) runSetup();

                this.users = new UserQuery(this.connection);
                this.sessions = new TimeQuery(this.connection);
                this.wages = new WageQuery(this.connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the existing database connection
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    public void disconnect() {
        try {
            if (this.connection == null) return;
            this.connection.close();
            this.connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if the database is currently initialized and connected
     * @return Whether the database is connected
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Checks if the database connected is currently setup
     * @return Whether the database is set up, else true if not connected
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    private boolean isSetup() {
        if (!isConnected()) return true;

        String sql = Clockwise.getResourceAsString("validate.sql");
        if (sql == null) return false;

        String[] queries = sql.split("\n");
        for (String query : queries) {
            try {
                ResultSet result = this.connection.prepareStatement(query).executeQuery();
                if (!result.next()) return false;
            } catch (SQLException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Run the setup for the database
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    private void runSetup() {
        try {
            String[] sql = Clockwise.getResourceAsString("database.sql").split(";");
            for (String query : sql) {
                if (query.trim().isEmpty()) continue;
                this.connection.prepareStatement(query).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionDetails getDetails() {
        return details;
    }

    public void setDetails(ConnectionDetails details) {
        this.details = details;
    }

}
