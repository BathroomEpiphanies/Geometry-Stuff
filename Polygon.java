/* A polygon is a vector of Coordinates representing its corners. The
 * last item in the vector should equal the first to represent a
 * closed shape. */

import java.io.Serializable;


public class Polygon implements Serializable {
    private static final long serialVersionUID = 200L;

    Coordinate[] corners;

    public Polygon(int corners) {
	this.corners = new Coordinate[corners];
    }

    public Polygon(Coordinate[] corners) {
	this.corners = corners;
    }

    public Polygon translate(Coordinate t) {
	Coordinate[] res = new Coordinate[this.corners.length];
	for(int i=0; i<this.corners.length; i++)
	    res[i] = this.corners[i].add(t);
	return new Polygon(res);
    }

    public Polygon scale(double s) {
	Coordinate[] res = new Coordinate[this.corners.length];
	for(int i=0; i<this.corners.length; i++)
	    res[i] = this.corners[i].scale(s);
	return new Polygon(res);
    }

    public Polygon rotate(double a) {
	Coordinate[] res = new Coordinate[this.corners.length];
	for(int i=0; i<this.corners.length; i++)
	    res[i] = this.corners[i].rotate(a);
	return new Polygon(res);
    }

    public boolean contains(Coordinate p) {
	double res = 0;
	for(int i=1; i<this.corners.length; i++)
	    res += this.corners[i].sub(p).angle(this.corners[i-1].sub(p));
	return Math.abs(res) > 6;
    }

    public int orientation() {
	return this.area()>0? 1: -1;
    }

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