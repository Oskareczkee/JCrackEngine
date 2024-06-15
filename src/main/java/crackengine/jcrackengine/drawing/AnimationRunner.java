package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.drawing.sprite.SpriteAnimation;

public class AnimationRunner implements Updatable {
    private final Entity target;
    private SpriteAnimation animation;
    private int frameLength=0;
    private int frameCounter=0;
    private int currentAnimationFrame=0;
    private boolean hasEnded=false;
    private String actualAnimationName="";

    public AnimationRunner(Entity target) {
        this.target=target;
        hasEnded=true;
    }

    public AnimationRunner(Entity e, SpriteAnimation animation, int frameLength) {
        this.animation = animation;
        this.frameLength = frameLength;
        this.target=e;
    }

    public void changeAnimation(SpriteAnimation animation, int frameLength) {
        this.animation = animation;
        this.frameLength = frameLength;

        hasEnded=false;
        frameCounter=0;
        currentAnimationFrame=0;
    }

    public void setAnimationName(String animationName){
        actualAnimationName=animationName;
    }

    public String getAnimationName(){
        return actualAnimationName;
    }

    public boolean isPlaying(){
        return !hasEnded;
    }

    public void rerun(){
        hasEnded=false;
        currentAnimationFrame=0;
        frameCounter=0;
    }

    private void SetIdle(){
        if(target.idleSprite!=null)
            target.sprite=target.idleSprite;
    }

    @Override
    public void earlyUpdate() {}

    @Override
    public void update() {
        if(hasEnded) return;
        if(animation==null) return;
        if(frameCounter++<frameLength) return;

        if(currentAnimationFrame>=animation.frameCount()){
            hasEnded=true;
            SetIdle();
            return;
        }

        frameCounter=0;
        target.sprite=animation.getSprite(currentAnimationFrame++);
    }

    @Override
    public void lateUpdate() {}
}
