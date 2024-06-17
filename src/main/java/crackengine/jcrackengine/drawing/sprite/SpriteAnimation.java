package crackengine.jcrackengine.drawing.sprite;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Stores sprite frames of animation
 */
public class SpriteAnimation {
    private final List<Image> sprites;

    public SpriteAnimation(List<Image> sprites) {
        this.sprites = sprites;
    }

    /**
     * @return number of frames in the animation
     */
    public int frameCount(){
        return sprites.size();
    }

    /**
     * @param index 0 based index
     * @return sprite frame under given index
     */
    public Image getSprite(int index) {
        return sprites.get(index);
    }
}
