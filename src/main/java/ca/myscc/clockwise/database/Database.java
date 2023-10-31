package ca.myscc.clockwise.database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
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
        if (this.details == null) return;
        this.connection = this.details.toConnection();

        if (isConnected()) runSetup();
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
     * Run the setup for the database
     * @author Santio Yousif
     * @date Oct. 31, 2023
     */
    private void runSetup() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("database.sql");
            if (stream == null) throw new IllegalStateException("Failed to find the setup script");

            String sql = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            this.connection.prepareStatement(sql).execute();

            stream.close();
        } catch (IOException | SQLException e) {
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
