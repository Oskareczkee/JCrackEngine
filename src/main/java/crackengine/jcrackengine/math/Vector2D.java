package crackengine.jcrackengine.math;

/**
 * 2 Dimensional Vector representation
 */
public class Vector2D {
    public double x=0,y=0;

    public Vector2D(){
        x=0;
        y=0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * @return returns new vector which values are opposite numbers eg. x=1 -> x=-1
     */
    public Vector2D opposite(){
        return new Vector2D(-x, -y);
    }


    /**
     * Sets vector x,y to opposite values e.g x=1 -> x=-1
     */
    public void setOpposite(){
        x=-x;
        y=-y;
    }

    /*TODO add more math methods here, like angle, vector length*/
}
