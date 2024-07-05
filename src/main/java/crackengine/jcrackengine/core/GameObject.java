package crackengine.jcrackengine.core;

import crackengine.jcrackengine.core.components.Component;
import crackengine.jcrackengine.math.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of all objects in your game<br/>
 * This class contains bare minimum to put your object in game world and in camera to be rendered
 */
public abstract class GameObject {
    public Coordinate position = new Coordinate(0,0); /*set default value, so no one will have problems forgetting about it*/
    public Coordinate lastOnCameraPosition = new Coordinate(0,0);
    protected double width=1, height=1;
    protected double angleDeg=0;
    protected int zIndex=0;

    protected List<Component> components = new ArrayList<Component>();

    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public int getZIndex(){return zIndex;}
    public void setZIndex(int zIndex){this.zIndex = zIndex;}
    public double getRotationDeg(){return angleDeg;}
    public void setRotationDeg(double angleDeg){this.angleDeg = angleDeg;}

    public void addComponent(Component component){
        component.bind(this);
        components.add(component);
    }

    public <T extends Component> T getComponent(Class<T> type){
        for(Component c : components)
            if(type.isInstance(c)) return type.cast(c);
        return null;
    }

    public <T extends Component> void updateComponent(T newValue){
        var type = newValue.getClass();
        for(int i=0; i<components.size(); i++){
            Component c = components.get(i);
            if(type.isInstance(c)){
                components.set(i, newValue);
                return;
            }
        }
    }

    /*
     * Setup is called when constructor ends constructing Object<br/>
     * Setup should contain things like: loading sprites, animations, setting constants etc...
     */
    protected void Setup(){}
}
