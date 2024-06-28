package crackengine.jcrackengine.physics.interfaces;

import crackengine.jcrackengine.math.Vector2F;

/**
 * Represents object that can move e.g is not static
 */
public interface Movable {
    Vector2F getMovementVector();
    void setMovementVector(Vector2F vector);
    void setMovementX(double x);
    void setMovementY(double y);

    /**
     * Changes position of object, depending on its movement vector
     */
    void move();
}
