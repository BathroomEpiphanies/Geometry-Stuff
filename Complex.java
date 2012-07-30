/* A class to represent Complex numbers
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Complex implements Serializable {
    private static final long serialVersionUID = 300L;
    
    final public static double   ALPHA = Math.PI/2;
    final public static Complex ZERO  = new Complex(0,0);
    final public static Complex ONE   = new Complex(1,0);
    final public static Complex UNIT  = new Complex(0,1);
    
    double a;
    double b;

    public Complex(double a, double b) {
	this.a = a;
	this.b = b;
    }

    /*  */
    public double norm() {
	return Math.sqrt(this.norm2());
    }

    /*  */
    public double norm2() {
	return a*a+b*b;
    }

    /*  */
    public double argument() {
	return Math.PI + this.toCoordinate().azimuth();
    }

    /*  */
    public Complex neg() { 
	return new Complex(-this.a, -this.b); 
    }

    /*  */
    public Complex conj() {
	return new Complex(this.a, -this.b);
    }

    /*  */
    public Complex add(Complex that) { 
	return new Complex(this.a+that.a, this.b+that.b); 
    }

    /*  */
    public Complex sub(Complex that) {
	return new Complex(this.a-that.a, this.b-that.b); 
    }

    /*  */
    public Complex mul(double mul) {
	return new Complex(this.a*mul, this.b*mul); 
    }

    /*  */
    public Complex mul(Complex that) {
	return new Complex(this.a*that.a-this.b*that.b, 
			   this.b*that.a+this.a*that.b); 
    }

    /*  */
    public Complex div(double div) {
	return new Complex(this.a/div, this.b/div); 
    }

    /*  */
    public Complex div(Complex that) {
	return this.mul(that.conj()).div(that.norm2());
    }

    /*  */
    public String toString() {
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
