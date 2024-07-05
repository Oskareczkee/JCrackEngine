package crackengine.jcrackengine.physics;

import crackengine.jcrackengine.math.Vector2F;

import java.util.ArrayList;
import java.util.List;

public class CollisionManifold {
    private boolean colliding = false;
    private Vector2F normal;
    private List<Vector2F> contactPoints = new ArrayList<Vector2F>();
    private double depth;

    public CollisionManifold(){
        normal = new Vector2F();
        depth = 0.0;
    }

    public CollisionManifold(Vector2F normal, List<Vector2F> contactPoints, double depth) {
        this.normal = normal;
        this.contactPoints = contactPoints;
        this.depth = depth;
        colliding=true;
    }

    public CollisionManifold(Vector2F normal,Vector2F contactPoint, double depth){
        this.normal = normal;
        contactPoints.add(contactPoint);
        this.depth = depth;
        colliding=true;
    }

    public List<Vector2F> getContactPoints() {
        return contactPoints;
    }

    public double getDepth() {
        return depth;
    }

    public Vector2F getNormal() {
        return normal;
    }
}
