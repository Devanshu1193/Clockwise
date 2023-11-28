package ca.myscc.clockwise.database.pojo;

import java.time.Duration;

/**
 * @date Nov. 21, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class  Session {

    private int user;
    private long timeStarted;
    private long timeEnded;

    /**
     * @param user The user id associated with the session
     * @param timeStarted The time the session started
     * @param timeEnded The time the session ended
     * @date Nov. 21, 2023
     * @author Santio Yousif
     */
    public Session(int user, long timeStarted, long timeEnded) {
        this.user = user;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
    }

    public String asFormatted() {
        Duration duration = Duration.ofSeconds(this.timeEnded - this.timeStarted);
        return String.format("%2d:%2d:%2d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart())
            .replace(" ", "0");
    }

    public int getUser() {
        return user;
    }

    public Session setUser(int user) {
        this.user = user;
        return this;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public Session setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
        return this;
    }

    public long getTimeEnded() {
        return timeEnded;
    }

    public Session setTimeEnded(long timeEnded) {
        this.timeEnded = timeEnded;
        return this;
    }

    @Override
    public String toString() {
        return "Session{" +
            "user=" + user +
            ", timeStarted=" + timeStarted +
            ", timeEnded=" + timeEnded +
            '}';
    }
}
