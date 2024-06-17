package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.drawing.interfaces.Collidable;
import crackengine.jcrackengine.drawing.collision.Collider;
import crackengine.jcrackengine.drawing.collision.RectangleCollider;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * Represents tile that has rectangular collider within it
 */
public class CollidableTile extends Tile implements Collidable {

    RectangleCollider collider;

    public CollidableTile(String texturePath) {
        super(texturePath);
        collider = new RectangleCollider(position,getWidth(), getHeight());
    }

    public CollidableTile(Coordinate position, String texturePath) {
        super(position, texturePath);
        collider = new RectangleCollider(position,getWidth(), getHeight());
    }

    public CollidableTile(Coordinate position, String texturePath, double width, double height) {
        super(position, texturePath, width, height);
        collider = new RectangleCollider(position,getWidth(), getHeight());
    }

    public CollidableTile(Tile tile, double width, double height) {
        super(tile, width, height);
        collider = new RectangleCollider(position,getWidth(), getHeight());
    }

    public CollidableTile(Tile tile, Coordinate coord, double width, double height) {
        super(tile, coord, width, height);
        collider = new RectangleCollider(new Coordinate(0,0),getWidth(), getHeight());
    }

    public void setCollider(RectangleCollider collider) {
        this.collider = collider;
    }

    @Override
    public Collider getCollider() {
        return collider; /*since tile is immovable, no need to get it from specific position*/
    }

    @Override
    public void onCollided(Collidable object) {
        new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/portal.mp3")).toExternalForm()).play();
        ((GameObject)object).position = new Coordinate(500,500);
    }
}
