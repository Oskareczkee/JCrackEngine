package crackengine.jcrackengine.core;

import crackengine.jcrackengine.drawing.Drawable;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.drawing.Updatable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderer implements Updatable, Drawable {
    List<GameObject> WorldObjects = new ArrayList<GameObject>();
    List<GameObject> BackgroundObjects = new ArrayList<>();
    private Camera RenderingCamera = null;
    /*TODO add list of updateable and drawable lists
    * TODO Add support for multiple layers, for now only simple background layer for level rendering is supported
    * TODO Consider using HashSet for storing objects
    */


    public void addObject (GameObject object) {
        WorldObjects.add(object);
    }

    public void removeObject (GameObject object) {
        WorldObjects.remove(object);
    }

    public void addBackgroundObject(GameObject object){
        BackgroundObjects.add(object);
    }

    public void removeBackgroundObject(GameObject object){
        BackgroundObjects.remove(object);
    }

    public void addCamera(Camera camera){
        RenderingCamera = camera;
        RenderingCamera.attachRenderer(this);
    }

    @Override
    public void earlyUpdate() {
        for (GameObject worldObject : WorldObjects) {
            if(worldObject instanceof Updatable)
                ((Updatable)worldObject).earlyUpdate();
        }
    }

    @Override
    public void update() {
        for (GameObject worldObject : WorldObjects) {
            if(worldObject instanceof Updatable)
                ((Updatable)worldObject).update();
        }

        if(RenderingCamera!=null)
            RenderingCamera.update();
    }

    @Override
    public void lateUpdate() {
        for (GameObject worldObject : WorldObjects) {
            if(worldObject instanceof Updatable)
                ((Updatable)worldObject).lateUpdate();
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        if (RenderingCamera != null)
            RenderingCamera.draw(g);
    }
}
