package ca.myscc.clockwise.database.queries;

import ca.myscc.clockwise.database.dao.TimeDAO;
import ca.myscc.clockwise.database.pojo.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

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
            SELECT * FROM times WHERE user_id = ? ORDER BY time_started DESC
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
    
    @Override
    public Long getTotalTimeAtDay(int userId, int daysAgo) {
        try {
            long now = Duration.ofDays(Duration.ofMillis(System.currentTimeMillis()).toDays()).toSeconds();
            long then = now - Duration.ofDays(daysAgo).toSeconds();
            
            PreparedStatement statement = connection.prepareStatement("""
            SELECT * FROM times WHERE user_id = ? AND time_started > ? AND time_started < ?
            """);

            statement.setInt(1, userId);
            statement.setLong(2, then);
            statement.setLong(3, then + Duration.ofDays(1).toSeconds());
            
            ResultSet results = statement.executeQuery();
            long total = 0L;
            
            while (results.next()) {
                total += results.getLong("time_stopped") - results.getLong("time_started");
            }
            
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
