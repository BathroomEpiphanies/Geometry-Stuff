public class GerberPlottables {
    
    final public static String GERBER_HEADER = 
	"G01*\n" +
	"G70*\n" +
	"G90*\n" +
	"%MOIN*%\n" +
	"%FSLAX34Y34*%\n" +
	"%AMHEXAGON*5,1,6,0,0,0.100000,30*%\n" +
	"%ADD10C,0.150000*%\n" + 
	"%ADD11HEXAGON*%\n" + 
	"G54D11*";

    final public static String GERBER_FOOTER = 
	"M02*";

    public static String circle(Coordinate center, double radius) {
	return String.format("G36*\nG01X%07dY%07dD02*\nG03X%07dY%07dI%07dJ%07dD01*\nG37*", 
			     (int)(center.x), (int)(center.y), (int)(center.x), (int)(center.y-radius), 0, (int)(radius) );
    }
    
    public static String hexagon(Coordinate center, double radius) {
	Coordinate corner = center.sub(Coordinate.Y.neg().scale(radius));
	String res = "G36*\n" + String.format("X%dY%dD02*\n", (int)(corner.x), (int)(corner.y));
	//	for(int i=1; i<6; i++) {
	for(int i: new int[] {1,2,3,4,5}) {
	    corner = corner.sub(center).rotate(Math.PI/3).add(center);
	    res += String.format("X%07dY%07dD01*\n", (int)(corner.x), (int)(corner.y));
	}
	res += "G37*";
	return res;
    }
}