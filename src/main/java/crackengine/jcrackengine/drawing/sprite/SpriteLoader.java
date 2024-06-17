package crackengine.jcrackengine.drawing.sprite;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class that allows to load sprites and sprite animations from files
 */
public class SpriteLoader {
    private Image spriteSheet;
    private int rows, cols;
    private int spriteWidth, spriteHeight;
    private int spriteCount;

    Image LoadSingle(String filepath){
        return new Image(filepath);
    }

    /**
     * @param filepath resources relative path, where sprite sheet can be found
     */
    public SpriteLoader withSpriteSheet(String filepath){
        spriteSheet = new Image(filepath);
        return this;
    }

    /**
     * @param rows how many rows of sprites are expected in the sprite sheet
     */
    public SpriteLoader withRows(int rows){
        this.rows =rows;
        return this;
    }

    /**
     * @param cols How many columns of sprites are expected in the sprite sheet
     */
    public SpriteLoader withCols(int cols){
        this.cols = cols;
        return this;
    }

    /**
     * @param spriteWidth expected sprite frame width
     */
    public SpriteLoader withSpriteWidth(int spriteWidth){
        this.spriteWidth = spriteWidth;
        return this;
    }

    /**
     * @param spriteHeight expected sprite frame height
     */
    public SpriteLoader withSpriteHeight(int spriteHeight){
        this.spriteHeight = spriteHeight;
        return this;
    }

    /**
     * @param spriteCount How many sprite frames to load from file
     */
    public SpriteLoader withSpriteCount(int spriteCount){
        this.spriteCount = spriteCount;
        return this;
    }

    /**
     * @param width Width of sprite frame
     * @param height Height of sprite frame
     */
    public SpriteLoader withSpriteSize(int width, int height){
        spriteWidth = width;
        spriteHeight = height;
        return this;
    }

    /**
     * If count is not set then count = rows*cols <br/>
     * If width is not set then width = spritesheet.width/cols<br/>
     * If height is not set then height = spritesheet.height/rows<br/>
     * @return Loads animation using previously given settings
     * @throws RuntimeException if sprite sheet is null
     */
    public SpriteAnimation loadAnimation(){
        if(spriteSheet == null)
            throw new RuntimeException("Sprite sheet in builder has not been set");
        else if(this.rows <=0 || this.cols<=0)
            throw new RuntimeException("Rows and cols in sprite builder has not been set properly");

        int count = spriteCount;
        if (count == 0)
            count = rows * cols;

        int width = this.spriteWidth;
        int height = this.spriteHeight;

        if (width == 0)
            width = (int) (spriteSheet.getWidth() / cols);
        if (height == 0)
            height = (int) (spriteSheet.getHeight() / rows);

        int x = 0;
        int y = 0;
        List<Image> sprites = new ArrayList<>(count);

        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < count; i++) {
            WritableImage subImage = new WritableImage(reader,x,y,width, height);
            sprites.add(subImage);
            x += width;
            if (x >= width * cols) { /*go to next row*/
                x = 0;
                y += height;
            }
        }

        return new SpriteAnimation(sprites);
    }
}
