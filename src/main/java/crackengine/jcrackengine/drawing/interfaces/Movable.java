package crackengine.jcrackengine.drawing.interfaces;

import crackengine.jcrackengine.math.Vector2D;

/**
 * Represents object that can move e.g is not static
 */
public interface Movable {
    Vector2D getMovementVector();
    void setMovementVector(Vector2D vector);
    void setMovementX(double x);
    void setMovementY(double y);

    /**
     * Changes position of object, depending on its movement vector
     */
    void move();
}
