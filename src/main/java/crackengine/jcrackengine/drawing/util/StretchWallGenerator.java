package crackengine.jcrackengine.drawing.util;

import crackengine.jcrackengine.drawing.map.Tile;
import crackengine.jcrackengine.drawing.map.TileMap;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2I;
import crackengine.jcrackengine.physics.interfaces.StaticCollidable;

import java.util.Arrays;
import java.util.List;

/**
 * Generates wall for Tile Labirynth Generator <br/>
 * Walls with textures are stretched to fit <br/>
 * This produces minimum amount of walls, but since texture is stretched it may not look good in some cases
 */
class StretchWallGenerator {

    private static class TileWallInfo{
        boolean[] isAdded = new boolean[4];
    }
    private TileLabyrinthGenerator g;
    private final Vector2I[] dirVec = new Vector2I[]{ //direction vectors for array movement
            new Vector2I(-1,0), //north
            new Vector2I(0,1), //east
            new Vector2I(1,0), //south
            new Vector2I(0,-1) //west
    };
    private TileWallInfo[][] addedWalls;

    public StretchWallGenerator(TileLabyrinthGenerator generator) {
        this.g=generator;
    }

    public List<List<Tile>> generate(){
        if(g.mapNodes==null) throw new RuntimeException("Map has not been yet generated");
        prepareForGeneration();

        g.outMap = new TileMap();
        g.outMap.preallocateSpace(g.width*3, g.height*3);
        var map = g.outMap.getMap();

        Coordinate coord = new Coordinate(0,0);
        //add walls
        for(int x = 0; x < g.height; x++){
            for(int y = 0; y < g.width; y++){
                for(int i =0;i<4;i++){
                    if(!g.mapNodes[x][y].sides[i] || hasWallBeenAdded(i,x,y)) continue;

                    Coordinate wallCoord = getWallPosition(i, coord);
                    long fill = (i==1 && g.mapNodes[x][y].sides[2]) ? (long) g.wallTile.getHeight():0; //strech tile in this one case

                    Tile tile = g.wallTile.makeCopy().setPosition(new Coordinate(wallCoord.x, wallCoord.y)).setWidth(g.wallTile.getWidth() + fill);
                    tile.setZIndex(g.wallZIndex);
                    rotateTile(tile, dirVec[i]);
                    map.get(x).add(tile);
                    addedWalls[x][y].isAdded[i] = true;
                }

                coord.x+= g.pathWidth;
            }

            coord.x=0;
            coord.y+= g.pathHeight;
        }

        return map;
    }

    private void prepareForGeneration(){
        addedWalls = new TileWallInfo[g.width*3][g.height*3];
        for(int x = 0; x < g.height; x++)
            for(int y = 0; y < g.width; y++){
                addedWalls[x][y] = new TileWallInfo();
                Arrays.fill(addedWalls[x][y].isAdded, false); /*set all sides to true (they have 4 walls actually, we will delete these walls with algorithms)*/
            }
    }

    private int getOppositeDirection(int direction){
        if(direction==0) return 2;
        if(direction==1) return 3;
        if(direction==2) return 0;
        if(direction==3) return 1;

        return 0;
    }

    private boolean hasWallBeenAdded(int direction, int x, int y){
        int opposite = getOppositeDirection(direction);
        var directionVec = dirVec[direction];

        if(isProperCoordinate(x+directionVec.x, y+directionVec.y))
            return addedWalls[x + directionVec.x][y + directionVec.y].isAdded[opposite];

        return false;
    }

    private Coordinate getWallPosition(int direction, Coordinate coord) {
        //positioning
        long wallX = 0;
        long wallY = 0;
        if (direction == 0 || direction==3) { //up and down
            wallX = (long) coord.x;
            wallY = (long) coord.y;
        } else if (direction == 1 ) { //right
            wallX = (long) (coord.x + g.pathWidth);
            wallY = (long) coord.y;
        } else if (direction == 2) { //left
            wallX = (long) coord.x;
            wallY = (long) (coord.y + g.pathHeight);
        }


        return new Coordinate(wallX, wallY);
    }

    private void rotateTile(Tile tile, Vector2I vec){
        if(vec.equals(dirVec[0]) || vec.equals(dirVec[2])) return;
        tile.rotate();
    }

    private boolean isProperCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < g.height && y < g.width;
    }
}
