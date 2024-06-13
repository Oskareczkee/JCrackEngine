package crackengine.jcrackengine;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.embed.swing.SwingFXUtils.*;

public class SpriteLoader {
    private Image spriteSheet;
    private int rows, cols;
    private int spriteWidth, spriteHeight;
    private int spriteCount;

    Image LoadSingle(String filepath){
        return new Image(filepath);
    }

    public SpriteLoader withSpriteSheet(String filepath){
        spriteSheet = new Image(filepath);
        return this;
    }

    public SpriteLoader withRows(int rows){
        this.rows =rows;
        return this;
    }

    public SpriteLoader withCols(int cols){
        this.cols = cols;
        return this;
    }

    public SpriteLoader withSpriteWidth(int spriteWidth){
        this.spriteWidth = spriteWidth;
        return this;
    }
    public SpriteLoader withSpriteHeight(int spriteHeight){
        this.spriteHeight = spriteHeight;
        return this;
    }
    public SpriteLoader withSpriteCount(int spriteCount){
        this.spriteCount = spriteCount;
        return this;
    }

    public SpriteLoader withSpriteSize(int width, int height){
        spriteWidth = width;
        spriteHeight = height;
        return this;
    }

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
