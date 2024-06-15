package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.drawing.sprite.SpriteAnimation;
import crackengine.jcrackengine.drawing.sprite.SpriteLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;

public abstract class Entity extends GameObject implements Drawable, Updatable {
    public Image idleSprite=null;
    public Image sprite=null;
    protected final HashMap<String, Image> sprites=new HashMap<>();
    protected final HashMap<String, SpriteAnimation> animations = new HashMap<>();
    private AnimationRunner animationRunner = null;

    public Entity(Coordinate position){
        this.position = position;
        animationRunner = new AnimationRunner(this);
        Setup();
    }

    public void LoadSprite(String SpriteName, String SpritePath){
        sprites.put(SpriteName, new Image(SpritePath));
    }

    public void SetIdleSprite(String SpriteName){
        idleSprite = sprites.get(SpriteName);
    }

    public void SetIdleAnimation(String AnimationName){
        /*TODO add this functionality later*/
    }

    public void LoadAnimation(String animationName, String AnimationPath, int rows, int cols){
        var animation = new SpriteLoader().withSpriteSheet(AnimationPath)
                .withRows(rows)
                .withCols(cols)
                .loadAnimation();

        animations.put(animationName, animation);
    }

    public void LoadAnimation(String animationName, SpriteLoader loader){
        var  animation = loader.loadAnimation();
        animations.put(animationName, animation);
    }

    public void FireAnimation(String animationName, int frameLength){
            var animation = animations.get(animationName);
            if(animation == null)   return;

            if(animationRunner.isPlaying() && animationRunner.getAnimationName().equals(animationName))
                return; /*do not change animation, if animation is playing, and requested is the same animation*/

            animationRunner.setAnimationName(animationName);
            animationRunner.changeAnimation(animation, frameLength);
    }

    public void ForceFireAnimation(String animationName, int frameLength){
        var animation = animations.get(animationName);
        if(animation == null)   return;

        animationRunner.setAnimationName(animationName);
        animationRunner.changeAnimation(animation, frameLength);
    }

    public boolean HasAnimation(String animationName){
        return animations.containsKey(animationName);
    }

    public SpriteAnimation GetAnimation(String animationName){
        return animations.get(animationName);
    }

    public void Setup(){}

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
    public void draw(GraphicsContext g) {}
    @Override
    public void update()
    {
        this.animationRunner.update();
    }
    @Override
    public void earlyUpdate(){}
    @Override
    public void lateUpdate() {}
}
