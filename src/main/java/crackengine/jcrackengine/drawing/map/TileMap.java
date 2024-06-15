package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.core.WorldRenderer;
import crackengine.jcrackengine.drawing.Coordinate;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TileMap {
    static int ID=0;
    private static HashMap<Integer, Tile> tiles = new HashMap<>();
    private List<List<Tile>> map = new ArrayList<List<Tile>>();
    private int TileWidth=1, TileHeight=1;

    public static void addTile(Tile tile) {
        tiles.put(ID++, tile);
    }

    public static void addTile(int id, Tile tile) {
        tiles.put(id, tile);
    }

    public static Tile getTile(int id) {
        return tiles.get(id);
    }

    public TileMap(int tileWidth, int tileHeight) {
        this.TileWidth = tileWidth;
        this.TileHeight = tileHeight;
    }

    public int getTileWidth() {
        return TileWidth;
    }

    public TileMap setTileWidth(int tileWidth) throws InvalidParameterException {
        if(tileWidth < 0) throw new InvalidParameterException("Tile width cannot be negative!");
        TileWidth = tileWidth;
        return this;
    }

    public int getTileHeight() {
        return TileHeight;
    }

    public TileMap setTileHeight(int tileHeight) throws InvalidParameterException {
        if(tileHeight < 0) throw new InvalidParameterException("Tile height cannot be negative!");
        TileHeight = tileHeight;
        return this;
    }

    public TileMap loadFromFile(String filename) throws RuntimeException {
        InputStream resource = Objects.requireNonNull(getClass().getResourceAsStream(filename));

        try(BufferedReader br = new BufferedReader(new InputStreamReader(resource))){
            int row=0;
            String line="";
            while((line=br.readLine())!=null){
                map.add(new ArrayList<Tile>());
                var tilesSplit = line.split(" ");
                for (String s : tilesSplit) {
                    int parsedID = Integer.parseInt(s);
                    if (!tiles.containsKey(parsedID))
                        throw new RuntimeException("Tile with id: " + parsedID + " has not been yet added");
                    map.get(row).add(tiles.get(parsedID));
                }
                row++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return this;
    }

    public void render(final WorldRenderer renderer){
        Coordinate actualCoordinate = new Coordinate(0,0);
        for (List<Tile> tileList : map) {
            for (Tile tile : tileList) {
                renderer.addBackgroundObject(new Tile(tile, actualCoordinate,TileWidth, TileHeight));
                actualCoordinate.x+=TileWidth;
            }
            actualCoordinate.x=0;
            actualCoordinate.y+=TileHeight;
        }
    }

    public void render(final WorldRenderer renderer, Coordinate startingCoordinate){
        Coordinate actualCoordinate = startingCoordinate;
        for (List<Tile> tileList : map) {
            for (Tile tile : tileList) {
                renderer.addBackgroundObject(new Tile(tile, actualCoordinate,TileWidth, TileHeight));
                actualCoordinate.x+=TileWidth;
            }
            actualCoordinate.x= startingCoordinate.x;
            actualCoordinate.y+=TileHeight;
        }
    }
}
