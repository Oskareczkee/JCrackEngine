package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.drawing.Coordinate;
import crackengine.jcrackengine.drawing.Drawable;
import crackengine.jcrackengine.drawing.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Tile extends GameObject implements Drawable {
    private final Image texture;
    private double width, height;

    public Tile(String texturePath){
        String externalPath = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
        this.texture = new Image(externalPath);
        this.position = new Coordinate(0,0);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Tile(Coordinate position, String texturePath) {
        String externalPath = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
        this.texture = new Image(externalPath);
        this.position = position;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Tile(Coordinate position, String texturePath, double width, double height) {
        String externalPath = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
        this.texture = new Image(externalPath, width, height, false, false);
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Tile(Tile tile, double width, double height){
        String imagePath = tile.texture.getUrl();
        this.texture = new Image(imagePath,width,height,false,false);

        this.width = width;
        this.height = height;
        this.position = new Coordinate(tile.position.x, tile.position.y); /*create new coordinate, otherwise reference will be used*/
    }

    public Tile(Tile tile, Coordinate coord, double width, double height){
        String imagePath = tile.texture.getUrl();
        this.texture = new Image(imagePath,width,height,false,false);

        this.width = width;
        this.height = height;
        this.position = new Coordinate(coord.x, coord.y);/*create new coordinate, otherwise reference will be used*/
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(texture, lastOnCameraPosition.x, lastOnCameraPosition.y);
    }
}
