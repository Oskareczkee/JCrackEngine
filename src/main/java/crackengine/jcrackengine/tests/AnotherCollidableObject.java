package crackengine.jcrackengine.tests;

import crackengine.jcrackengine.drawing.StaticObject;
import crackengine.jcrackengine.drawing.interfaces.Copyable;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.physics.collision.CircleCollider;
import crackengine.jcrackengine.physics.collision.Collider;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;
import javafx.scene.image.Image;

import java.util.Objects;

public class AnotherCollidableObject extends StaticObject implements Copyable, StaticCollidable {
    String spritePath="";
    CircleCollider collider;

    public AnotherCollidableObject(Coordinate position) {
        super(position);
        this.width=32;
        this.height=32;
        collider = new CircleCollider(new Coordinate(0,0),32);
        spritePath = Objects.requireNonNull(getClass().getResource("/Tiles/WallTile.png")).toExternalForm();
        sprite = new Image(spritePath);

        addComponent(collider);
    }

    @Override
    public void onCollided(Collider c) {}

    @Override
    public Collider getCollider() {
        return collider.getFromPosition(position);
    }

    @Override
    public AnotherCollidableObject makeCopy() {
        return new AnotherCollidableObject(position);
    }
}
