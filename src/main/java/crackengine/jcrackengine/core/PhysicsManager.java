package crackengine.jcrackengine.core;

import crackengine.jcrackengine.core.interfaces.Updatable;
import crackengine.jcrackengine.math.Vector2F;
import crackengine.jcrackengine.physics.CollisionManifold;
import crackengine.jcrackengine.physics.RigidBody;
import crackengine.jcrackengine.physics.collision.Collider;
import crackengine.jcrackengine.physics.collision.Collisions;
import crackengine.jcrackengine.physics.interfaces.DynamicCollidable;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;

import java.util.ArrayList;
import java.util.List;

public class PhysicsManager implements Updatable, Runnable {
    private static int UPDATE_RATE=60;
    public static Vector2F GravityForce = new Vector2F(0,10);
    private long updateTime=0;
    private long lastUpdateTime=0;
    private long deltaTime=0;
    private double frameLength = (double) 1 /UPDATE_RATE; //frame length is in seconds
    private boolean isRunning=true;
    private final Object lock=new Object();
    private final long NANO_SECOND = 1000 * 1000 * 1000;
    List<StaticCollidable> colliders = new ArrayList<StaticCollidable>();
    List<DynamicCollidable> dynamicColliders = new ArrayList<DynamicCollidable>();

    public <T extends StaticCollidable> void add(T collidable){
        if(collidable instanceof StaticCollidable)
            colliders.add(collidable);
        if(collidable instanceof DynamicCollidable)
            dynamicColliders.add((DynamicCollidable)collidable);
    }

    public <T extends StaticCollidable> void remove(T collidable){
        colliders.remove(collidable);
        if(collidable instanceof DynamicCollidable)
            dynamicColliders.remove((DynamicCollidable)collidable);
    }

    public void setUpdateRate(int rate){
        if(rate<=0) return;
        UPDATE_RATE=rate;
        frameLength = (double) 1 /UPDATE_RATE;
    }

    public void stop(){
        isRunning=false;
    }

    public void start(){
        isRunning=true;
    }

    @Override
    public void update() {
        for(DynamicCollidable collidable : dynamicColliders) {
            GameObject object = (GameObject) collidable;
            if(object.getComponent(Collider.class)==null || object.getComponent(RigidBody.class)==null) continue;

            Collider objectCollider = object.getComponent(Collider.class);
            RigidBody objectRB = object.getComponent(RigidBody.class);
            RigidBody colliderRB = null;

            for(StaticCollidable staticCollidable : colliders) {
                GameObject collider = (GameObject) staticCollidable;
                if(collider.getComponent(Collider.class)==null) continue;
                colliderRB = collider.getComponent(RigidBody.class);

                Collider colliderCollider = collider.getComponent(Collider.class);
                Collider objectFP = objectCollider.getFromPosition(object.position);
                Collider colliderFP = colliderCollider.getFromPosition(collider.position);

                if(objectCollider!=colliderCollider && objectFP.collides(colliderFP)){ //object!=colliderprevents object colliding with itself
                    collidable.onCollision(colliderFP); //action
                    staticCollidable.onCollided(objectFP);//reaction

                    if(colliderFP.isTrigger()) continue; //triggers cannot be collided
                    
                    CollisionManifold manifold = Collisions.findCollisionManifold(objectFP, colliderFP);
                    LinearImpulseResolution(objectRB, colliderRB, manifold); //resolve collisions
                }
            }

            objectRB.applyForce((double) deltaTime /NANO_SECOND);
            if(colliderRB!=null) colliderRB.applyForce((double) deltaTime /NANO_SECOND);
        }
    }

    /**
     * <a href="https://research.ncl.ac.uk/game/mastersdegree/gametechnologies/physicstutorials/5collisionresponse/Physics%20-%20Collision%20Response.pdf">Linear impulse resolution math</a>
     */
    private void LinearImpulseResolution(RigidBody r1, RigidBody r2, CollisionManifold m){
        if(m==null) return; //collision is not supported
        //if any of the rigid body is null treat it as infinite mass static object, like wall
        if(r1 == null) r1 = RigidBody.InfiniteMass();
        if(r2 == null) r2 = RigidBody.InfiniteMass();

        double invMass1 = r1.getInverseMass();
        double invMass2 = r2.getInverseMass();
        double invMassSum = invMass1+invMass2;

        if(invMassSum==0.0) return; //both objects have infinite mass, nothing to resolve

        Vector2F relativeVel = r1.getVelocity().sub(r2.getVelocity());
        Vector2F relativeNormal = m.getNormal().normalize();
        if(relativeVel.dot(relativeNormal) > 0.0) //objects are moving away from each other, nothing to resolve
            return;

        double restitution = Math.min(r1.getRestitution(), r2.getRestitution()); //choose smaller restitution
        double n = (-(1.0+restitution) * relativeVel.dot(relativeNormal));
        double j = n/invMassSum;
        if(!m.getContactPoints().isEmpty() && j!=0.0) //distribute over all contact points evenly
            j/=m.getContactPoints().size();

        Vector2F impulse = new Vector2F(relativeNormal.mul(j));
        Vector2F r1Vel = r1.getVelocity().add(impulse.mul(invMass1)); //this one is added
        Vector2F r2Vel = r2.getVelocity().add(impulse.mul(-invMass2)); //this one is subtracted
        r1.setVelocity(r1Vel);
        r2.setVelocity(r2Vel);
    }

    @Override
    public void run() {
        //set this to default otherwise delta time will be big in first frame leading to errors
        updateTime = System.nanoTime();
        lastUpdateTime = System.nanoTime();

        while(true){ //this should run infinitely, but you can stop physics engine anytime
            while(isRunning) {
                updateTime = System.nanoTime();
                deltaTime = (updateTime - lastUpdateTime);//cast to seconds
                if((double) deltaTime /NANO_SECOND >= frameLength){
                    synchronized (lock){
                        update();
                    }
                    lastUpdateTime = System.nanoTime();
                }
            }
        }
    }
}
