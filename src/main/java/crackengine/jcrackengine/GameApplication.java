package crackengine.jcrackengine;

import crackengine.jcrackengine.core.Camera;
import crackengine.jcrackengine.core.KeyHandler;
import crackengine.jcrackengine.core.WorldRenderer;
import crackengine.jcrackengine.drawing.Coordinate;
import crackengine.jcrackengine.drawing.Player;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameApplication extends Application{
    public static String GameName = "Your game name";

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static int CAMERA_SCALE=1;
    public static int UPDATE_FRAMERATE=60;

    public static  WorldRenderer Renderer; /*it also manages updates*/
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

        /*update loop init*/
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/UPDATE_FRAMERATE), e->{
            EarlyUpdate();
            Update();
            LateUpdate();
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();

        Renderer = new WorldRenderer();
        Setup();

        Stage = new Stage();
        this.Stage.setTitle(GameName);
        this.Stage.setScene(Scene);
    }

    public void Setup(){
        Player player = new Player();
        this.Camera.attach(player).setBound(new Rectangle2D(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
        Renderer.addCamera(this.Camera);
        Renderer.addObject(player);
        TileMap.addTile(new Tile("/Tiles/Grass.png"));
        new TileMap(64,64).
            loadFromFile("/Maps/TestMap").
            render(Renderer);
        Renderer.addObject(new Tile(new Coordinate(32,32),"/Tiles/Totem.png"));
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