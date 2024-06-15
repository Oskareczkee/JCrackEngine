package crackengine.jcrackengine.drawing;

import crackengine.jcrackengine.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class Player extends Entity {
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
        IdleNorth = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_NORTH.png")).toString();
        IdleEast = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_EAST.png")).toExternalForm();
        IdleSouth = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_SOUTH.png")).toExternalForm();
        IdleWest = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_WEST.png")).toExternalForm();
        WalkNorth= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_NORTH.png")).toExternalForm();
        WalkEast= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_EAST.png")).toExternalForm();
        WalkSouth= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_SOUTH.png")).toExternalForm();
        WalkWest= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_WEST.png")).toExternalForm();

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

        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.W.getCode())) {
            FireAnimation("WalkNorth", animationFrameLength);
            SetIdleSprite("IdleNorth");
            position.y-=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.A.getCode())){
            FireAnimation("WalkWest", animationFrameLength);
            SetIdleSprite("IdleWest");
            position.x-=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.S.getCode())){
            FireAnimation("WalkSouth", animationFrameLength);
            SetIdleSprite("IdleSouth");
            position.y+=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.D.getCode())){
            FireAnimation("WalkEast", animationFrameLength);
            SetIdleSprite("IdleEast");
            position.x+=speed;
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(sprite, lastOnCameraPosition.x, lastOnCameraPosition.y);
    }
}
