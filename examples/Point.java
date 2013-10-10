
/**
 * An immutable four-dimensional point.
 *
 * @author Robert Grimm
 */
public class Point {

  /** The dimensions. */
  public static final int DIMENSIONS = 4;

  /** The origin. */
  public static final Point ORIGIN = new Point(0,0,0,0);

  private final double[] coordinates;

  /**
   * Create a new point.
   *
   * @param c1 The first coordinate.
   * @param c2 The second coordinate.
   * @param c3 The third coordinate.
   * @param c4 The fourth coordinate.
   */
  public Point(double c1, double c2, double c3, double c4) {
    coordinates = new double[] { c1, c2, c3, c4 };
  }

  /**
   * Get the specified coordinate.
   *
   * @param idx The index.
   * @return The coordinate.
   * @throws IndexOutOfBoundsException Signals an invalid index.
   */
  public double getCoordinate(int idx) {
    try {
      return coordinates[idx];
    } catch (ArrayIndexOutOfBoundsException x) {
      throw new IndexOutOfBoundsException("Index: " + idx);
    }
  }

  /**
   * Determine the distance from the specified point.
   *
   * @param p The other point.
   * @return The distance.
   */
  public double getDistanceFrom(Point p) {
    double distanceSquared = 0;

    for (int i=0; i<DIMENSIONS; i++) {
      double diff = this.getCoordinate(i) - p.getCoordinate(i);
      distanceSquared += diff * diff;
    }

    return Math.sqrt(distanceSquared);
  }

  /**
   * Get a string representation for this point.
   *
   * @return The string representation.
   */
  public String toString() {
    return "Point(" +
      getCoordinate(0) + ", " +
      getCoordinate(1) + ", " +
      getCoordinate(2) + ", " +
      getCoordinate(3) + ")";
  }

  public static void main(String[] args) {
    Point p1 = new Point(1, 2, 3, 4);

    System.out.println(p1);
    System.out.println("Distance from origin: " + p1.getDistanceFrom(ORIGIN));
  }

}

