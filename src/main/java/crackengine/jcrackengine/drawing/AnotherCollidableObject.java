package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.drawing.interfaces.Copyable;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.physics.collision.BoxCollider;
import crackengine.jcrackengine.physics.collision.Collider;
import crackengine.jcrackengine.physics.interfaces.Movable;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;
import javafx.scene.image.Image;

import java.util.Objects;

public class AnotherCollidableObject extends StaticObject implements Copyable, StaticCollidable {
    String spritePath="";
    BoxCollider collider;

    public AnotherCollidableObject(Coordinate position) {
        super(position);
        this.width=32;
        this.height=32;
        collider = new BoxCollider(32,32,45);
        spritePath = Objects.requireNonNull(getClass().getResource("/Tiles/WallTile.png")).toExternalForm();
        sprite = new Image(spritePath);
    }

    @Override
    public void onCollided(StaticCollidable object) {
        if (object instanceof Movable){
            var oppositeMovement = ((Movable)object).getMovementVector().opposite().rotate(collider.getAngleDeg());
            ((GameObject)object).position.x += (long) oppositeMovement.x;
            ((GameObject)object).position.y += (long) oppositeMovement.y;
        }
    }

    @Override
    public Collider getCollider() {
        return collider.getFromPosition(position);
    }

    @Override
    public AnotherCollidableObject makeCopy() {
        return new AnotherCollidableObject(position);
    }
}
