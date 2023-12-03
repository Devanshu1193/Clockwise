package ca.myscc.clockwise.database.animations;

import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.util.Duration;

/**
 * A base transition class which contains code stolen from {@link javafx.animation.ScaleTransition}
 * @date Dec. 3, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public abstract class BaseTransition extends Transition {
    
    /**
     * The duration of this {@code ScaleTransition}.
     * <p>
     * It is not possible to change the {@code duration} of a running
     * {@code ScaleTransition}. If the value of {@code duration} is changed for
     * a running {@code ScaleTransition}, the animation has to be stopped and
     * started again to pick up the new value.
     * <p>
     * Note: While the unit of {@code duration} is a millisecond, the
     * granularity depends on the underlying operating system and will in
     * general be larger. For example animations on desktop systems usually run
     * with a maximum of 60fps which gives a granularity of ~17 ms.
     *
     * Setting duration to value lower than {@link Duration#ZERO} will result
     * in {@link IllegalArgumentException}.
     *
     * @defaultValue 400ms
     */
    private ObjectProperty<Duration> duration;
    private static final Duration DEFAULT_DURATION = Duration.millis(400);
    
    public final void setDuration(Duration value) {
        this.setCycleDuration(value);
        if ((duration != null) || (!DEFAULT_DURATION.equals(value))) {
            durationProperty().set(value);
        }
    }
    
    public final Duration getDuration() {
        return (duration == null)? DEFAULT_DURATION : duration.get();
    }
    
    public final ObjectProperty<Duration> durationProperty() {
        if (duration == null) {
            duration = new ObjectPropertyBase<Duration>(DEFAULT_DURATION) {
                
                @Override
                public void invalidated() {
                    try {
                        setCycleDuration(getDuration());
                    } catch (IllegalArgumentException e) {
                        if (isBound()) {
                            unbind();
                        }
                        set(getCycleDuration());
                        throw e;
                    }
                }
                
                @Override
                public Object getBean() {
                    return BaseTransition.this;
                }
                
                @Override
                public String getName() {
                    return "duration";
                }
            };
        }
        return duration;
    }
    
}
