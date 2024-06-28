package crackengine.jcrackengine.physics.interfaces;

public interface DynamicCollidable extends StaticCollidable {
    /**
     * Trigerred when object collides with another object
     * @param object object with which collision occurred
     */
    default void onCollision(StaticCollidable object) {}
}
