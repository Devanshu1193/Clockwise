package ca.myscc.clockwise.database.queries;

import ca.myscc.clockwise.database.dao.UserDAO;
import ca.myscc.clockwise.database.pojo.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Handles communication with the database to read/write data relating
 * to Users
 * @date Nov. 7, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class UserQuery implements UserDAO {

    private final Connection connection;

    /**
     * @param connection The database connection to use
     * @date Nov. 7, 2023
     * @author Santio Yousif
     */
    public UserQuery(Connection connection) {
        this.connection = connection;
    }

    /**
     * Converts a query to a hashed byte array using SHA-256
     * @param query The string to hash
     * @return A completable future of a hashed byte array, or an unhashed byte array if SHA-256 isn't available
     * @date Nov. 7, 2023
     * @author Santio Yousif
     */
    private CompletableFuture<byte[]> hash(String query) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        byte[] queryBytes = query.getBytes(StandardCharsets.UTF_8);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {

                future.complete(MessageDigest.getInstance("SHA-256")
                    .digest(queryBytes));

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();

                // Return unhashed byte array on failure
                future.complete(queryBytes);
            }
        });

        return future;
    }

    @Override
    public User login(String username, String password) {
        try {
            String hashedPassword = bytesToHex(hash(password).get());
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1
                """);

            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            ResultSet results = statement.executeQuery();
            if (!results.next()) return null;

            return new User(
                results.getInt("id"),
                results.getString("first_name"),
                results.getString("last_name"),
                results.getString("username"),
                results.getString("password")
            );
        } catch (SQLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean exists(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
            SELECT COUNT(username) FROM users WHERE username = ? LIMIT 1
            """);

            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            if (!results.next()) return false;

            return results.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public void create(String firstName, String lastName, String username, String password) {
        hash(password).thenAccept((hashedPassword) -> {
            try {
                PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO users(first_name, last_name, username, password) VALUES (?,?,?,?);
                """);

                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, username);
                statement.setString(4, bytesToHex(hashedPassword));

                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Converts a binary array to a hexadecimal string
     * @param bytes The byte array to convert
     * @return A hexadecimal array
     */
    @SuppressWarnings("StringConcatenationInLoop")
    private static String bytesToHex(byte[] bytes) {
        String hex = "";

        for (byte aByte : bytes) {
            String hexString = Integer.toHexString(0xff & aByte);
            if (hexString.length() == 1) hex += "0"; // Ensure properly length (ex: "0A")
            hex += hexString;
        }

        return hex;
    }

}
