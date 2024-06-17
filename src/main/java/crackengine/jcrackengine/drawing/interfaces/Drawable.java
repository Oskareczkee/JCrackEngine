package crackengine.jcrackengine.drawing.interfaces;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represents object that can be drawn on the screen
 */
public interface Drawable {

    /**
     * Draws given object using provided graphics context
     * @param g graphics context -> screen/canvas where object should be drawn
     */
    default void draw(GraphicsContext g) {}
}
