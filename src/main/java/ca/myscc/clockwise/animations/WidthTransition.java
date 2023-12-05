package ca.myscc.clockwise.animations;

import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom transition that animates the width of a node from one value to another
 * @date Dec. 3, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class WidthTransition extends BaseTransition {
    
    private double startWidth;
    private double endWidth;
    private Region node;
    
    private List<Runnable> onFinished = new ArrayList<>();
    private Map<Double, Runnable> atSize = new HashMap<>();
    private double previous = 0;
    
    /**
     * Creates a new width transition
     * @param node The node to animate
     * @param startWidth The starting width
     * @param endWidth The ending width
     */
    public WidthTransition(Region node, double startWidth, double endWidth) {
        this.node = node;
        this.startWidth = startWidth;
        this.endWidth = endWidth;
    }
    
    /**
     * Creates a new width transition
     * @param node The node to animate
     * @param endWidth The ending width
     */
    public WidthTransition(Region node, double endWidth) {
        this(node, node.getMinWidth(), endWidth);
    }
    
    @Override
    protected void interpolate(double frac) {
        double width = (startWidth + frac * (endWidth - startWidth));
        node.setMinWidth(width);
        
        for (Map.Entry<Double, Runnable> entry : atSize.entrySet()) {
            if (previous < entry.getKey() && width >= entry.getKey()) {
                entry.getValue().run();
            }
        }
        
        previous = width;
        if (width == endWidth) onFinished.forEach(Runnable::run);
    }
    
    public void onFinished(Runnable onFinished) {
        this.onFinished.add(onFinished);
    }
    
    public void atSize(double width, Runnable onTime) {
        this.atSize.put(width, onTime);
    }
    
}
