package ca.myscc.clockwise.database.dao;

import ca.myscc.clockwise.database.pojo.Session;

import java.util.List;

/**
 * @date Nov. 14, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public interface TimeDAO {

    /**
     * Save a timed session to the database
     * @param session The session to save
     * @author Santio Yousif
     * @date Nov. 14, 2023
     */
    void track(Session session);

    /**
     * Gets all sessions associated with a user id
     * @param userId The user id to check
     * @return A list of all sessions belonging to the user
     * @author Santio Yousif
     * @date Nov. 21, 2023
     */
    List<Session> getSessions(int userId);
    
    /**
     * Gets the total time tracked by a user at a specific day
     * @param userId The user id to check
     * @param daysAgo The amount of days ago to check
     * @return A long representing the total time tracked in seconds
     * @author Santio Yousif
     * @date Dec. 3, 2023
     */
    Long getTotalTimeAtDay(int userId, int daysAgo);

}
