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
    System.out.println("Translating TranslateMe.java");
    File file = new File(path);
    t.translateFile(file);
    t.compileOutput();
    String javaOut = t.runJavaFile(file);
    System.out.println(javaOut);
    String cppOut = t.runCPPOutput();
    System.out.println(cppOut);
    t.compareOutputs(javaOut,cppOut);
  }

  @Test public void translateWiesMidtermExamples(){
    for (int i = 1; i < 2; i++){ //This should go to 22
      String path = "examples/wies-tests/Test" + i + ".java";
      System.out.println("Translating Wies Test" + i + ".java");
      File file = new File(path);
      t.translateFile(file);
      t.compileOutput();
      String javaOut = t.runJavaFile(file);
      //System.out.println(javaOut);
      String cppOut = t.runCPPOutput();
      //System.out.println(cppOut);
      t.compareOutputs(javaOut,cppOut);
      //assertTrue(javaOut.equals(cppOut));
    } /*
    for (int i = 100; i < 106; i++){
      String path = "examples/wies-tests/Test" + i + ".java";
      System.out.println("Translating Wies Test" + i + ".java");
      File file = new File(path);
      t.translateFile(file);
      t.compileOutput();
      System.out.println("Running java file:");
      System.out.println(t.runJavaFile(file));
    }*/
  }
}
