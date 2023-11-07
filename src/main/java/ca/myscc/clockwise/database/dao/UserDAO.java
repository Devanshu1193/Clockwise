package ca.myscc.clockwise.database.dao;

import ca.myscc.clockwise.database.pojo.User;

/**
 * @date Nov. 7, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public interface UserDAO {

    /**
     * Get a user from their username and password
     * @param username The username of the user
     * @param password The password of the user
     * @return A user, or null if no user was found.
     */
    User login(String username, String password);

    /**
     * Checks if a user already has a certain username
     * @param username The username to check
     * @return Whether a user is already associated with the name
     */
    boolean exists(String username);

    /**
     * Creates a new user account
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param username The username to use
     * @param password The associated password for the account
     */
    void create(String firstName, String lastName, String username, String password);

}
