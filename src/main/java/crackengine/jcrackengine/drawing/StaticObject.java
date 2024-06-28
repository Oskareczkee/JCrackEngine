package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.drawing.interfaces.Drawable;
import crackengine.jcrackengine.math.Coordinate;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class StaticObject extends GameObject implements Drawable {
    protected Image sprite = null;
    public StaticObject(Coordinate position) {
        this.position = position;
    }

    public StaticObject(Coordinate position, String spritePath){
        this.position = position;
        this.sprite = new Image(spritePath);
    }

    public void setSprite(String spritePath){
        this.sprite = new Image(spritePath);
    }

    public void setSprite(Image sprite){
        this.sprite = sprite;
    }

    @Override
    public void draw(GraphicsContext g) {
        if(sprite==null)return;

        //draw rotated sprite if it is rotated
        if(angleDeg%360!=0){
            ImageView iv = new ImageView(sprite);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            iv.setRotate(angleDeg);
            g.drawImage(iv.snapshot(params,null), lastOnCameraPosition.x, lastOnCameraPosition.y);
            return;
        }

        //do not rotate sprite if it is not rotated
        g.drawImage(sprite, lastOnCameraPosition.x, lastOnCameraPosition.y);
    }
}
