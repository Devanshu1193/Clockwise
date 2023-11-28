package ca.myscc.clockwise.database.queries;

import ca.myscc.clockwise.database.dao.WageDAO;
import ca.myscc.clockwise.database.pojo.Wage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles communication with the database to read/write data relating
 * to Wages
 * @date Nov. 28, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class WageQuery implements WageDAO {

    private final Connection connection;

    /**
     * @param connection The database connection to use
     * @date Nov. 28, 2023
     * @author Santio Yousif
     */
    public WageQuery(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Wage getWage(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
            SELECT * FROM wages WHERE user_id = ? LIMIT 1
            """);

            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return new Wage(
                    results.getDouble("wage")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setWage(int userId, double wage) {
        // Convert the wage down to the cent and lose any other precision
        String formatted = String.format("%4.2f", wage);
        double newWage = Double.parseDouble(formatted);

        // Save it in the database
        try {
            PreparedStatement statement = connection.prepareStatement("""
            REPLACE INTO wages(user_id, wage) VALUES(?, ?)
            """);

            statement.setInt(1, userId);
            statement.setDouble(2, newWage);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
