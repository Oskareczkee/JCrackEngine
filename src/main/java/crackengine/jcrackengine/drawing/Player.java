package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.GameApplication;
import crackengine.jcrackengine.drawing.interfaces.Collidable;
import crackengine.jcrackengine.drawing.collision.Collider;
import crackengine.jcrackengine.drawing.collision.RectangleCollider;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class Player extends Entity implements Collidable {
    private final int speed=5;
    private String WalkNorth="";
    private String WalkEast="";
    private String WalkSouth="";
    private String WalkWest="";
    private String IdleNorth="";
    private String IdleEast="";
    private String IdleSouth="";
    private String IdleWest="";
    private final int animationFrameLength=10;
    RectangleCollider collider = new RectangleCollider(new Rectangle2D(0,0,10,10));

    public Player(Coordinate position) {
        super(position);
        Setup();
    }

    public Player(){
        super(new Coordinate(0,0));
        Setup();
    }

    @Override
    public void Setup() {
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
    }

    @Override
    public void update() {
        super.update();

        setMovementVector(new Vector2D(0,0));
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.W.getCode())) {
            FireAnimation("WalkNorth", animationFrameLength);
            SetIdleSprite("IdleNorth");
            movement.y=-speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.A.getCode())){
            FireAnimation("WalkWest", animationFrameLength);
            SetIdleSprite("IdleWest");
            movement.x=-speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.S.getCode())){
            FireAnimation("WalkSouth", animationFrameLength);
            SetIdleSprite("IdleSouth");
            movement.y=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.D.getCode())){
            FireAnimation("WalkEast", animationFrameLength);
            SetIdleSprite("IdleEast");
            movement.x=speed;
        }

        move();
    }

    @Override
    public Collider getCollider() {
        return collider.getFromPosition(position);
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(sprite, lastOnCameraPosition.x, lastOnCameraPosition.y);
    }
}
