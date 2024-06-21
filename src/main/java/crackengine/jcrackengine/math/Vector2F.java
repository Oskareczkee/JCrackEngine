package crackengine.jcrackengine.math;

/**
 * 2 Dimensional Vector representation with floating point (double) numbers
 */
public class Vector2F {
    public double x=0,y=0;

    public Vector2F(){
        x=0;
        y=0;
    }

    public Vector2F(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * @return returns new vector which values are opposite numbers eg. x=1 -> x=-1
     */
    public Vector2F opposite(){
        return new Vector2F(-x, -y);
    }


    /**
     * Sets vector x,y to opposite values e.g x=1 -> x=-1
     */
    public void setOpposite(){
        x=-x;
        y=-y;
    }

    public boolean equals(Vector2F v) {
        return x==v.x && y==v.y;
    }

    /*TODO add more math methods here, like angle, vector length*/
}
