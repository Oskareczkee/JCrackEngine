package crackengine.jcrackengine.physics;

import crackengine.jcrackengine.core.PhysicsManager;
import crackengine.jcrackengine.core.components.Component;
import crackengine.jcrackengine.math.Vector2F;
import crackengine.jcrackengine.physics.collision.Collider;

public class RigidBody extends Component {
    private Collider collider = null;
    private Vector2F forces = new Vector2F();
    private Vector2F velocity = new Vector2F();
    private double mass=1.0; //mass is in kilograms
    private boolean useGravity=false;
    private double restitution=.2;
    private Vector2F dragForce = new Vector2F(.6, .6);

    public static RigidBody InfiniteMass(){
        RigidBody rigidBody = new RigidBody();
        rigidBody.setMass(0.0);
        return rigidBody;
    }

    public void addForce(Vector2F force) {
        this.forces.x += force.x;
        this.forces.y += force.y;
    }

    public double getRestitution(){
        return restitution;
    }

    public void setRestitution(double restitution){
        this.restitution = restitution;
    }

    public Vector2F getDragForce() {
        return dragForce;
    }

    public void setDragForce(Vector2F dragForce) {
        this.dragForce = dragForce;
    }

    public void attachCollider(Collider collider) {
        this.collider = collider;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Vector2F getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity;
    }

    public void setMass(double mass){
        if(mass<0) return;
        this.mass=mass;
    }

    public double getMass(){
        return mass;
    }

    public double getInverseMass(){
        if(mass==0) return 0;
        return 1/mass;
    }

    public Vector2F getForces(){
        return this.forces;
    }
    public void setForces(Vector2F forces) {
        this.forces.x = forces.x;
        this.forces.y = forces.y;
    }

    public void useGravity(boolean useGravity) {
        this.useGravity = useGravity;
    }

    public void applyForce(double deltaTime){
        if(mass==0.0) return; //if object has no mass treat it as infinite mass

        if(useGravity) forces = forces.add(PhysicsManager.GravityForce);
        Vector2F acceleration = new Vector2F(forces.x, forces.y).div(mass);
        velocity = velocity.add(acceleration.mul(deltaTime));

        //apply drag force
        velocity.x*=(1-dragForce.x * deltaTime);
        velocity.y*=(1-dragForce.y*deltaTime);

        this.entity.position.x += (velocity.x*deltaTime);
        this.entity.position.y += (velocity.y*deltaTime);

        this.forces.x=0;
        this.forces.y=0;
    }
}