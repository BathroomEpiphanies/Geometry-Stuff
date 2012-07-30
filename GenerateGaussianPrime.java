/* Generates all Gaussian primes in the first quadrant. {g = a+bi |
 * a,b integers}
 * 
 * Writes ArrayList<Gaussian> object containing the primes with a+b <
 * maxNorm, to outputFile.
 * 
 * Usage
 *   java GenerateGaussianPrime maxNorm
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GenerateGaussianPrime {

    private static ArrayList<Gaussian> primes = new ArrayList<Gaussian>();
    static {
	primes.add(new Gaussian( 1, 1)); primes.add(new Gaussian(-1, 1));
	primes.add(new Gaussian(-1,-1)); primes.add(new Gaussian( 1,-1));
    }

    public static void main(String[] args) throws Exception {
	int maxNorm = Integer.parseInt(args[0]);
	maxNorm *= maxNorm;

	for(Gaussian e=new Gaussian(3,0); e.norm2()<maxNorm; e=e.inc()) 
	    hopp: {
		Gaussian f = e;
		for(Gaussian z: primes) {
		    if(f.mod(z).equals(Gaussian.ZERO) && f.norm2() > z.norm2()) {
			System.err.printf("%s\t=\t%s\tx\t%s\n", f.toString(), z.toString(), f.div(z).toString());
			break hopp;
		    }
		}
		System.err.printf("%s\n", f.toString());
		primes.add(e);
		primes.add(e.mul(Gaussian.UNIT));
		primes.add(e.neg());
		primes.add(e.neg().mul(Gaussian.UNIT));
	    }
	
	System.err.println(primes);

	printPrimes(primes);
	//printGerber(primes);
    }

    private static void printPrimes(ArrayList<Gaussian> primes) throws Exception {
	ObjectOutputStream oout = new ObjectOutputStream(System.out);
	oout.writeObject(primes); oout.close();
    }

    private static void printGerber(ArrayList<Gaussian> primes) throws Exception {
	System.out.println(GerberPlottables.GERBER_HEADER);
	System.out.println("G75*");
	int scale = 1000;
	//	double radius = scale/2*2/Math.sqrt(3);
	double radius = scale/2;
	for(Gaussian e: primes)
	    System.out.println(GerberPlottables.circle(e.mul(scale).toCoordinate(), radius));
	System.out.println(GerberPlottables.GERBER_FOOTER);
    }
}
