package ca.myscc.clockwise.scenes;

import ca.myscc.clockwise.Clockwise;
import ca.myscc.clockwise.Constants;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A base class for scenes so that all scenes we create will have a similar
 * style applied to all. This class also provides useful methods such as
 * {@link BaseScene#open()} which switches over to the scene.
 * @author Santio Yousif
 * @date Oct. 24, 2023
 * @version 1.0
 */
public abstract class BaseScene extends Scene {

    /**
     * Constructor for BaseScene, this defaults the sizing of the
     * scene along with the coloring.
     * @author Santio Yousif
     * @date Oct. 24, 2023
     */
    public BaseScene() {
        super(new StackPane(), 1024, 768);
        StackPane root = (StackPane) super.getRoot();
        root.setBackground(Constants.BACKGROUND);

        root.getChildren().add(start());
    }


    /**
     * Gets the main stage for the project and changes the scene on it
     * over to this current scene object.
     * @author Santio Yousif
     * @date Oct. 24, 2023
     */
    public void open() {
        Clockwise.getStage().setScene(this);
    }
    /**
     * This is called immediately after this scene has been constructed and setup.
     * You should place your code here as it provides you the root border pane
     * and prevents you from overwriting the default behaviour.
     * @author Santio Yousif
     * @date Oct. 24, 2023
     */
    abstract Pane start();
}
