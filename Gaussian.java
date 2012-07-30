/* A class to represent Gaussian integers Gaussian integers are
   complex numbers on the form a + bi, a,b integers.

   Complex numbers have no total ordering.  Here they are ordered by
   their norm, and argument if the norms are equal.x */

import java.io.Serializable;
import java.util.ArrayList;

public class Gaussian implements Comparable<Gaussian>, Serializable {
    private static final long serialVersionUID = 300L;
    
    final public static double   ALPHA = Math.PI/2;
    final public static Gaussian ZERO  = new Gaussian(0,0);
    final public static Gaussian ONE   = new Gaussian(1,0);
    final public static Gaussian UNIT  = new Gaussian(0,1);
    
    int a;
    int b;

    public Gaussian(int a, int b) {
	this.a = a;
	this.b = b;
    }

    /* Complex numbers are ordered! promise...
     */
    public int compareTo(Gaussian that) {
	if(this.equals(that))
	   return 0;
	else if(this.norm2() == that.norm2())
	    return this.argument() > that.argument()? 1: -1;
	else
	    return this.norm2()-that.norm2();
    }

    /* They can at least be equal.
     */
    public Boolean equals(Gaussian that) {
	return this.a == that.a && this.b == that.b;
    }

    /* Scans through the first quadrant.
     */
    public Gaussian inc() {
	if(this.equals(ZERO))
	    return(ONE);
	if(this.a == 1)
	    return new Gaussian(this.b+2, 0);
	else
	    return new Gaussian(this.a-1, this.b+1);
    }

    /* Returns the square of the Euclidean norm.
     */
    public int norm2() {
	return a*a+b*b;
    }

    /*  */
    public double argument() {
	return Math.PI + this.toCoordinate().azimuth();
    }

    /*  */
    public Gaussian neg() { 
	return new Gaussian(-this.a, -this.b); 
    }

    /*  */
    public Gaussian conj() {
	return new Gaussian(this.a, -this.b);
    }

    /*  */
    public Gaussian add(Gaussian that) { 
	return new Gaussian(this.a+that.a, this.b+that.b); 
    }

    /*  */
    public Gaussian sub(Gaussian that) {
	return new Gaussian(this.a-that.a, this.b-that.b); 
    }

    /*  */
    public Gaussian mul(int mul) {
	return new Gaussian(this.a*mul, this.b*mul); 
    }

    /*  */
    public Gaussian mul(Gaussian that) {
	return new Gaussian(this.a*that.a-this.b*that.b, 
			    this.b*that.a+this.a*that.b); 
    }

    /*  */
    public Gaussian div(int div) {
	return new Gaussian(this.a/div, this.b/div); 
    }

    /*  */
    public Gaussian div(Gaussian that) {
	return this.mul(that.conj()).div(that.norm2());
    }

    /* This may be bit questionable...
     */
    public Gaussian mod(int div) {
	return new Gaussian(this.a%div, this.b%div); 
    }

    /* This may be bit questionable...
     */
    public Gaussian mod(Gaussian that) {
	int norm = that.norm2();
	Gaussian prod = this.mul(that.conj());
	return new Gaussian(prod.a%norm, prod.b%norm);
    }

    /* Fancy formatted printing string.
     */
    public String toString() {
	String format = "";
	if(this.a == 0)
	    if(this.b == -1)
		return "-i";
	    else if(this.b == 0)
		return "0";
	    else if(this.b == 1)
		return "i";
	    else
		return String.format("%di", this.b);
	else
	    if(this.b == -1)
		return String.format("%d-i", this.a);
	    else if(this.b == 0)
		return String.format("%d", this.a);
	    else if(this.b == 1)
		return String.format("%d+i", this.a);
	    else
		return String.format(this.b<0? "%d%di": "%d+%di", this.a, this.b);
    }

    /* Returns a string representing the complex number in a+bi form.
     */
    public String toComplexString() {
	if(this.b>=0)
	    return String.format("%.3f +%.3fi", this.a, this.b);
	else
	    return String.format("%.3f %.3fi", this.a, this.b);
    }

    /* Returns the Coordinate (a,b).
     */
    public Coordinate toCoordinate() {
	return new Coordinate(this.a, this.b);
    }

    /*  */
    public boolean isPrime() {
	return false;
    }
}
