package crackengine.jcrackengine.core;

import crackengine.jcrackengine.drawing.interfaces.Drawable;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.drawing.interfaces.Updatable;
import crackengine.jcrackengine.drawing.interfaces.Collidable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * World renderer manages every object in the game world<br/>
 * Objects can be added to it to be drawn, updated and collided <br/>
 * Renderer requires Camera to work properly <br/>
 * This description will get updated, as this class is not completed yet
 */
public class WorldRenderer implements Updatable, Drawable {
    
    List<Drawable> WorldObjects = new ArrayList<>();
    List<Drawable> BackgroundObjects = new ArrayList<>();
    List<Drawable> UIObjects = new ArrayList<>();
    List<Updatable> UpdatableObjects = new ArrayList<>();
    List<Collidable> CollidableObjects = new ArrayList<>();

    private Camera RenderingCamera = null;
    /*TODO add list of updateable and drawable lists
    * TODO Add support for multiple layers, for now only simple background layer for level rendering is supported
    * TODO Consider using HashSet for storing objects
    */


    public void addObject (GameObject object) {
        if(object instanceof Drawable)
            WorldObjects.add((Drawable) object);
        if(object instanceof Collidable)
            CollidableObjects.add((Collidable) object);
        if(object instanceof Updatable)
            UpdatableObjects.add((Updatable) object);
    }

    public void removeObject (GameObject object) {
        if(!(object instanceof Drawable)) return;
        WorldObjects.remove((Drawable)object);
    }

    public void addBackgroundObject(GameObject object){
        if(object instanceof Drawable)
            BackgroundObjects.add((Drawable) object);
    }

    public void removeBackgroundObject(GameObject object){
        if(!(object instanceof Drawable)) return;
        BackgroundObjects.remove((Drawable)object);
    }

    public void addUIObject(GameObject object){
        if(object instanceof Drawable)
            UIObjects.add((Drawable) object);
    }

    public void removeUIObject(GameObject object){
        if(!(object instanceof Drawable)) return;
        UIObjects.remove((Drawable)object);
    }

    public void addCamera(Camera camera){
        RenderingCamera = camera;
        RenderingCamera.attachRenderer(this);
    }

    @Override
    public void earlyUpdate() {
        for ( Updatable object : UpdatableObjects)
            object.earlyUpdate();
    }

    @Override
    public void update() {
        //check collision
        for (Collidable object : CollidableObjects) {
                for(Collidable obj : CollidableObjects){
                        if(object!=obj && object.getCollider().collides(obj.getCollider())){ //object!=obj prevents object colliding with itself
                            object.onCollision(obj); //action
                            obj.onCollided(object);  //reaction
                        }
                    }
            }

        //update everything
        for(Updatable object : UpdatableObjects)
            object.update();

        //update camera if set
        if(RenderingCamera!=null)
            RenderingCamera.update();
    }

    @Override
    public void lateUpdate() {
        for (Updatable object : UpdatableObjects)
                object.lateUpdate();
    }

    @Override
    public void draw(GraphicsContext g) {
        if (RenderingCamera != null)
            RenderingCamera.draw(g);
    }
}
