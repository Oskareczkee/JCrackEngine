package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.core.GameObject;
import crackengine.jcrackengine.drawing.interfaces.Copyable;
import crackengine.jcrackengine.drawing.interfaces.Drawable;
import crackengine.jcrackengine.math.Coordinate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Tiles are rectangles with texture, from which tile map can be drawn
 */
public class Tile extends GameObject implements Copyable, Drawable {
    protected Image texture;
    protected double width, height;

    public Tile(String texturePath){
        String externalPath = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
        this.texture = new Image(externalPath);
        this.position = new Coordinate(0,0);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Tile(String texturePath, Coordinate position) {
        String externalPath = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
        this.texture = new Image(externalPath);
        this.position = position;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Tile(Tile tile){
        this.width=tile.width;
        this.height=tile.height;

        String externalPath = tile.texture.getUrl();
        this.texture = new Image(externalPath, width, height,false,false);
        this.zIndex = tile.zIndex;
    }

    public Tile(Tile tile, Coordinate position){
        this.width=tile.width;
        this.height=tile.height;

        String externalPath = tile.texture.getUrl();
        this.texture = new Image(externalPath, width, height,false,false);
        this.position = position;
        this.zIndex = tile.zIndex;
    }

    private void resizeTexture(){
        texture = new Image(texture.getUrl(), width, height, false, false);
    }

    public Tile setPosition(Coordinate position){
        this.position = position;
        return this;
    }

    public Tile setTexture(String texturePath){
        String externalPath = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
        this.texture = new Image(externalPath, width, height, false, false);
        return this;
    }

    public Tile setWidth(double width){
        this.width = width;
        resizeTexture();
        return this;
    }

    public Tile setHeight(double height){
        this.height = height;
        resizeTexture();
        return this;
    }

    public Tile setSize(double width, double height){
        this.width = width;
        this.height = height;
        resizeTexture();

        return this;
    }

    /**
     * Rotates tile by 90 degrees <br/>
     * This function simply swaps height and width of the texture
     * @return reference to the rotated tile
     */
    public Tile rotate(){
        double r = this.height;
        this.height = this.width;
        this.width = r;

        resizeTexture();
        return this;
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

    @Override
    public Tile makeCopy() {
        return new Tile(this);
    }
}
