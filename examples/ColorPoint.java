public class ColorPoint extends Point {
	public ColorPoint (double c1, double c2, double c3, double c4) {
	super(c1,c2,c3,c4);
	}
	public int c;
	public String getColor (ColorPoint a) {
	if (a.c == 1)
		return "Green";
	else
		return "No Color"; 

	}
}