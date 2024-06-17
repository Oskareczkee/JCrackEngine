package crackengine.jcrackengine.drawing.collision;

import crackengine.jcrackengine.math.Coordinate;
import javafx.geometry.Rectangle2D;

/**
 * Collider that has rectangular shape
 */
public class RectangleCollider extends Collider {
    Rectangle2D colliderBox;

    public RectangleCollider(Rectangle2D colliderBox) {
        this.colliderBox = colliderBox;
        this.entityOffset = new Coordinate((long) colliderBox.getMinX(), (long) colliderBox.getMinY());
    }

    public RectangleCollider(Coordinate offset, double width, double height){
        this.colliderBox = new Rectangle2D(offset.x, offset.y,  width, height);
        this.entityOffset = offset;
    }

    @Override
    public boolean collides(Collider other) {
        if(other instanceof RectangleCollider)
            return colliderBox.intersects(((RectangleCollider)other).colliderBox);
        else if(other instanceof CircleCollider c)
            return RectangleCircleIntersection(c.radius, c.center, colliderBox);

        return false;
    }

    @Override
    public RectangleCollider getFromPosition(Coordinate position) {
        RectangleCollider collider = new RectangleCollider(new Rectangle2D(position.x + entityOffset.x,
                position.y + entityOffset.y,
                colliderBox.getWidth(), colliderBox.getHeight()));
        collider.setEntityOffset(new Coordinate(entityOffset.x, entityOffset.y));
        return collider;
    }
}
