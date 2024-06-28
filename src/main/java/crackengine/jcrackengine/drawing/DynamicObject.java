package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.core.interfaces.Updatable;
import crackengine.jcrackengine.math.Coordinate;

public abstract class DynamicObject extends StaticObject implements Updatable {
    public DynamicObject(Coordinate position) {
        super(position);
    }

    public DynamicObject(Coordinate position, String spritePath) {
        super(position, spritePath);
    }
}
