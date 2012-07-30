/* A polygon is a vector of Coordinates representing its corners. The
 * last item in the vector should equal the first to represent a
 * closed shape. */

import java.io.Serializable;

public class Polygon implements Serializable {
    private static final long serialVersionUID = 200L;

    Coordinate[] corners;

    /* The corners of a Polygon is an ordered vector of Coordinates.
     */
    public Polygon(Coordinate[] corners) {
	this.corners = corners;
    }

    /* Returns a new Polygon where every corner is translated by
     * adding the Coordinate t.
     */
    public Polygon translate(Coordinate t) {
	Coordinate[] res = new Coordinate[this.corners.length];
	for(int i=0; i<this.corners.length; i++)
	    res[i] = this.corners[i].add(t);
	return new Polygon(res);
    }

    /* Returns a new Polygon where all corners Coordinates are scaled
     * by the factor s.
     */
    public Polygon scale(double s) {
	Coordinate[] res = new Coordinate[this.corners.length];
	for(int i=0; i<this.corners.length; i++)
	    res[i] = this.corners[i].scale(s);
	return new Polygon(res);
    }

    /* Returns a new Polygon where every corner is rotated the angle a
     * around the origo.
     */
    public Polygon rotate(double a) {
	Coordinate[] res = new Coordinate[this.corners.length];
	for(int i=0; i<this.corners.length; i++)
	    res[i] = this.corners[i].rotate(a);
	return new Polygon(res);
    }

    /* Returns true if the Coordinate p lies within the Polygon. 
     */
    public boolean contains(Coordinate p) {
	double res = 0;
	for(int i=1; i<this.corners.length; i++)
	    res += this.corners[i-1].sub(p).angle(this.corners[i].sub(p));
	return Math.abs(res) > 6;
    }

    /* Returns 1 if the orientation of the corners is in positive
     * direction, -1 else.
     */
    public int orientation() {
	return this.area()>0? 1: -1;
    }

    /* Returns the area of the Polygon, this is negative if the
     * orientation of the corners is in negative order.
     */
    public double area() {
	double area = 0;
	for(int i=1; i<this.corners.length; i++)
	    area += this.corners[i-1].cross(this.corners[i]);
	return area;
    }

    public String toString() {
	String res = "[Polygon";
	for(int i=0; i<corners.length && i<4; i++)
	    res += " " + corners[i].toString();
	return res+"]\n";
    }
}