/* A class to represent Eisenstein integers
   Eisenstein integers are complex numbers
   on the form a + bω, where ω = e^(⅔πi) and
   a,b are integers. Note that ω² = -1 - ω.

   Complex numbers have no total ordering.
   Here they are ordered by their norm, and
   argument if the norms are equal. 
*/

import java.io.Serializable;
import java.util.ArrayList;

public class Eisenstein implements Comparable<Eisenstein>, Serializable {
    private static final long serialVersionUID = 300L;
    
    final public static double     ALPHA = 2*Math.PI/3;
    final public static Eisenstein ZERO  = new Eisenstein(0,0);
    final public static Eisenstein ONE   = new Eisenstein(1,0);
    final public static Eisenstein OMEGA = new Eisenstein(0,1);
    final public static Eisenstein OMEG2 = new Eisenstein(-1,-1);

    int a;
    int b;

    public Eisenstein(int a, int b) {
	this.a = a;
	this.b = b;
    }

    /* Complex numbers are ordered! promise...
     */
    public int compareTo(Eisenstein that) {
	if(this.equals(that))
	   return 0;
	else if(this.norm2() == that.norm2())
	    return this.argument() > that.argument()? 1: -1;
	else
	    return this.norm2()-that.norm2();
    }

    /* They can at least be equal.
     */
    public Boolean equals(Eisenstein that) {
	return this.a == that.a && this.b == that.b;
    }

    /* Scans through the first "triant" centered around the positive
     * real axis.
     */
    public Eisenstein inc() {
	if(this.b >= this.a-1)
	    return new Eisenstein(0, -this.a-1);
	else if(this.b < 0)
	    return new Eisenstein(this.a+1, this.b+1);
	else
	    return new Eisenstein(this.a, this.b+1);
    }

    /* Returns the square of the Euclidean norm.
     */
    public int norm2() {
	return a*a-a*b+b*b;
    }

    /*  */
    public double argument() {
	return Math.PI + this.toCoordinate().azimuth();
    }

    /*  */
    public Eisenstein neg() { 
	return new Eisenstein(-this.a, -this.b); 
    }

    /*  */
    public Eisenstein conj() {
	return new Eisenstein(this.a-this.b, -this.b);
    }

    /*  */
    public Eisenstein add(Eisenstein that) { 
	return new Eisenstein(this.a+that.a, this.b+that.b); 
    }

    /*  */
    public Eisenstein sub(Eisenstein that) {
	return new Eisenstein(this.a-that.a, this.b-that.b); 
    }

    /*  */
    public Eisenstein mul(int mul) {
	return new Eisenstein(this.a*mul, this.b*mul); 
    }

    /*  */
    public Eisenstein mul(Eisenstein that) {
	return new Eisenstein(this.a*that.a-this.b*that.b, 
			      this.b*that.a+this.a*that.b-this.b*that.b); 
    }

    /*  */
    public Eisenstein div(int div) {
	return new Eisenstein(this.a/div, this.b/div); 
    }

    /*  */
    public Eisenstein div(Eisenstein that) {
	return this.mul(that.conj()).div(that.norm2());
    }

    /*  */
    public Eisenstein mod(int div) {
	return new Eisenstein(this.a%div, this.b%div); 
    }

    /* This may be bit questionable...
     */
    public Eisenstein mod(Eisenstein that) {
	int norm = that.norm2();
	Eisenstein prod = this.mul(that.conj());
	return new Eisenstein(prod.a%norm, prod.b%norm);
    }

    /* Fancy formatted printing string.
     */
    public String toString() {
	String res = this.a==0? "": ""+this.a;
	switch(this.b) {
	case  0: return res;
	case  1: return res + "+w";
	case -1: return res + "-w";
	default: return res + (this.b>0? "+": "") + this.b + "w";
	}
    }

    /* Returns a string representing the complex number in a+bi form.
     */
    public String toComplexString() {
	if(this.b>=0)
	    return String.format("%.3f +%.3fi", this.a+this.b*Math.cos(ALPHA), this.b*Math.sin(ALPHA));
	else
	    return String.format("%.3f %.3fi", this.a+this.b*Math.cos(ALPHA), this.b*Math.sin(ALPHA));
    }

    /* Returns the Coordinate (a,b).
     */
    public Coordinate toCoordinate() {
	return new Coordinate(this.a+this.b*Math.cos(Eisenstein.ALPHA), this.b*Math.sin(Eisenstein.ALPHA));
    }

    /*  */
    public boolean isPrime() {
	return false;
    }
}
