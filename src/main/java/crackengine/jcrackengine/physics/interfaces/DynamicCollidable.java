package crackengine.jcrackengine.physics.interfaces;

import crackengine.jcrackengine.physics.collision.Collider;

public interface DynamicCollidable extends StaticCollidable {
    /**
     * Trigerred when object collides with another object
     * @param object object with which collision occurred
     */
    void onCollision(Collider collider);
}
