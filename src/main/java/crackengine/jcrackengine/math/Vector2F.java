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

    public Vector2F(Vector2F v){
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2F(Coordinate c){
        this.x=c.x;
        this.y=c.y;
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

    public Vector2F add(Vector2F v){
        return new Vector2F(this.x + v.x,this.y + v.y);
    }

    public Vector2F sub(Vector2F v){
        return new Vector2F(this.x-v.x,this.y-v.y);
    }

    /**
     * Multiplies vector components by scalar v.x*s, v.y*s
     * @return reference to this vector
     */
    public Vector2F mul(double scalar){
        return new Vector2F(this.x*scalar,this.y*scalar);
    }

    /**
     * Divides vector components by scalar v.x/s v.y/s
     * @return reference to this vector
     */
    public Vector2F div(double scalar){
        return new Vector2F(this.x/scalar, this.y/scalar);
    }

    /**
     * @return dot product of this vector
     */
    public double dot(Vector2F v){
        return this.x*v.x+this.y*v.y;
    }

    /**
     * Calculates cross product of this vector and vector v
     * @return cross product (our vector is 2D, so cross product is scalar, z component of 3D vector)
     */
    public double cross(Vector2F v){
        return this.x*v.y-this.y*v.x;
    }

    /**
     * @return length of this vector
     */
    public double length(){
        return Math.sqrt(this.x*this.x+this.y*this.y);
    }

    /**
     * Calculates length of this vector without calculating square root of it
     * @return squared length of this vector
     */
    public double lengthSquared(){
        return this.x*this.x+this.y*this.y;
    }

    /**
     * Normalizes vector - normalized vector is a direction vector, each component is divided by vector length
     * @return normalized vector
     */
    public Vector2F normalize(){
        double length = this.length();
        return this.div(length);
    }

    /**
     * Returns normal vector to given vector
     * @return vector normal (perpendicular) to this vector
     */
    public Vector2F getNormal(){
        return new Vector2F(-this.y,this.x);
    }

    /**
     * Rotates vector by angle from (0,0) origin
     * @param angleDeg rotation angle
     * @return this vector rotated by degree
     */
    public Vector2F rotate(double angleDeg){
        double radians = Math.toRadians(angleDeg);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double x = this.x*cos - this.y*sin;
        double y = this.x*sin + this.y*cos;
        return new Vector2F(x,y);
    }

    /**
     * @return cosine of angle between 2 vectors
     */
    public double angleCos(Vector2F v){
        return this.dot(v)/(this.length()*v.length());
    }

    /**
     * Rotates vector by angle from given origin
     * @param angleDeg rotation angle
     * @param origin origin of rotation
     * @return point rotated by degree
     */
    public Vector2F rotate(double angleDeg, Vector2F origin){
        Vector2F translate = new Vector2F(x-origin.x,y-origin.y).rotate(angleDeg);
        return this.add(translate);
    }

    /**
     * @return Deep copy of this vector
     */
    public Vector2F copy(){
        return new Vector2F(this.x, this.y);
    }

    public boolean equals(Vector2F v) {
        return x==v.x && y==v.y;
    }

    public static Vector2F getNormal(double angleDeg){
        double angleRad = Math.toRadians(angleDeg);
        return new Vector2F(Math.sin(angleRad), -Math.cos(angleRad));
    }
}
