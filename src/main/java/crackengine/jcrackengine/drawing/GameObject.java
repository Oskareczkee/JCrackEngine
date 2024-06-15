package crackengine.jcrackengine.drawing;

public class GameObject {
    public Coordinate position;
    public Coordinate lastOnCameraPosition = new Coordinate(0,0);
    protected double width, height;
    public double getWidth(){return 1;}
    public double getHeight(){return 1;}
}
