package crackengine.jcrackengine.drawing.map;

import crackengine.jcrackengine.core.WorldRenderer;
import crackengine.jcrackengine.drawing.GameObject;
import crackengine.jcrackengine.drawing.interfaces.Drawable;
import crackengine.jcrackengine.math.Coordinate;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>2 dimensional tile map representation<br/>
 * Each tile should have its unique id, and can be added to the tile map</br><br/>
 * Map is a text file that has tile ids separated by space, each id is loaded, and proper tile is mapped<br/>
 * </p>
 * Map can look like this: <br/>
 * 0 1 0 1<br/>
 * 0 0 0 0<br/>
 * 1 1 1 1<br/>
 * 2 3 3 4<br/>
 * Which represents 4x4 map, which uses tiles with ids from 0 to 4
 */
public class TileMap {
    /*Todo: improve map format
    *  Number one: load tiles from another file -> example: 0 /Tiles/Grass.png should load tile from file and give it index 0
    *  Numver two: before map loads, in map file there should be info which tiles will be needed and where to find them*/
    static int ID=0;
    private static HashMap<Integer, Tile> tiles = new HashMap<>();
    private List<List<Tile>> map = new ArrayList<List<Tile>>();
    private int TileWidth=1, TileHeight=1;

    /**
     * Adds to to the tile map, id is added automatically
     * @param tile tile
     */
    public static void addTile(Tile tile) {
        tiles.put(ID++, tile);
    }

    /**
     * Adds tile to the tile map, you can set you own id for this tile
     * @param id id of tile
     * @param tile tile
     */
    public static void addTile(int id, Tile tile) {
        tiles.put(id, tile);
    }

    /**
     * @param id 0 based index
     * @return tiles with given id, null if index is wrong
     */
    public static Tile getTile(int id) {
        return tiles.get(id);
    }

    public TileMap(int tileWidth, int tileHeight) {
        this.TileWidth = tileWidth;
        this.TileHeight = tileHeight;
    }

    public TileMap(){}

    public int getTileWidth() {
        return TileWidth;
    }

    public TileMap setTileWidth(int tileWidth) throws InvalidParameterException {
        if(tileWidth < 0) throw new InvalidParameterException("Tile width cannot be negative!");
        TileWidth = tileWidth;
        return this;
    }

    /**
     * Allocates enough memory in list to store map of given width and height<br/>
     * This is used for example in TileLabirynthGenerator, to preallocate enough space for map
     * @param width width of map
     * @param height height of map
     * @return reference to this object
     */
    public TileMap preallocateSpace(int width, int height){
            map = new ArrayList<List<Tile>>(height);
            for(int i=0; i<height; i++)
                map.add(new ArrayList<>(width));

            return this;
    }

    /**
     * Returns map object, which can be changed<br/>
     * This is mainly used for map generators, to change map properties
     * @return reference to the map tile list
     */
    public List<List<Tile>> getMap() {
        return map;
    }

    /**
     * sets given map as new map stored in the object
     * @param map map reference
     */
    public void setMap(List<List<Tile>> map) {
        this.map = map;
    }

    public int getTileHeight() {
        return TileHeight;
    }

    public TileMap setTileHeight(int tileHeight) throws InvalidParameterException {
        if(tileHeight < 0) throw new InvalidParameterException("Tile height cannot be negative!");
        TileHeight = tileHeight;
        return this;
    }

    /**
     * Loads map from given file
     * @param filename relative path to the map in resources
     * @return Tile Map with loaded tiles
     * @throws RuntimeException if tile with given id has not been added to the tile map
     */
    public TileMap loadFromFile(String filename) throws RuntimeException {
        InputStream resource = Objects.requireNonNull(getClass().getResourceAsStream(filename));

        try(BufferedReader br = new BufferedReader(new InputStreamReader(resource))){
            int row=0;
            int cX=0;
            int cY=0;
            String line="";
            while((line=br.readLine())!=null){
                map.add(new ArrayList<>());
                var tilesSplit = line.split(" ");
                for (String s : tilesSplit) {
                    int parsedID = Integer.parseInt(s);
                    if (!tiles.containsKey(parsedID))
                        throw new RuntimeException("Tile with id: " + parsedID + " has not been yet added");
                    map.get(row).add(new Tile(tiles.get(parsedID)).
                                    setPosition(new Coordinate(cX, cY)).
                                    setSize(TileWidth, TileHeight));
                    cX+=TileWidth;
                }
                cX=0;
                cY+=TileHeight;
                row++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Adds tiles to the given renderer. Offset is not set and rendering will start at (0,0) coordinate
     * @param renderer Renderer which should render this map
     */
    public void render(final WorldRenderer renderer){
        for (List<Tile> tileList : map) {
            for (Tile tile : tileList) {
                if(tile==null) continue;
                renderer.addBackgroundObject(tile);
            }
        }
    }

    /**
     * Add tiles to the given renderer, but applies offset relative to the (0,0) point
     * @param renderer Renderer which should render this map
     * @param startingCoordinate Offset at which map should render (Normally starts at (0,0))
     */
    public void render(final WorldRenderer renderer, Coordinate startingCoordinate){
        for (List<Tile> tileList : map) {
            for (Tile tile : tileList) {
                if(tile==null) continue;
                Coordinate offsetCoordinate = new Coordinate(tile.position.x+startingCoordinate.x, tile.position.y+startingCoordinate.y);
                Tile positionedTile = tile.makeCopy();
                positionedTile.setPosition(offsetCoordinate);
                renderer.addBackgroundObject(positionedTile);
            }
        }
    }
}
