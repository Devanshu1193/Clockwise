package ca.myscc.clockwise.database.pojo;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * A pojo representation of a user's wage in the database.
 * @date Nov. 28, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class Wage {
    private final double wage;

    /**
     * @param wage The wage of the user
     */
    public Wage(double wage) {
        this.wage = wage;
    }

    double getWage() {
        return this.wage;
    }

    double calculate(Duration duration) {
        return this.wage * duration.get(ChronoUnit.HOURS);
    }

    String getFormatted() {
        return String.format("$%.2f/hr", wage);
    }
}
