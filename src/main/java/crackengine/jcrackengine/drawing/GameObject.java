package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.math.Coordinate;

/**
 * Base of all objects in your game<br/>
 * This class contains bare minimum to put your object in game world and in camera to be rendered
 */
public abstract class GameObject {
    public Coordinate position = new Coordinate(0,0); /*set default value, so no one will have problems forgetting about it*/
    public Coordinate lastOnCameraPosition = new Coordinate(0,0);
    protected double width=1, height=1;
    protected int zIndex=0;

    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public int getZIndex(){return zIndex;}
    public void setZIndex(int zIndex){this.zIndex = zIndex;}
}
