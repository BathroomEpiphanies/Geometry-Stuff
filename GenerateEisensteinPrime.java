import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GenerateEisensteinPrime {

    public static void main(String[] args) throws Exception {
	int maxNorm = Integer.parseInt(args[0]);

	ArrayList<Eisenstein> primes = new ArrayList<Eisenstein>();

	for(Eisenstein e=new Eisenstein(0,-2); e.norm2()<maxNorm; e=e.inc()) 
	    hopp: {
		Eisenstein f = e;
		for(Eisenstein z=new Eisenstein(2,0); z.norm2()<f.norm2(); z=z.inc())
		    if(f.mod(z).equals(Eisenstein.ZERO))
			break hopp;
		primes.add(e);
	    }
	System.out.println(primes);

	ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(args[1]));
	oout.writeObject(primes);
	oout.close();
    }
}
