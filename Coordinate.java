/* A Coordinateinate is simply x and y values in the plane 
 *
 */

import java.io.Serializable;
import java.util.List;
import java.util.Iterator;

public class Coordinate implements Serializable {
    private static final long serialVersionUID = 100L;

    final public static Coordinate X = new Coordinate(1,0);
    final public static Coordinate Y = new Coordinate(0,1);

    double x;
    double y;

    public Coordinate(double x, double y) {
	this.x = x;
	this.y = y;
    }

    /* The Euclidean norm of the vector represented by this
     * Coordinate. 
     */
    public double magnitude() {
	return Math.sqrt(this.dot(this));
    }

    /* The azimuth of the vector represented by this Coordinate, in
     * the interval [-π,π). 
     */
    public double azimuth() {
	return X.angle(this);
    }

    /* Returns true if that Coordinate is within the distance
     * precision of this Coordinate, false otherwise. 
     */
    public Boolean within(Coordinate that, double prec) {
	return this.distance(that) <= prec;
    }
    
    /* The Euclidean distance from this Coordinate to that
     * Coordinate. 
     */
    public double distance(Coordinate that) {
	return that.sub(this).magnitude();
    }

    /* The bearing to that Coordinate relative to this Coordinate, in
     * the interval [-π,π). 
     */
    public double bearing(Coordinate that) {
	return that.sub(this).azimuth();
    }

    /* The angle between this and that coordinate with the vertex at
     * the origin, in the interval [-π,π).
     */
    public double angle(Coordinate that) {
	double res = Math.acos(this.dot(that)/this.magnitude()/that.magnitude());
	return this.cross(that) < 0? res: -res;
    }

    /* Returns a new Coordinate representing the mirror, through the
     * origin, of this Coordinate.
     */
    public Coordinate neg() {
	return new Coordinate(-this.x, -this.y);
    }

    /* Returns a new Coordinate representing the vector sum of the
     * vectors represented by this and that Coordinate.
     */
    public Coordinate add(Coordinate that) {
	return new Coordinate(this.x+that.x, this.y+that.y);
    }

    /* Returns a new Coordinate representing the vector difference
     * between the vectors represented by this and that Coordinate.
     */
    public Coordinate sub(Coordinate that) {
	return new Coordinate(this.x-that.x, this.y-that.y);
    }

    /* Returns a new Coordinate representing the scalar product of
     * this Coordinate and scale.
     */
    public Coordinate scale(double scale) {
	return new Coordinate(scale*this.x, scale*this.y);
    }

    /* Returns a new Coordinate representing the vector this rotated
     * angle radians around the origin.
     */
    public Coordinate rotate(double angle) {
	return new Coordinate(this.x*Math.cos(angle)-this.y*Math.sin(angle), 
			      this.x*Math.sin(angle)+this.y*Math.cos(angle));
    }

    /* Returns the Euclidean norm of the cross product between the
     * vectors represented by this and that Coordinate.
     */
    public double cross(Coordinate that) {
	return this.x*that.y - this.y*that.x;
    }

    /* Returns the dot product between the vectors represented by this
     * and that Coordinate.
     */
    public double dot(Coordinate that) {
	return this.x*that.x + this.y*that.y;
    }

    public String toString() {
	return String.format("(%.2f,%.2f)", this.x, this.y);
    }

    public boolean inAny(List<Polygon> polygons) {
	for(Polygon p: polygons)
	    if(p.contains(this))
		return true;
	return false;
    }
}
