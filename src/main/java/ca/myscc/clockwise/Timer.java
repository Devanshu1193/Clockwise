package ca.myscc.clockwise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Acts as a stopwatch, tracking the amount of elapsed seconds
 * @date Nov. 28, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class Timer {

    private long seconds = 0;
    private ScheduledFuture<?> future = null;
    private final List<Runnable> observers = new ArrayList<>();

    private int getMinutes() {
        return (int) Math.floor(this.seconds / 60.0) % 60;
    }

    private int getHours() {
        return (int) Math.floor(this.getMinutes() / 60.0);
    }

    private int getModSeconds() {
        return (int) (this.seconds % 60);
    }

    public String getTime() {
        return String.format("%2d:%2d:%2d", this.getHours(), this.getMinutes(), this.getModSeconds())
            .replace(" ", "0");
    }

    public void start() {
        if (this.future != null) return;
        this.future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            this.seconds++;
            this.sendUpdate();
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void pause() {
        this.future.cancel(false);
        this.future = null;
    }

    public void reset() {
        this.pause();
        this.seconds = 0;
        this.sendUpdate();
    }

    public boolean isRunning() {
        return this.future != null;
    }

    private void sendUpdate() {
        for (Runnable observer : observers) {
            observer.run();
        }
    }

    public void listen(Runnable callback) {
        this.observers.add(callback);
    }

    public long getSeconds() {
        return seconds;
    }
}
