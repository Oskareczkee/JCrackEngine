package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.math.Coordinate;

/**
 * Base of all objects in your game<br/>
 * This class contains bare minimum to put your object in game world and in camera to be rendered
 */
public abstract class GameObject {
    public Coordinate position = new Coordinate(0,0); /*set default value, so no one will have problems forgetting about it*/
    public Coordinate lastOnCameraPosition = new Coordinate(0,0);
    protected double width, height;
    public double getWidth(){return 1;}
    public double getHeight(){return 1;}
}
