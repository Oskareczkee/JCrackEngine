package crackengine.jcrackengine.drawing.interfaces;

import crackengine.jcrackengine.drawing.collision.Collider;

public interface StaticCollidable {

    /**
     * Trigerred when some object collided with this object (reaction to collision)
     * @param object object with which collision occurred
     */
    default void onCollided(StaticCollidable object) {}

    Collider getCollider();
}
