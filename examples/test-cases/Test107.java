//ArrayStoreException

class Students { int x, y; }
class Classes extends Students { int color; }

public class Test107 {

	  public static void main(String[] args) {
	        Classes[] cpa = new Classes[10];
	        Students[] pa = cpa;
	        System.out.println(pa[1] == null);
	        //try {
	            pa[0] = new Students();
	        //} catch (ArrayStoreException e) {
	          //  System.out.println(e);
	        //}
	    }

}
