package ca.myscc.clockwise.database.dao;

/**
 * @date Nov. 14, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public interface TimeDAO {

    /**
     * Save a timed session to the database
     * @param userId The user associated with the session
     * @param timeStarted The unix timestamp of when the session started
     * @param timeEnded The unix timestamp of when the session ended
     */
    void track(int userId, long timeStarted, long timeEnded);

    /**
     * Checks if a user already has a certain username
     * @param username The username to check
     * @return Whether a user is already associated with the name
     */
    List<Sessions> exists(String username);

}
