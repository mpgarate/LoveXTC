import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.*;
import java.io.File;
import java.io.Reader;
import java.util.Arrays;

public class TestDriver{

  TestTool t = new TestTool();
  
  @Before public void setUp() {
  	// Nothing to do.
  }

  @After public void tearDown() {
    // Nothing to do.
  }

  @Test public void oneEqulalsOne() {
    assertTrue(1 == 1);
  }

  @Test public void twoEqualsTwo() {
    assertTrue(2 == 2);
  }
  @Test public void translateTranslateMe() {
    String path = "examples/TranslateMe.java";
    String name = "TranslateMe.java";
    t.translateAndCompare(path, name);
  }

  @Test public void translateWiesMidtermExamples(){
    for (int i = 1; i < 2; i++){ //This should go to 22
      String path = "examples/wies-tests/Test" + i + ".java";
      String name = "Wies Test" + i + ".java";
      t.translateAndCompare(path, name);
    } /*
    for (int i = 100; i < 106; i++){
      String path = "examples/wies-tests/Test" + i + ".java";
      String name = "Wies Test" + i + ".java";
      t.translateAndCompare(path, name);
    }*/
  }
}
