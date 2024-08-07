package crackengine.jcrackengine.tests;

import crackengine.jcrackengine.GameApplication;
import crackengine.jcrackengine.drawing.Entity;
import crackengine.jcrackengine.physics.RigidBody;
import crackengine.jcrackengine.physics.collision.CircleCollider;
import crackengine.jcrackengine.physics.interfaces.DynamicCollidable;
import crackengine.jcrackengine.physics.interfaces.Movable;
import crackengine.jcrackengine.physics.collision.Collider;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2F;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Objects;

public class Player extends Entity implements DynamicCollidable, Movable {
    private final int speed=300;
    private String WalkNorth="";
    private String WalkEast="";
    private String WalkSouth="";
    private String WalkWest="";
    private String IdleNorth="";
    private String IdleEast="";
    private String IdleSouth="";
    private String IdleWest="";
    private final int animationFrameLength=10;
    CircleCollider collider = new CircleCollider(new Coordinate(0,0),10);
    RigidBody rb = new RigidBody();
    Vector2F movement = new Vector2F(0,0);

    public Player(Coordinate position) {
        super(position);
        sprites = new HashMap<>();
        Setup();
    }

    public Player(){
        super(new Coordinate(0,0));
        Setup();
    }

    @Override
    public void Setup() {
        rb.useGravity(true);
        rb.setCollider(collider);
        addComponent(collider);
        addComponent(rb);

        IdleNorth = Objects.requireNonNull(getClass().getResource("/Sprites/IDLE_NORTH.png")).toString();
        IdleEast = Objects.requireNonNull(getClass().getResource("/Sprites/IDLE_EAST.png")).toExternalForm();
        IdleSouth = Objects.requireNonNull(getClass().getResource("/Sprites/IDLE_SOUTH.png")).toExternalForm();
        IdleWest = Objects.requireNonNull(getClass().getResource("/Sprites/IDLE_WEST.png")).toExternalForm();
        WalkNorth= Objects.requireNonNull(getClass().getResource("/Sprites/WALK_NORTH.png")).toExternalForm();
        WalkEast= Objects.requireNonNull(getClass().getResource("/Sprites/WALK_EAST.png")).toExternalForm();
        WalkSouth= Objects.requireNonNull(getClass().getResource("/Sprites/WALK_SOUTH.png")).toExternalForm();
        WalkWest= Objects.requireNonNull(getClass().getResource("/Sprites/WALK_WEST.png")).toExternalForm();

        LoadSprite("IdleNorth",IdleNorth);
        LoadSprite("IdleEast",IdleEast);
        LoadSprite("IdleSouth",IdleSouth);
        LoadSprite("IdleWest",IdleWest);
        LoadAnimation("WalkNorth", WalkNorth,1,4);
        LoadAnimation("WalkEast", WalkEast,1,4);
        LoadAnimation("WalkSouth", WalkSouth,1,4);
        LoadAnimation("WalkWest", WalkWest,1,4);
        SetSprite("IdleEast");
    }

    @Override
    public void update() {
        super.update();

        setMovementVector(new Vector2F(0,0));
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.W.getCode())) {
            FireAnimation("WalkNorth", animationFrameLength);
            SetIdleSprite("IdleNorth");
            movement.y-=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.A.getCode())){
            FireAnimation("WalkWest", animationFrameLength);
            SetIdleSprite("IdleWest");
            movement.x-=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.S.getCode())){
            FireAnimation("WalkSouth", animationFrameLength);
            SetIdleSprite("IdleSouth");
            movement.y+=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.D.getCode())){
            FireAnimation("WalkEast", animationFrameLength);
            SetIdleSprite("IdleEast");
            movement.x+=speed;
        }

        move();
    }

    @Override
    public Collider getCollider() {
        return collider.getFromPosition(position);
    }

    @Override
    public void onCollision(Collider collider) {
    }

    @Override
    public void onCollided(Collider collider) {}

    @Override
    public Vector2F getMovementVector() {
        return movement;
    }

    @Override
    public void setMovementVector(Vector2F vector) {
        this.movement = vector;
    }

    @Override
    public void setMovementX(double x) {
        this.movement.x=x;
    }

    @Override
    public void setMovementY(double y) {
        this.movement.y=y;
    }

    @Override
    public void move() {
        rb.addForce(new Vector2F(movement.x, movement.y));
    }
}
