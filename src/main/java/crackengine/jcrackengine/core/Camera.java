package crackengine.jcrackengine.core;

import crackengine.jcrackengine.drawing.Coordinate;
import crackengine.jcrackengine.drawing.Drawable;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.drawing.Updatable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Camera extends GameObject implements Updatable, Drawable {
    private GameObject attachedEntity = null;
    private WorldRenderer renderer = null;
    private Rectangle2D bound = null;
    private int offsetX=0, offsetY=0; /*offset to the entity, offset is used, to properly put camera in the center of the screen*/


    public Camera(){
        position = new Coordinate(0,0);
    }
    public Camera attach(GameObject entity){
        attachedEntity=entity;
        return this;
    }
    void attachRenderer(WorldRenderer renderer){
        this.renderer=renderer;
    }

    public Camera setBound(Rectangle2D bound){
        this.bound=bound;
        return this;
    }

    public Camera setOffset(Coordinate offset){
        offsetX = (int) offset.x;
        offsetY = (int) offset.y;

        return this;
    }

    private void drawIfInBound(GameObject object, GraphicsContext g, Rectangle2D renderingBound){
        if(!(object instanceof Drawable)) return;
        Rectangle2D objectBound = new Rectangle2D(object.position.x, object.position.y,object.getWidth(),object.getHeight());
        if(renderingBound.intersects(objectBound)){
            int screenX = (int) (object.position.x - this.position.x + bound.getWidth()/2);
            int screenY = (int) (object.position.y - this.position.y + bound.getHeight()/2);
            object.lastOnCameraPosition = new Coordinate(screenX, screenY); /*set object position for drawing*/
            ((Drawable) object).draw(g);
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        Coordinate topLeftBound = new Coordinate((long) (position.x - bound.getWidth()/2), (long) (position.y - bound.getHeight()/2));
        Rectangle2D renderingBound = new Rectangle2D(topLeftBound.x, topLeftBound.y, bound.getWidth(), bound.getHeight());

        for(GameObject object : renderer.BackgroundObjects)
            drawIfInBound(object,g,renderingBound);
        for(GameObject object : renderer.WorldObjects)
            drawIfInBound(object,g,renderingBound);
    }

    @Override
    public void update() {
        if(attachedEntity != null) {
            position.x = attachedEntity.position.x + offsetX;
            position.y = attachedEntity.position.y + offsetY;
        }
    }

    @Override
    public void earlyUpdate() {}
    @Override
    public void lateUpdate() {}

}
