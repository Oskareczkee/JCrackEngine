package crackengine.jcrackengine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Player extends Entity {
    private final int speed=5;
    private String WalkNorth="";
    private String WalkEast="";
    private String WalkSouth="";
    private String WalkWest="";
    private String Idle="";
    private String IdleNorth="";
    private String IdleEast="";
    private String IdleSouth="";
    private String IdleWest="";
    private final int animationFrameLength=10;
    /*TODO:add sprite animations, sprites should be updates once 10 frames or more, this should be available to change*/

    public Player(Coordinate position) {
        super(position);
    }

    public Player(){
        super(new Coordinate(0,0));
    }

    @Override
    public void Setup() {
        Idle=Objects.requireNonNull(getClass().getResource("/RaiderSprite.png")).toExternalForm();
        IdleNorth = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_NORTH.png")).toExternalForm();
        IdleEast = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_EAST.png")).toExternalForm();
        IdleSouth = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_SOUTH.png")).toExternalForm();
        IdleWest = Objects.requireNonNull(getClass().getResource("/RaiderSprites/IDLE_WEST.png")).toExternalForm();
        WalkNorth= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_NORTH.png")).toExternalForm();
        WalkEast= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_EAST.png")).toExternalForm();
        WalkSouth= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_SOUTH.png")).toExternalForm();
        WalkWest= Objects.requireNonNull(getClass().getResource("/RaiderSprites/WALK_WEST.png")).toExternalForm();

        LoadIdleSprite(Idle);
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
            //LoadIdleSprite(IdleNorth);
            position.y-=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.A.getCode())){
            FireAnimation("WalkWest", animationFrameLength);
            //LoadIdleSprite(IdleWest);
            position.x-=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.S.getCode())){
            FireAnimation("WalkSouth", animationFrameLength);
            //LoadIdleSprite(IdleSouth);
            position.y+=speed;
        }
        if(GameApplication.KeyHandler.isKeyPressed(KeyCode.D.getCode())){
            FireAnimation("WalkEast", animationFrameLength);
            //LoadIdleSprite(IdleEast);
            position.x+=speed;
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        //sprite = idleSprite;
        //sprite = animations.get("Walk").getSprite(2);
        g.drawImage(sprite, position.x, position.y);
    }
}
