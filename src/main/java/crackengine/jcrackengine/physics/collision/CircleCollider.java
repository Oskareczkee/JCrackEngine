package crackengine.jcrackengine.physics.collision;

import crackengine.jcrackengine.math.Coordinate;

/**
 * Collider with circular shape
 */
public class CircleCollider extends Collider{
    Coordinate center;
    double radius;

    public CircleCollider(final Coordinate offset, final double radius) {
        this.entityOffset = offset;
        this.center = offset;
        this.radius = radius;
    }

    @Override
    public boolean collides(Collider other) {
        if(other instanceof CircleCollider c)
            return CircleCircleIntersection(center,radius,c.center,c.radius);
        else if (other instanceof RectangleCollider r)
            return RectangleCircleIntersection(radius,center,r.colliderBox);
        else if(other instanceof BoxCollider b)
            return BoxCircleIntersection(radius, center,b);

        return false;
    }

    @Override
    public CircleCollider getFromPosition(Coordinate position) {
        Coordinate offsetPosition = new Coordinate(position.x + entityOffset.x, position.y + entityOffset.y);
        CircleCollider c = new CircleCollider(offsetPosition, radius);
        c.setEntityOffset(new Coordinate(entityOffset.x, entityOffset.y));
        c.bind(entity); //rebind entity
        c.isTrigger = isTrigger;
        return c;
    }
}
