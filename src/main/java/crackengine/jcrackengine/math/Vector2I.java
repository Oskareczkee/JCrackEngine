package crackengine.jcrackengine.math;

/**
 * 2 Dimensional Vector representation with integer (int) numbers
 */
public class Vector2I {
    public int x=0,y=0;

    public Vector2I(){
        x=0;
        y=0;
    }

    public Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * @return returns new vector which values are opposite numbers eg. x=1 -> x=-1
     */
    public Vector2I opposite(){
        return new Vector2I(-x, -y);
    }


    /**
     * Sets vector x,y to opposite values e.g x=1 -> x=-1
     */
    public void setOpposite(){
        x=-x;
        y=-y;
    }

    public boolean equals(Vector2I v) {
        return x==v.x && y==v.y;
    }

    /*TODO add more math methods here, like angle, vector length*/
}
