package crackengine.jcrackengine.tests;

import crackengine.jcrackengine.GameApplication;
import crackengine.jcrackengine.core.Audio;
import crackengine.jcrackengine.core.GameObject;
import crackengine.jcrackengine.drawing.map.StaticCollidableTile;
import crackengine.jcrackengine.drawing.map.Tile;
import crackengine.jcrackengine.physics.collision.Collider;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;
import crackengine.jcrackengine.math.Coordinate;

/**
 * Created for test, teleports player
 */
public class TeleporterTile extends StaticCollidableTile {
    private Coordinate teleportPosition= new Coordinate(0,0);
    public TeleporterTile(String texturePath, Coordinate pos) {
        super(texturePath, pos);
        setTrigger(true);
    }

    public TeleporterTile(String texturePath) {
        super(texturePath);
    }

    public TeleporterTile(Tile tile) {
        super(tile);
    }

    public TeleporterTile(Tile tile, Coordinate pos) {
        super(tile, pos);
    }

    public TeleporterTile setTeleportPosition(Coordinate position){
        this.teleportPosition = position;
        return this;
    }

    @Override
    public void onCollided(Collider collider) {
        var object = collider.getEntity();
        GameApplication.Audio.playSound(new Audio("/Sounds/portal.mp3"));
        object.position = new Coordinate(teleportPosition.x, teleportPosition.y);
    }
}
