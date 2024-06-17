package crackengine.jcrackengine.drawing.interfaces;

import crackengine.jcrackengine.drawing.collision.Collider;

public interface Collidable {
    /**
     * Trigerred when object collides with another object
     * @param object object with which collision occurred
     */
    default void onCollision(Collidable object) {}

    /**
     * Trigerred when some object collided with this object (reaction to collision)
     * @param object object with which collision occurred
     */
    default void onCollided(Collidable object) {}

    Collider getCollider();
}
