package crackengine.jcrackengine;

import javafx.scene.image.Image;

import java.util.List;

public class SpriteAnimation {
    private final List<Image> sprites;

    public SpriteAnimation(List<Image> sprites) {
        this.sprites = sprites;
    }

    public int frameCount(){
        return sprites.size();
    }

    public Image getSprite(int index) {
        return sprites.get(index);
    }
}
