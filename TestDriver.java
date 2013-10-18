import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDriver{
  
  @Before public void setUp() {
  	// Nothing to do.
  }

  @After public void tearDown() {
    // Nothing to do.
  }

  @Test public void oneEqulalsOne() {
    assertTrue(1 == 1);
  }

  @Test
  public void twoEqualsTwo() {
    assertTrue(2 == 2);
  }
}