package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.drawing.interfaces.Collidable;
import crackengine.jcrackengine.drawing.interfaces.Drawable;
import crackengine.jcrackengine.drawing.interfaces.Movable;
import crackengine.jcrackengine.drawing.interfaces.Updatable;
import crackengine.jcrackengine.drawing.sprite.AnimationRunner;
import crackengine.jcrackengine.drawing.sprite.SpriteAnimation;
import crackengine.jcrackengine.drawing.sprite.SpriteLoader;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2D;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Entity represents everything that can be drawn, updated and has ability to move and collide<br/>
 * Entity has its animation system, that allows adding animation to it
 */
public abstract class Entity extends GameObject implements Drawable, Updatable, Movable, Collidable {
    /**
     * Actual idle sprite. Idle sprites are set, when animation ends<br/>
     */
    public Image idleSprite=null;
    /**
     * Actual sprite to be drawn, changing it is not recommended<br/>
     * Used by AnimationRunner to change actual sprite to draw
     */
    public Image sprite=null;
    protected final HashMap<String, Image> sprites=new HashMap<>();
    protected final HashMap<String, SpriteAnimation> animations = new HashMap<>();
    private final AnimationRunner animationRunner;
    protected Vector2D movement = new Vector2D();

    public Entity(Coordinate position){
        this.position = position;
        animationRunner = new AnimationRunner(this);
        Setup();
    }

    /**
     * @param SpriteName Name of this sprite, how it will be kept in sprites map
     * @param SpritePath relative path in resources, where sprite can be found
     */
    public void LoadSprite(String SpriteName, String SpritePath){
        sprites.put(SpriteName, new Image(SpritePath));
    }

    /**
     * @param SpriteName Name of sprite to be set, sprites are kept in sprites map, sprite name is the key
     */
    public void SetIdleSprite(String SpriteName){
        idleSprite = sprites.get(SpriteName);
    }

    /**
     * @param AnimationName To be added
     */
    public void SetIdleAnimation(String AnimationName){
        /*TODO add this functionality later*/
    }

    /**
     * Loads animation from given file. File is standard png file, with multiple sprites on one image
     * @param animationName Name of this animation, how it will be kept in animation map
     * @param AnimationPath relative path in resources, where animation can be found
     * @param rows how many rows of sprites file has
     * @param cols how many columns of sprites file has
     */
    public void LoadAnimation(String animationName, String AnimationPath, int rows, int cols){
        var animation = new SpriteLoader().withSpriteSheet(AnimationPath)
                .withRows(rows)
                .withCols(cols)
                .loadAnimation();

        animations.put(animationName, animation);
    }

    /**
     * Loads animation from file using custom SpriteLoader
     * @param animationName Name of this animation, how it will be kept in animation map
     * @param loader Custom sprite loader
     */
    public void LoadAnimation(String animationName, SpriteLoader loader){
        var  animation = loader.loadAnimation();
        animations.put(animationName, animation);
    }

    /**
     * Fires animation with proper speed<br/>
     * Animation will not be fired if requested animation is already playing
     * @param animationName name of animation to be drawn
     * @param frameLength length in frames between changes of animation frame
     */
    public void FireAnimation(String animationName, int frameLength){
            var animation = animations.get(animationName);
            if(animation == null)   return;

            if(animationRunner.isPlaying() && animationRunner.getAnimationName().equals(animationName))
                return; /*do not change animation, if animation is playing, and requested is the same animation*/

            animationRunner.setAnimationName(animationName);
            animationRunner.changeAnimation(animation, frameLength);
    }


    /**
     * Fires animation with proper speed<br/>
     * Animation is fired regardless if requested animation is already playing or not
     * @param animationName name of animation to be drawn
     * @param frameLength length in frames between changes of animation frame
     */
    public void ForceFireAnimation(String animationName, int frameLength){
        var animation = animations.get(animationName);
        if(animation == null)   return;

        animationRunner.setAnimationName(animationName);
        animationRunner.changeAnimation(animation, frameLength);
    }

    /**
     * @param animationName name of animation
     * @return true if animation has been added
     */
    public boolean HasAnimation(String animationName){
        return animations.containsKey(animationName);
    }

    /**
     * @param animationName name of animation
     * @return animation with given name, or null if it does not exist
     */
    public SpriteAnimation GetAnimation(String animationName){
        return animations.get(animationName);
    }

    /**
     * Setup is called when constructor ends constructing Entity<br/>
     * Setup should contain things like: loading sprites, animations, setting constants etc...
     */
    public void Setup(){}

    @Override
    public void onCollision(Collidable object) {
        movement.setOpposite(); /*move in opposite direction*/
        move();
        movement.setOpposite(); /*restore previous movement vector*/
    }

    @Override
    public void move(){
        position.x+= (long) movement.x;
        position.y+= (long) movement.y;
    }

    @Override
    public Vector2D getMovementVector(){
        return movement;
    }

    @Override
    public void setMovementVector(Vector2D movement){
        this.movement = movement;
    }

    @Override
    public void setMovementX(double x){
        this.movement.x = x;
    }

    @Override
    public void setMovementY(double y){
        this.movement.y = y;
    }

    @Override
    public double getWidth() {
        if(sprite==null) return width;
        return sprite.getWidth();
    }

    @Override
    public double getHeight() {
        if(sprite==null) return height;
        return sprite.getHeight();
    }

    @Override
    public void update()
    {
        this.animationRunner.update();
    }
}
