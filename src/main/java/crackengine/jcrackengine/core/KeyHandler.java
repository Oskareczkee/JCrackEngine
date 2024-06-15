package crackengine.jcrackengine.core;

import java.util.HashSet;


/**
 * Key Handler is base class for handling key input.
 * It allows you to check which keys are pressed right now
 */
public class KeyHandler{
    private HashSet<Integer> PressedKeys = new HashSet<>();
    // private HashMap<Integer, Eve>

    public void keyPressed(int code) {
        PressedKeys.add(code);
    }
    public void keyReleased(int code) {
        PressedKeys.remove(code);
    }

    public boolean isKeyPressed(int keyCode) {
        return PressedKeys.contains(keyCode);
    }
}
