package crackengine.jcrackengine.physics.collision;

import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2F;
import javafx.geometry.Rectangle2D;

public class BoxCollider extends Collider{
    double angleDeg=0;
    Vector2F center = new Vector2F();
    double width, height;

    public BoxCollider(Rectangle2D colliderBox){
        center = calculateCenter(colliderBox);
        width=colliderBox.getWidth();
        height=colliderBox.getHeight();
    }

    public BoxCollider(Rectangle2D colliderBox, double angleDeg) {
        this(colliderBox);
        this.angleDeg=angleDeg;
    }

    public BoxCollider(Coordinate offset, double width, double height) {
        this(new Rectangle2D(offset.x,offset.y,width,height));
    }

    public BoxCollider(Coordinate offset, double width, double height, double angleDeg) {
        this(new Rectangle2D(offset.x,offset.y,width,height),angleDeg);
    }

    public BoxCollider(double width, double height) {
        this(new Rectangle2D(0,0,width,height));
    }

    public BoxCollider(double width, double height, double angleDeg) {
        this(new Rectangle2D(0,0,width,height),angleDeg);
    }

    private Vector2F calculateCenter(Rectangle2D collider){
        return new Vector2F((collider.getMinX() + collider.getMaxX())/2,
                (collider.getMinY() + collider.getMaxY())/2);
    }

    public Vector2F[] getVertices(){
        return new Vector2F[]{
                new Vector2F(center.x - width/2, center.y + height/2).rotate(angleDeg,center),
                new Vector2F(center.x + width/2, center.y + height/2).rotate(angleDeg,center),
                new Vector2F(center.x + width/2, center.y - height/2).rotate(angleDeg,center),
                new Vector2F(center.x - width/2, center.y - height/2).rotate(angleDeg,center)
        };
    }

    public Vector2F getCenter() {
        return center;
    }

    public double getAngleDeg(){
        return angleDeg;
    }

    public void setAngleDeg(double angleDeg){
        this.angleDeg=angleDeg;
    }

    public double getMinX(){
        var vertices = getVertices();
        double minX=vertices[0].x;
        for(int i=1;i<vertices.length;i++)
            minX=Math.min(minX,vertices[i].x);

        return minX;
    }

    public double getMinY(){
        var vertices = getVertices();
        double minY=vertices[0].y;
        for(int i=1;i<vertices.length;i++)
            minY=Math.min(minY,vertices[i].y);

        return minY;
    }

    public double getMaxX(){
        var vertices = getVertices();
        double maxX=vertices[0].x;
        for(int i=1;i<vertices.length;i++)
            maxX=Math.max(maxX,vertices[i].x);

        return maxX;
    }

    public double getMaxY(){
        var vertices = getVertices();
        double maxY=vertices[0].y;
        for(int i=1;i<vertices.length;i++)
            maxY=Math.max(maxY,vertices[i].y);

        return maxY;
    }

    /**
     * out[0] -> min x, min y <br/>
     * out[1] -> max x, max y
     * @return returns array of size 2 of vectors containing min/max x/y coordinates
     *
     */
    public Vector2F[] getMinMaxXY(){
        var vertices = getVertices();
        double minX=vertices[0].x;
        double minY=vertices[0].y;
        double maxX=vertices[0].x;
        double maxY=vertices[0].y;

        for(int i=1;i<vertices.length;i++){
            minX=Math.min(minX,vertices[i].x);
            minY=Math.min(minY,vertices[i].y);
            maxX=Math.max(maxX,vertices[i].x);
            maxY=Math.max(maxY,vertices[i].y);
        }

        return new Vector2F[]{
                new Vector2F(minX,minY),
                new Vector2F(maxX,maxY)
        };
    }

    @Override
    public BoxCollider getFromPosition(Coordinate position) {
        Coordinate pos  = new Coordinate(entityOffset.x + position.x, entityOffset.y + position.y);
        BoxCollider collider = new BoxCollider(pos,width, height);
        collider.angleDeg = this.angleDeg;
        return collider;
    }

    @Override
    public boolean collides(Collider other) {
        if(other instanceof CircleCollider c)
            return BoxCircleIntersection(c.radius, c.center, this);
        else if (other instanceof RectangleCollider c)
            return BoxBoxIntersection(this, new BoxCollider(c.colliderBox));
        else if(other instanceof BoxCollider b)
            return BoxBoxIntersection(this, b);

        return false;
    }
}
