package crackengine.jcrackengine.drawing.collision;

import crackengine.jcrackengine.math.Coordinate;
import javafx.geometry.Rectangle2D;


/**
 * Base of all colliders
 */
public abstract class Collider {
    Coordinate entityOffset = new Coordinate(0, 0);
    protected boolean isTrigger=false;

    /**
     * @param position in world position of this collider
     * @return new Collider, with proper in world position, entity offset is added to this position as well
     */
    public abstract Collider getFromPosition(Coordinate position);
    /**
     * @param other other collider
     * @return is there an intersection between this collider and other requested
     */
    public abstract boolean collides(Collider other);

    /**
     * Entity offset is an offset which collider will have according to original position of an object<br/>
     * For example if entityOffset=(50,50), position=(100,100), then collider will start at position (150,150)
     * @return entity offset
     */
    public Coordinate getEntityOffset(){
        return entityOffset;
    }

    /**
     * Entity offset is an offset which collider will have according to original position of an object<br/>
     * For example if entityOffset=(50,50), position=(100,100), then collider will start at position (150,150)
     * @param offset entity offset
     */
    public void setEntityOffset(Coordinate offset){
        this.entityOffset=offset;
    }

    /**
     * @return returns whether given collider is trigger
     */
    public boolean isTrigger(){
        return isTrigger;
    }

    /**
     *  Trigger colliders do not run onCollision method, but should run onCollided<p>
     *  onCollided should implement behavior of trigger
     * @param trigger sets this collider as trigger or not
     */
    public void setTrigger(boolean trigger){
        isTrigger=trigger;
    }

    /**
     * @param radius radius of circle
     * @param circleCenter center of circle
     * @param rectangle rectangle
     * @return is there intersetion between this rectangle and circle
     */
    protected boolean RectangleCircleIntersection(double radius, Coordinate circleCenter, Rectangle2D rectangle){
        //nearest point from rectangle to circle
        double x1 = rectangle.getMinX(), x2 = rectangle.getMaxX();
        double y1 = rectangle.getMinY(), y2 = rectangle.getMaxY();

        double nX = Math.max(x1, Math.min(circleCenter.x,x2));
        double nY = Math.max(y1, Math.min(circleCenter.y,y2));

        double dx = nX- circleCenter.x;
        double dy = nY- circleCenter.y;
        return (dx*dx + dy*dy <= radius*radius);
    }

    /**
     * @param circleCenter1 center of first circle
     * @param r1 radius of fist circle
     * @param circleCenter2 center of second circle
     * @param r2 radius of second circle
     * @return do these 2 circles intersect
     */
    protected boolean CircleCircleIntersection(Coordinate circleCenter1, double r1, Coordinate circleCenter2, double r2){
        //I do not use square root operation, powering is cheaper
        double centerDistanceSquared = (Math.pow(circleCenter2.x-circleCenter1.x,2) + Math.pow(circleCenter2.y-circleCenter1.y,2));
        return Math.pow(r1+r2,2)>=centerDistanceSquared;
    }
}
