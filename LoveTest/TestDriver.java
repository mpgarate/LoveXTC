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
  
  @Test public void translateTranslateMe() {
    String path = "examples/TranslateMe.java";
    String name = "TranslateMe.java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateOverloaded() {
    String path = "examples/Overloaded.java";
    String name = "Overloaded.java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateDerived() {
    String path = "examples/Derived.java";
    String name = "Derived.java";
    t.translateAndCompare(path, name);
  }
/*
  @Test public void translateWiesMidtermExamples(){
    for (int i = 1; i < 3; i++){ //This should go to 22
      String path = "examples/wies-tests/Test" + i + ".java";
      String name = "Wies Test" + i + ".java";
      t.translateAndCompare(path, name);
    } 
    for (int i = 100; i < 106; i++){
      String path = "examples/wies-tests/Test" + i + ".java";
      String name = "Wies Test" + i + ".java";
      t.translateAndCompare(path, name);
    }
  }
*/
  @Test public void translateWies1(){
    String path = "examples/wies-tests/Test" + 1 + ".java";
    String name = "Wies Test" + 1 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies2(){
    String path = "examples/wies-tests/Test" + 2 + ".java";
    String name = "Wies Test" + 2 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies3(){
    String path = "examples/wies-tests/Test" + 3 + ".java";
    String name = "Wies Test" + 3 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies5(){
    String path = "examples/wies-tests/Test" + 5 + ".java";
    String name = "Wies Test" + 5 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies6(){
    String path = "examples/wies-tests/Test" + 6 + ".java";
    String name = "Wies Test" + 6 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies9(){
    String path = "examples/wies-tests/Test" + 9 + ".java";
    String name = "Wies Test" + 9 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies10(){
    String path = "examples/wies-tests/Test" + 10 + ".java";
    String name = "Wies Test" + 10 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies11(){
    String path = "examples/wies-tests/Test" + 11 + ".java";
    String name = "Wies Test" + 11 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies12(){
    String path = "examples/wies-tests/Test" + 12 + ".java";
    String name = "Wies Test" + 12 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies13(){
    String path = "examples/wies-tests/Test" + 13 + ".java";
    String name = "Wies Test" + 13 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies100(){
    String path = "examples/wies-tests/Test" + 100 + ".java";
    String name = "Wies Test" + 100 + ".java";
    t.translateAndCompare(path, name);
  }
}
