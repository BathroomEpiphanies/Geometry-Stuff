/* Generates all Eisenstein primes in the "triant" centered on the
 * real axis. {e = a+bω = re^(πi) | r > 0, -⅓πi ≤ α < ⅓πi}
 * 
 * Writes ArrayList<Eisenstein> object containing the primes with
 * Euclidean norm less than maxNorm, to outputFile.
 * 
 * Usage
 *   java GenerateEisensteinPrime maxNorm
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GenerateEisensteinPrime {

    private static ArrayList<Eisenstein> primes = new ArrayList<Eisenstein>();
    static {
	primes.add(new Eisenstein( 0,-2)); primes.add(new Eisenstein( 1,-1));
	primes.add(new Eisenstein( 2, 0)); primes.add(new Eisenstein( 2, 1));
	primes.add(new Eisenstein( 2, 2)); primes.add(new Eisenstein( 1, 2));
	primes.add(new Eisenstein( 0, 2)); primes.add(new Eisenstein(-1, 1));
	primes.add(new Eisenstein(-2, 0)); primes.add(new Eisenstein(-2,-1));
	primes.add(new Eisenstein(-2,-2)); primes.add(new Eisenstein(-1,-2));
    }

    public static void main(String[] args) throws Exception {
	int maxNorm = Integer.parseInt(args[0]);
	maxNorm *= maxNorm;

	for(Eisenstein e=new Eisenstein(0,-3); e.norm2()<maxNorm; e=e.inc()) 
	    hopp: {
		Eisenstein f = e;
		for(Eisenstein z: primes) {
		    if(f.mod(z).equals(Eisenstein.ZERO) && f.norm2() > z.norm2()) {
			System.err.printf("%s\t=\t%s\tx\t%s\n", f.toString(), z.toString(), f.div(z).toString());
			break hopp;
		    }
		}
		System.err.printf("%s\n", f.toString());
		primes.add(e);
		primes.add(e.mul(Eisenstein.OMEGA));
		primes.add(e.mul(Eisenstein.OMEG2));
	    }

	System.err.println(primes);

	printPrimes(primes);
	//printGerber(primes);
    }

    private static void printPrimes(ArrayList<Eisenstein> primes) throws Exception {
	ObjectOutputStream oout = new ObjectOutputStream(System.out);
	oout.writeObject(primes); oout.close();
    }

    private static void printGerber(ArrayList<Eisenstein> primes) throws Exception {
	System.out.println(GerberPlottables.GERBER_HEADER);
	System.out.println("G75*");
	int scale = 1000;
	//	double radius = scale/2*2/Math.sqrt(3);
	double radius = scale/2;
	for(Eisenstein e: primes)
	    System.out.println(GerberPlottables.circle(e.mul(scale).toCoordinate(), radius));
	System.out.println(GerberPlottables.GERBER_FOOTER);
    }
}
