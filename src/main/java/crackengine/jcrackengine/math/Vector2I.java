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

    public Vector2I add(Vector2I v){
        this.x+=v.x;
        this.y+=v.y;
        return this;
    }

    /**
     * Multiplies vector components by scalar v.x*s, v.y*s
     * @return reference to this vector
     */
    public Vector2I mul(int scalar){
        this.x*=scalar;
        this.y*=scalar;
        return this;
    }

    /**
     * Divides vector components by scalar v.x/s v.y/s
     * @return reference to this vector
     */
    public Vector2I div(int scalar){
        this.x/=scalar;
        this.y/=scalar;
        return this;
    }

    /**
     * @return dot product of this vector
     */
    public double dot(Vector2I v){
        return this.x*v.x+this.y*v.y;
    }

    /**
     * Calculates cross product of this vector and vector v
     * @return cross product (our vector is 2D, so cross product is scalar, z component of 3D vector)
     */
    public double cross(Vector2I v){
        return this.x*v.y-this.y*v.x;
    }

    /**
     * @return length of this vector
     */
    public double length(){
        return Math.sqrt(this.x*this.x+this.y*this.y);
    }

    /**
     * Normalizes vector - normalized vector is a direction vector, each component is divided by vector length
     * @return normalized vector
     */
    public Vector2I normalize(){
        double length = this.length();
        return this.div((int) length);
    }

    /**
     * @return Deep copy of this vector
     */
    public Vector2I copy(){
        return new Vector2I(this.x, this.y);
    }

    public boolean equals(Vector2I v) {
        return x==v.x && y==v.y;
    }
}
