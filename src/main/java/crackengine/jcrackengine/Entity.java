package crackengine.jcrackengine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.HashMap;

public abstract class Entity implements Drawable, Updatable {
    public Coordinate position;
    public Image idleSprite=null;
    public Image sprite=null;
    protected final HashMap<String, SpriteAnimation> animations = new HashMap<>();
    private AnimationRunner animationRunner = null;

    public Entity(Coordinate position){
        this.position = position;
        animationRunner = new AnimationRunner(this);
        Setup();
    }

    public void LoadIdleSprite(String SpritePath){
        idleSprite = new Image(SpritePath);
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

            if(animationRunner.isPlaying()) return;
            animationRunner.changeAnimation(animation, frameLength);
    }

    public void ForceFireAnimation(String animationName, int frameLength){
        var animation = animations.get(animationName);
        if(animation == null)   return;

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
