package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;
import crackengine.jcrackengine.physics.collision.Collider;
import crackengine.jcrackengine.physics.collision.RectangleCollider;

/**
 * Represents tile that has rectangular collider within it
 */
public class StaticCollidableTile extends Tile implements StaticCollidable {
    RectangleCollider collider;

    public StaticCollidableTile(String texturePath, Coordinate pos) {
        super(texturePath, pos);
        collider = new RectangleCollider(width, height);
        addComponent(collider);
    }

    public StaticCollidableTile(String texturePath) {
        super(texturePath);
        collider = new RectangleCollider(width, height);
        addComponent(collider);
    }

    public StaticCollidableTile(Tile tile) {
        super(tile);
        collider = new RectangleCollider(width, height);
        addComponent(collider);
    }

    public StaticCollidableTile(Tile tile, Coordinate pos) {
        super(tile, pos);
        collider = new RectangleCollider(width, height);
        addComponent(collider);
    }


    public StaticCollidableTile setCollider(RectangleCollider collider) {
        this.collider = collider;
        return this;
    }

    public void setTrigger(boolean trigger){
        collider.setTrigger(trigger);
        updateComponent(collider);
    }

    public boolean isTrigger(){
        return collider.isTrigger();
    }

    @Override
    public StaticCollidableTile setPosition(Coordinate pos) {
        super.setPosition(pos);
        return this;
    }

    @Override
    public StaticCollidableTile setSize(double width, double height) {
        super.setSize(width, height);
        boolean isTrigger = collider.isTrigger();
        collider = new RectangleCollider(width, height);
        collider.setTrigger(isTrigger);
        collider.bind(this);
        updateComponent(collider);
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
        return collider.getFromPosition(position); /*since tile is immovable, no need to get it from specific position*/
    }

    @Override
    public void onCollided(Collider collider) {
        /*

        var object = collider.getEntity();
        if (object instanceof Movable){
            var oppositeMovement = ((Movable)object).getMovementVector().opposite();
            object.position.x += (long) oppositeMovement.x;
            object.position.y += (long) oppositeMovement.y;
        }
         */
    }

    @Override
    public StaticCollidableTile makeCopy() {
        return new StaticCollidableTile(this);
    }
}
