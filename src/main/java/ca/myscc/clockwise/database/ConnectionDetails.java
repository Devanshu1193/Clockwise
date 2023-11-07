package ca.myscc.clockwise.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Holds the connection details required to log into
 * the database
 * @date Oct. 31, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class ConnectionDetails {

    private String host;
    private String name;
    private String username;
    private String password;

    /**
     * Create a new store for database credentials
     * @param host The host uri for the database
     * @param name The database name to use
     * @param username The username to connect with
     * @param password The associated password
     */
    public ConnectionDetails(String host, String name, String username, String password) {
        this.host = host;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Serialize the connection details to a string
     * @return The serialized data
     * @apiNote This method returns non-safe data, do not share
     * the returned data
     */
    public String serialize() {
        return this.host + "\n" + this.name + "\n" + this.username + "\n" + this.password;
    }

    /**
     * Deserialize the input and convert it to connection
     * details.
     * @param input The serialized data
     * @return The connection details
     */
    public static ConnectionDetails deserialize(String input) {
        String[] details = input.split("\n");
        if (details.length != 4) return null;

        return new ConnectionDetails(
            details[0],
            details[1],
            details[2],
            details[3]
        );
    }

    /**
     * Generate a database connection from these connection details.
     * This is the only way to use the password property
     * @return A database connection, or null.
     */
    @SuppressWarnings("Java9ReflectionClassVisibility")
    public Connection toConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.name, this.username, this.password);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ConnectionDetails{host=" + this.host + ", name=" + this.name + ", username=" + this.username + "}";
    }
}
