package crackengine.jcrackengine;

import crackengine.jcrackengine.core.*;
import crackengine.jcrackengine.tests.AnotherCollidableObject;
import crackengine.jcrackengine.tests.TeleporterTile;
import crackengine.jcrackengine.drawing.ui.UIText;
import crackengine.jcrackengine.drawing.util.TileLabyrinthGenerator;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.tests.Player;
import crackengine.jcrackengine.drawing.map.StaticCollidableTile;
import crackengine.jcrackengine.drawing.map.Tile;
import crackengine.jcrackengine.drawing.map.TileMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Objects;

public class GameApplication extends Application{
    public static String GameName = "Your game name";

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static int UPDATE_FRAMERATE=60;

    public static  WorldRenderer Renderer; /*it also manages updates*/
    public static PhysicsManager Physics;
    public static Thread PhysicsThread;
    public static Thread RendererThread;
    public static AudioManager Audio = new AudioManager();
    public Canvas RenderCanvas;
    private Stage Stage;
    private Camera Camera = new Camera();

    private final GraphicsContext gc;
    public static KeyHandler KeyHandler = new KeyHandler();

    @Override
    public void start(Stage stage) {
            new GameApplication();
            Stage.show();
    }

    public GameApplication(){
        // Create a 2D Canvas
        RenderCanvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        RenderCanvas.setFocusTraversable(true); /*without this, key handling does not work*/
        gc = RenderCanvas.getGraphicsContext2D();

        /*Setup base key handling by render canvas*/
        RenderCanvas.setOnKeyPressed(e -> GameApplication.KeyHandler.keyPressed(e.getCode().getCode()));
        RenderCanvas.setOnKeyReleased(e->GameApplication.KeyHandler.keyReleased(e.getCode().getCode()));

        StackPane StackPane = new StackPane();
        StackPane.getChildren().add(RenderCanvas);

        // Create a Scene
        Scene Scene = new Scene(StackPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        Scene.setFill(Color.BLACK);

        Renderer = new WorldRenderer();
        Physics = new PhysicsManager();
        Renderer.addPhysicsManager(Physics);

        Stage = new Stage();
        this.Stage.setTitle(GameName);
        this.Stage.setScene(Scene);

        /*update loop init*/
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/UPDATE_FRAMERATE), e->{
            EarlyUpdate();
            Update();
            LateUpdate();
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
        Setup();

        PhysicsThread = new Thread(Physics);
        PhysicsThread.start();
    }

    public void Setup(){
        Player player = new Player(new Coordinate(0,0));
        UIText text = new UIText("Your HP: 100").
                setFont(Font.loadFont(Objects.requireNonNull(getClass().getResourceAsStream("/Fonts/PixelFont.ttf")),20)).
                setColor(Color.WHITE);

        this.Camera.attach(player).
                    setBound(new Rectangle2D(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));

        Renderer.addCamera(this.Camera);
        Renderer.addObject(player);
        Renderer.addUIObject(text);



        TileMap.addTile(new Tile("/Tiles/Grass.png"));
        new TileMap(64,64).
            loadFromFile("/Maps/TestMap").
            render(Renderer);

        TeleporterTile teleport = (TeleporterTile) new TeleporterTile("/Tiles/Totem.png", new Coordinate(256,128)).setSize(64,64);
        var boxTest = new AnotherCollidableObject(new Coordinate(100,528));
        boxTest.setRotationDeg(60);
        teleport.setTeleportPosition(new Coordinate(512,512));
        Renderer.addObject(teleport);
        Renderer.addObject(boxTest);

        new TileLabyrinthGenerator(10,10)
                .setPathSize(128,128)
                .setWallTile(new StaticCollidableTile("/Tiles/WallTile.png").setSize(128,16))
                .generateRDFS()
                .getTileMap()
                .render(Renderer, new Coordinate(300,300));

        Audio.setGlobalVolume(0.05);
        Audio.playInLoop(new Audio("/Sounds/Versus.mp3", "BackgroundMusic").setVolume(.5));
    }

    public void EarlyUpdate(){
        Renderer.earlyUpdate();
    }

    public void Update(){
        Renderer.update();
        Draw(gc);
    }

    public void LateUpdate(){
        Renderer.lateUpdate();
    }

    public void Draw(GraphicsContext gc){
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); /*clear the screen*/
        Renderer.draw(gc);
    }
}