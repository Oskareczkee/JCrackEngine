package crackengine.jcrackengine.drawing.util;

import crackengine.jcrackengine.drawing.map.Tile;
import crackengine.jcrackengine.drawing.map.TileMap;
import crackengine.jcrackengine.math.Coordinate;
import crackengine.jcrackengine.math.Vector2I;

import java.util.*;

public class TileLabyrinthGenerator {
    static class QuadNode{
        public boolean[] sides = new boolean[4]; //4 sides starting from 0 - north, 1 - east, 2 - south 3 - west
        public boolean visited = false;
        Coordinate arrPos;
    }

    private Vector2I[] dirVec = new Vector2I[]{ //direction vectors for array movement
            new Vector2I(-1,0), //north
            new Vector2I(0,1), //east
            new Vector2I(1,0), //south
            new Vector2I(0,-1) //west
    };

    /*these are not private, so they can be shared with wall generators*/
    TileMap outMap;
    QuadNode[][] mapNodes;
    int width, height;
    Tile wallTile;
    int wallZIndex=2;
    int pathWidth=0, pathHeight=0;

    /**
     * Helper method, returns true when all settings in generator are set<br/>
     * Throws proper exceptions when something is not set
     */
    private void isReady() throws RuntimeException {
        if(width <=0 || height <=0) throw new RuntimeException("Tile Labyrinth Generator - width and height must be set and be greater than 0");
        if(wallTile == null) throw new RuntimeException("Tile Labyrinth Generator - Wall Tile is not set");
        if(pathWidth <=0 || pathHeight <=0) throw new RuntimeException("Tile Labyrinth Generator - path width or path height must be set and be greater than 0");
    }

    private void prepareForGeneration(){
        mapNodes = new QuadNode[width][height];
        for(int x = 0; x < height; x++)
            for(int y = 0; y < width; y++){
                mapNodes[x][y] = new QuadNode();
                Arrays.fill(mapNodes[x][y].sides, true); /*set all sides to true (they have 4 walls actually, we will delete these walls with algorithms)*/
                mapNodes[x][y].arrPos = new Coordinate(x,y);
            }
    }

    private int vectorToSideIndex(Vector2I vec){
        if(vec.equals(dirVec[0])) return 0;
        if(vec.equals(dirVec[1])) return 1;
        if(vec.equals(dirVec[2])) return 2;
        if(vec.equals(dirVec[3])) return 3;
        return -1;
    }
    private boolean isProperCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    /**
     * Generates labyrinth using iterative Randomized depth first search algorithm
     * @return reference to the generator with generated map
     */
    public TileLabyrinthGenerator generateRDFS(){
        isReady();
        prepareForGeneration();

        Random rd = new Random();
        Stack<QuadNode> stack = new Stack<>();

        int cX = rd.nextInt(0,height); //current X
        int cY = rd.nextInt(0,height); //current Y
        QuadNode startNode = mapNodes[cX][cY];

        startNode.visited=true;
        stack.push(startNode);
        QuadNode currentNode;

        while(!stack.isEmpty()){
            currentNode = stack.pop();
            cX = (int) currentNode.arrPos.x;
            cY = (int) currentNode.arrPos.y;

            List<QuadNode> unvisitedNeighbors = new ArrayList<QuadNode>(4);
            for(var vec : dirVec){
                if(!isProperCoordinate(cX + vec.x, cY + vec.y)) continue;
                if(mapNodes[cX + vec.x][cY + vec.y].visited) continue;

                unvisitedNeighbors.add(mapNodes[cX + vec.x][cY + vec.y]);
            }

            if(unvisitedNeighbors.isEmpty()) continue;

            stack.push(currentNode);

            QuadNode neighbor = unvisitedNeighbors.get(rd.nextInt(unvisitedNeighbors.size()));
            Vector2I wallDirection = new Vector2I((int) (neighbor.arrPos.x - cX), (int) (neighbor.arrPos.y - cY));
            Vector2I oppositeWall = wallDirection.opposite();

            currentNode.sides[vectorToSideIndex(wallDirection)]=false;
            neighbor.sides[vectorToSideIndex(oppositeWall)]=false;
            neighbor.visited=true;
            stack.push(neighbor);
        }

        return this;
    }


    public TileMap getTileMap(){
        if(mapNodes==null) throw new RuntimeException("Map has not been yet generated");
        StretchWallGenerator gen = new StretchWallGenerator(this);
        var map = gen.generate();

        outMap.setMap(map);
        return outMap;
    }

    /*SETTERS*/

    public TileLabyrinthGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public TileLabyrinthGenerator setWidth(int width){
        this.width = width;
        return this;
    }
    public TileLabyrinthGenerator setHeight(int height){
        this.height = height;
        return this;
    }
    public TileLabyrinthGenerator setPathSize(int width, int height){
        this.pathWidth = width;
        this.pathHeight = height;
        return this;
    }
    public TileLabyrinthGenerator setWallTile(Tile tile){
        this.wallTile = tile;
        return this;
    }
    public void setWallZIndex(int wallZIndex) {
        this.wallZIndex = wallZIndex;
    }


    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Tile getWallTile(){
        return wallTile;
    }

    public int getWallZIndex() {
        return wallZIndex;
    }

    public int getPathWidth(){
        return pathWidth;
    }
    public int getPathHeight(){
        return pathHeight;
    }
}
