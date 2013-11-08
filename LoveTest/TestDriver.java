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

  @Test
  public void twoEqualsTwo() {
    assertTrue(2 == 2);
  }

  @Test
  public void translateWiesMidtermExamples(){
    for (int i = 1; i < 22; i++){
      String path = "examples/wies-tests/Test" + i + ".java";
      System.out.println("Translating Wies Test" + i + ".java");
      t.translateFile(path);
      t.compileOutput();
      System.out.println("Running java file:");
      System.out.println(t.runJavaFile(path));
    }
    for (int i = 100; i < 106; i++){
      String path = "examples/wies-tests/Test" + i + ".java";
      System.out.println("Translating Wies Test" + i + ".java");
      t.translateFile(path);
      t.compileOutput();
      System.out.println("Running java file:");
      System.out.println(t.runJavaFile(path));
    }
  }
}
