package ca.myscc.clockwise.database.dao;

import ca.myscc.clockwise.database.pojo.Wage;

/**
 * @date Nov. 28, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public interface WageDAO {

    /**
     * Get a wage for a user
     * @param userId The id of the user
     * @return The user's wage, or null if not found
     */
    Wage getWage(int userId);

    /**
     * Sets the wage of the user to the specified value
     * @param userId The id of the user
     * @param wage The wage to set the user at
     */
    void setWage(int userId, double wage);

}
