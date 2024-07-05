package crackengine.jcrackengine.core;

import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.drawing.interfaces.Drawable;
import crackengine.jcrackengine.core.interfaces.Updatable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Camera gets objects from the renderer, and draws all objects that are within its rendering bound<br/>
 * Objects are mapped to the rendering canvas relative to the camera position<br/>
 * Camera can be attached to any object, and its position will update with attached object position</br>
 * This can be used to attach camera to the player, then camera will "walk" with player<br/>
 * Offset can be applied to properly center camera
 */
public class Camera extends GameObject implements Updatable, Drawable {
    private GameObject attachedEntity = null;
    private WorldRenderer renderer = null;
    private Rectangle2D bound = null;
    private int bufferWidth =0, bufferHeight = 0; /*buffer width and height are used to render additional content behind the camera, to remove "flickering" effect*/
    private int offsetX=0, offsetY=0; /*offset to the entity, offset is used, to properly put camera in the center of the screen*/

    /*TODO: implement double and triple buffering
    *       Properly implement rendering buffer (idea: image should be drawn on separate canvas, and then screen area should be cut from it and rendered
    *       Rendering buffer does not work properly, when camera has offset on screen, meaning that if we try to implement split screen it will not work as expected
    * */


    public Camera(){
        position = new Coordinate(0,0);
    }
    public Camera attach(GameObject entity){
        attachedEntity=entity;
        return this;
    }

    /**
     * Attaches given renderer to the camera
     * @param renderer renderer from which camera will take objects to draw
     */
    void attachRenderer(WorldRenderer renderer){
        this.renderer=renderer;
    }

    /**
     * Camera bound is area of the screen which will be used for drawing<br/>
     * Width and height determine width and height of drawing area<br/>
     * You can also set (x,y) to move camera rendering area on the screen
     * @param bound camera bound
     * @return reference to this camera
     */
    public Camera setBound(Rectangle2D bound){
        this.bound=bound;
        return this;
    }

    /**
     * Sets the offset relative to the attached entity position
     * @param offset camera offset
     * @return reference to the actual object
     */
    public Camera setOffset(Coordinate offset){
        offsetX = (int) offset.x;
        offsetY = (int) offset.y;

        return this;
    }


    /**
     * Rendering buffer is used to draw additional content "behind the camera"<br/>
     * Example: setting buffer to (100,100) means that additional 100 pixels width and height will be drawn behind the camera view<br/>
     * Rendering buffer is evenly distributed between left, right, up and down side of rendering area<br/>
     * By default this buffer is set o (0,0) meaning that no additional objects will be rendered behind the camera<br
     * @param bufferSize size of the buffer
     * @return reference to the camera
     */
    @Deprecated
    public Camera setRenderingBuffer(Coordinate bufferSize){
        bufferWidth = (int) bufferSize.x;
        bufferHeight = (int) bufferSize.y;
        return this;
    }

    private void drawIfInBound(GameObject object, GraphicsContext g, Rectangle2D renderingBound){
        if(!(object instanceof Drawable)) return;
        Rectangle2D objectBound = new Rectangle2D(object.position.x, object.position.y,object.getWidth(),object.getHeight());
        if(renderingBound.intersects(objectBound)){
            int screenX = (int) (object.position.x - this.position.x + bound.getWidth()/2 + bound.getMinX());
            int screenY = (int) (object.position.y - this.position.y + bound.getHeight()/2 + bound.getMinY());
            object.lastOnCameraPosition = new Coordinate(screenX, screenY); /*set object position for drawing*/
            ((Drawable) object).draw(g);
        }
    }

    private void drawUI(GameObject object, GraphicsContext g){
        //calculate on screen position, shift it by camera bound position
        int screenX = (int) (object.position.x + bound.getMinX());
        int screenY = (int) (object.position.y + bound.getMinY());
        object.lastOnCameraPosition = new Coordinate(screenX, screenY);

        ((Drawable) object).draw(g);
    }

    @Override
    public void draw(GraphicsContext g) {
        Coordinate topLeftBound = new Coordinate((long) (position.x - bound.getWidth()/2),
                                 (long) (position.y - (bound.getHeight() / 2)));
        Rectangle2D renderingBound = new Rectangle2D(topLeftBound.x, topLeftBound.y, bound.getWidth(), bound.getHeight());

        for(var layer : renderer.BackgroundObjects.values())
            for(Drawable object : layer)
                drawIfInBound((GameObject) object,g,renderingBound);
        
        for(Drawable object : renderer.WorldObjects)
            drawIfInBound((GameObject) object,g,renderingBound);
        for(Drawable object : renderer.UIObjects)
            drawUI((GameObject) object,g);
    }

    @Override
    public void update() {
        //this updates camera position relative to the attached entity position
        if(renderer==null) //if renderer is not set, then entity has to be updated manually, otherwise it will not contain its actual position
            ((Updatable)attachedEntity).update();
        if(attachedEntity != null) {
            position.x = attachedEntity.position.x + offsetX;
            position.y = attachedEntity.position.y + offsetY;
        }
    }
}
