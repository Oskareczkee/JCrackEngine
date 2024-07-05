package crackengine.jcrackengine.physics.interfaces;

import crackengine.jcrackengine.physics.collision.Collider;

public interface StaticCollidable {

    /**
     * Trigerred when some object collided with this object (reaction to collision)
     * @param object object with which collision occurred
     */
    void onCollided(Collider collider);

    Collider getCollider();
}
