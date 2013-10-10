
/* test file */
public class Test {

  private double[] points;

  // test for constructor
  public Test(double c1, double c2) {
    points = new double[] { c1, c2};
  }

  // test for try-catch
  public double tryCatchTest(int a) {
    try {
      return points[a];
    } catch (ArrayIndexOutOfBoundsException x) {
      throw new IndexOutOfBoundsException("Index: " + a);
    }
  }

  // test for for/while blocks
  public double ForWhileloopIfElseTest() {
    double sumf= 0;

    for (int i=0; i<2; i++) {
      sumf = sumf + points[i];
    }
    double sumw= 0;
    int i = 0;
    while (i<2)
    {
      sumw = sumw + points[i];
      i++;
    }

    if (sumw == sumf) return sumw;
    else return 0.0;
  }

  // test for nested blocks
  public double nestedTest() {
    if (points[0] < 10) {
      double sum = 0;
      for (int i=0; i<2; i++) {
      sum = sum + points[i];
    }
      points[0] = sum;
    }
    else {
      points[1] = 12;
    }
    return 1.0;
  }

  // test for empty method block
  public static void main(String[] args) {
    Test p1 = new Test(1,2);
    System.out.println(p1);
  }

}

