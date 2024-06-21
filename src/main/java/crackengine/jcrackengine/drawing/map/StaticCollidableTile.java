package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.GameApplication;
import crackengine.jcrackengine.core.Audio;
import crackengine.jcrackengine.drawing.interfaces.Movable;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.drawing.interfaces.StaticCollidable;
import crackengine.jcrackengine.drawing.collision.Collider;
import crackengine.jcrackengine.drawing.collision.RectangleCollider;

/**
 * Represents tile that has rectangular collider within it
 */
public class StaticCollidableTile extends Tile implements StaticCollidable {
    RectangleCollider collider;

    public StaticCollidableTile(String texturePath, Coordinate pos) {
        super(texturePath, pos);
        collider = new RectangleCollider(pos,width, height);
    }

    public StaticCollidableTile(String texturePath) {
        super(texturePath);
        collider = new RectangleCollider(position,width, height);
    }

    public StaticCollidableTile(Tile tile) {
        super(tile);
        collider = new RectangleCollider(position,width, height);
    }

    public StaticCollidableTile(Tile tile, Coordinate pos) {
        super(tile, pos);
        collider = new RectangleCollider(pos,width, height);
    }


    public StaticCollidableTile setCollider(RectangleCollider collider) {
        this.collider = collider;
        return this;
    }

    @Override
    public StaticCollidableTile setPosition(Coordinate pos) {
        super.setPosition(pos);
        collider = new RectangleCollider(pos,width, height);
        return this;
    }

    @Override
    public StaticCollidableTile setSize(double width, double height) {
        super.setSize(width, height);
        collider = new RectangleCollider(position,width, height);
        return this;
    }

    @Override
    public StaticCollidableTile setHeight(double height) {
        return setSize(this.width,height);
    }

    @Override
    public StaticCollidableTile setWidth(double width) {
        return setSize(width, this.height);
    }

    @Override
    public Collider getCollider() {
        return collider; /*since tile is immovable, no need to get it from specific position*/
    }

    @Override
    public void onCollided(StaticCollidable object) {
        if (object instanceof Movable){
            var oppositeMovement = ((Movable)object).getMovementVector().opposite();
            ((GameObject)object).position.x += (long) oppositeMovement.x;
            ((GameObject)object).position.y += (long) oppositeMovement.y;
        }
    }

    @Override
    public StaticCollidableTile makeCopy() {
        return new StaticCollidableTile(this);
    }
}
