import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.*;
import java.io.File;
import java.io.Reader;

public class TestDriver{

  TestTool t = new TestTool();
  
  @Before public void setUp() {
  	// Nothing to do.
  }

  @After public void tearDown() {
    // Nothing to do.
  }

  @Test public void oneEqulalsOne() {
    assertTrue(1 == 5);
  }

  @Test
  public void twoEqualsTwo() {
    assertTrue(1 == 2);
  }
  
  @Test
  public void translateWiesMidtermExamples(){
    File folder = new File("examples/wies-tests/");
    File[] files = folder.listFiles();

    for (File file : files) {
      if (file.isFile() && file.getName().endsWith(".java")) {
        t.translateFile(file);
      }
    }
  }
}
