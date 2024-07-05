package crackengine.jcrackengine.physics.collision;

import crackengine.jcrackengine.math.CrackMath;
import crackengine.jcrackengine.math.Vector2F;
import crackengine.jcrackengine.physics.CollisionManifold;
import javafx.util.Pair;

public class Collisions {
    public static CollisionManifold findCollisionManifold(Collider a, Collider b) {
        if((a instanceof CircleCollider ca) && (b instanceof CircleCollider cb))
            return circleCircleManifold(ca,cb);

        else if((a instanceof RectangleCollider rc) && (b instanceof CircleCollider cc))
            return circlePolygonManifold(cc, new BoxCollider(rc.colliderBox));

        else if ((a instanceof CircleCollider cc) && (b instanceof RectangleCollider rc))
            return circlePolygonManifold(cc, new BoxCollider(rc.colliderBox));


        return null;
    }

    private static CollisionManifold circleCircleManifold(CircleCollider a, CircleCollider b) {
        double sumRadii = a.radius + b.radius;
        Vector2F c1 = new Vector2F(a.center);
        Vector2F c2 = new Vector2F(b.center);
        Vector2F dist = c1.sub(c2);
        if(dist.lengthSquared() - (sumRadii*sumRadii) > 0)
            return new CollisionManifold(); //return empty collision manifold if there is no collision between colliders

        double depth = Math.abs(dist.length() - sumRadii) * 0.5;
        Vector2F normal = new Vector2F(dist);
        normal.normalize();
        double distanceToPoint = a.radius - depth;
        Vector2F contactPoint = new Vector2F(c1.add(normal.mul(distanceToPoint)));

        return new CollisionManifold(normal, contactPoint, depth);
    }

    /*TODO: change this really to the polygon*/
    private static CollisionManifold circlePolygonManifold(CircleCollider a, BoxCollider b) {
        Vector2F center = new Vector2F(a.center);

        var contactPoint = circlePolygonContactPoint(a,b);
        Vector2F cp = contactPoint.getKey();
        double distanceSquared = contactPoint.getValue();

        if(distanceSquared < 0 ) //there was no collision :(
            return new CollisionManifold();

        Vector2F normal = center.sub(cp);
        normal = normal.normalize();
        double depth = Math.sqrt((a.radius*a.radius) - distanceSquared);
        return new CollisionManifold(normal, cp, depth);
    }

    /*TODO: change this really to the polygon*/
    private static Pair<Vector2F, Double> circlePolygonContactPoint(CircleCollider a, BoxCollider b){
        Vector2F center = new Vector2F(a.center);
        var vert = b.getVertices();

        double minDist = Double.MAX_VALUE;
        Vector2F contactPoint = new Vector2F();

        for(int i=0;i<vert.length;i++){
            Vector2F v1 = vert[i];
            Vector2F v2 = vert[(i+1)%vert.length];
            Vector2F point = closestPoint(v1,v2,center);
            Vector2F vec = center.sub(point);
            double distSquared = vec.lengthSquared();

            if(distSquared < minDist){
                minDist = distSquared;
                contactPoint = point;
            }
        }

        if(minDist>a.radius*a.radius)
            minDist=-1;

        return new Pair<>(contactPoint, minDist);
    }

    /**
     * @return distance from point P to line AB
     */
    private static Vector2F closestPoint(Vector2F A, Vector2F B, Vector2F P){
        Vector2F AB = B.sub(A);
        Vector2F AP = P.sub(A);

        double dot = AB.dot(AP);
        double len = AB.lengthSquared();
        double p = dot/len;
        p = CrackMath.clamp(p,0.0,1.0);

        double cX = A.x + p*AB.x; //closest point X
        double cY = A.y + p*AB.y; //closest point Y

        return new Vector2F(cX,cY);
    }
}
