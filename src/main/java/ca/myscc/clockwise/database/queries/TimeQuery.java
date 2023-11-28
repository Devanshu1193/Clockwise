package ca.myscc.clockwise.database.queries;

import ca.myscc.clockwise.database.dao.TimeDAO;
import ca.myscc.clockwise.database.pojo.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles communication with the database to read/write data relating
 * to Time Sessions
 * @date Nov. 21, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class TimeQuery implements TimeDAO {

    private final Connection connection;

    /**
     * @param connection The database connection to use
     * @date Nov. 21, 2023
     * @author Santio Yousif
     */
    public TimeQuery(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void track(Session session) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
            INSERT INTO times(user_id, time_started, time_stopped) VALUES(?, ?, ?)
            """);

            statement.setInt(1, session.getUser());
            statement.setLong(2, session.getTimeStarted());
            statement.setLong(3, session.getTimeEnded());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Session> getSessions(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
            SELECT * FROM times WHERE user_id = ?
            """);

            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();
            ArrayList<Session> sessions = new ArrayList<>();

            while (results.next()) {
                sessions.add(new Session(
                    results.getInt("user_id"),
                    results.getLong("time_started"),
                    results.getLong("time_stopped")
                ));
            }

            return sessions;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
