package crackengine.jcrackengine.physics.collision;

import crackengine.jcrackengine.core.components.Component;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2F;
import crackengine.jcrackengine.physics.RigidBody;
import javafx.geometry.Rectangle2D;


/**
 * Base of all colliders
 */
public abstract class Collider extends Component {
    Coordinate entityOffset = new Coordinate(0, 0);
    protected boolean isTrigger=false;
    protected double angleDeg=0;

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

    public double getAngleDeg(){
        return angleDeg;
    }

    public void setAngleDeg(double angleDeg){
        this.angleDeg = angleDeg;
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

        //closest point to the circle
        double nX = Math.max(x1, Math.min(circleCenter.x,x2));
        double nY = Math.max(y1, Math.min(circleCenter.y,y2));

        double dx = nX- circleCenter.x;
        double dy = nY- circleCenter.y;
        return (dx*dx + dy*dy <= radius*radius);
    }

    protected boolean BoxCircleIntersection(double radius, Coordinate circleCenter, BoxCollider box){
        var minmax = box.getMinMaxXY();
        //nearest point from box to circle
        double x1 = minmax[0].x, x2 = minmax[1].x;
        double y1 = minmax[0].y, y2 = minmax[1].y;

        //closest point to the circle
        double nX = Math.max(x1, Math.min(circleCenter.x,x2));
        double nY = Math.max(y1, Math.min(circleCenter.y,y2));

        double dx = nX- circleCenter.x;
        double dy = nY- circleCenter.y;
        return (dx*dx + dy*dy <= radius*radius);
    }

    /**
     * projects box vertices over given axis and returns max and min projection
     * @return minimum out[0] and maximum out[1] projection
     */
    private Vector2F findMinMaxProjection(BoxCollider box, Vector2F axis){
        var vert = box.getVertices();
        double projection = vert[0].dot(axis);
        double min = projection, max = projection;
        for(int i=1; i<vert.length; i++){
            projection = vert[i].dot(axis);
            max = Math.max(max, projection);
            min = Math.min(min, projection);
        }

        return new Vector2F(min, max);
    }

    /**
     * Returns whether 2 boxes overlaps, this is basically SAT algorithm<br/>
     * <a href="https://textbooks.cs.ksu.edu/cis580/04-collisions/04-separating-axis-theorem/index.html">SAT Reference</a>
     * @param b1 first box
     * @param b2 second box
     * @return whether these 2 boxes collides
     */
    protected boolean BoxBoxIntersection(BoxCollider b1, BoxCollider b2){
        var b1Vert = b1.getVertices();
        var b2Vert = b2.getVertices();

        Vector2F[] b1Normals = new Vector2F[4]; //calculate all normals
        Vector2F[] b2Normals = new Vector2F[4];
        var edge1 = b1Vert[b1Vert.length-1].sub(b1Vert[0]); //normal vector from the last to first corner
        var edge2 = b2Vert[b2Vert.length-1].sub(b2Vert[0]);
        b1Normals[0] = new Vector2F(edge1.y, -edge1.x).normalize();//normalized perpendicular vector
        b2Normals[0]= new Vector2F(edge2.y, -edge2.x).normalize();

        for(int i=1; i<b1Normals.length; i++){
            edge1 = b1Vert[i].sub(b1Vert[i-1]);
            edge2 = b2Vert[i].sub(b2Vert[i-1]);
            b1Normals[i] = new Vector2F(edge1.y, -edge1.x).normalize();
            b2Normals[i]= new Vector2F(edge2.y, -edge2.x).normalize();
        }


        for(Vector2F normal : b1Normals){
            Vector2F mm1 = findMinMaxProjection(b1, normal);
            Vector2F mm2 = findMinMaxProjection(b2, normal);
            if(mm1.y < mm2.x || mm2.y < mm1.x) return false; //remember that mm1.y is max, mm1.x is min
        }
        for(Vector2F normal : b2Normals){
            Vector2F mm1 = findMinMaxProjection(b1, normal);
            Vector2F mm2 = findMinMaxProjection(b2, normal);
            if(mm1.y < mm2.x || mm2.y < mm1.x) return false; //remember that mm1.y is max, mm1.x is min
        }

        return true;
    }

    /*Todo: Implement convex shape and sat algorithm for them*/

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

    public void applyReactionForce(Collider collider){
        RigidBody rb = entity.getComponent(RigidBody.class);
        if(rb==null) return;

        Vector2F reflected = rb.getForces().opposite().rotate(collider.angleDeg);

        //add more constraints here
        rb.setForces(reflected);
    }
}
