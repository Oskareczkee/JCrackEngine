package crackengine.jcrackengine.drawing.interfaces;

/**
 * Represents object, that updates its state each frame
 * <p>
 *     There are 3 stages of update:
 *     <ul>
 *         <li>Early Update -> You can put here everything that should happen before update</li>
 *         <li>Update -> Main update method</li>
 *         <li>Late Update -> You can put here code, that will be executed after object has been updated</li>
 *     </ul>
 * </p>
 */
public interface Updatable {
    default void earlyUpdate() {}
    default void update() {}
    default void lateUpdate() {}
}
