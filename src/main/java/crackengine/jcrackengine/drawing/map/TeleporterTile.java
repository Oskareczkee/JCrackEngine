package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.GameApplication;
import crackengine.jcrackengine.core.Audio;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;
import crackengine.jcrackengine.math.Coordinate;

/**
 * Created for test, teleports player
 */
public class TeleporterTile extends StaticCollidableTile {
    private Coordinate teleportPosition= new Coordinate(0,0);
    public TeleporterTile(String texturePath, Coordinate pos) {
        super(texturePath, pos);
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
    public void onCollided(StaticCollidable object) {
        GameApplication.Audio.playSound(new Audio("/Sounds/portal.mp3"));
        ((GameObject)object).position = new Coordinate(teleportPosition.x, teleportPosition.y);
    }
}
