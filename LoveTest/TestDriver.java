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
  }/*
  @Test public void translateOverOverloaded() {
    String path = "examples/OverOverloaded.java";
    String name = "OverOverloaded.java";
    t.translateAndCompare(path, name);
  }*/
  @Test public void translateDerived() {
    String path = "examples/Derived.java";
    String name = "Derived.java";
    t.translateAndCompare(path, name);
  }
/*
  @Test public void translateWiesMidtermExamples(){
    for (int i = 1; i < 3; i++){ //This should go to 22
      String path = "examples/test-cases/Test0" + i + ".java";
      String name = "Wies Test" + i + ".java";
      t.translateAndCompare(path, name);
    } 
    for (int i = 100; i < 106; i++){
      String path = "examples/test-cases/Test0" + i + ".java";
      String name = "Wies Test" + i + ".java";
      t.translateAndCompare(path, name);
    }
  }
*/
  @Test public void translateWies1(){
    String path = "examples/test-cases/Test00" + 1 + ".java";
    String name = "Wies Test" + 1 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies2(){
    String path = "examples/test-cases/Test00" + 2 + ".java";
    String name = "Wies Test" + 2 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies3(){
    String path = "examples/test-cases/Test00" + 3 + ".java";
    String name = "Wies Test" + 3 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies4(){
    String path = "examples/test-cases/Test00" + 4 + ".java";
    String name = "Wies Test" + 4 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies5(){
    String path = "examples/test-cases/Test00" + 5 + ".java";
    String name = "Wies Test" + 5 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies6(){
    String path = "examples/test-cases/Test00" + 6 + ".java";
    String name = "Wies Test" + 6 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies9(){
    String path = "examples/test-cases/Test00" + 9 + ".java";
    String name = "Wies Test" + 9 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies10(){
    String path = "examples/test-cases/Test0" + 10 + ".java";
    String name = "Wies Test" + 10 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies11(){
    String path = "examples/test-cases/Test0" + 11 + ".java";
    String name = "Wies Test" + 11 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies12(){
    String path = "examples/test-cases/Test0" + 12 + ".java";
    String name = "Wies Test" + 12 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies13(){
    String path = "examples/test-cases/Test0" + 13 + ".java";
    String name = "Wies Test" + 13 + ".java";
    t.translateAndCompare(path, name);
  }

  /* 
    Wies test 14 is handled correctly, but the error output is not 
    Identical. Here our test driver ensures that our output for test 14
    compiles, runs, and prints nothing to stdout, just like the java file. 
  */
  @Test public void translateWies14(){
    String path = "examples/test-cases/Test0" + 14 + ".java";
    String name = "Wies Test" + 14 + ".java";
    t.translateAndCompare(path, name);
  }


  @Test public void translateWies19(){
    String path = "examples/test-cases/Test0" + 19 + ".java";
    String name = "Wies Test" + 19 + ".java";
    t.translateAndCompare(path, name);
  }


  @Test public void translateWies36(){
    String path = "examples/test-cases/Test0" + 36 + ".java";
    String name = "Wies Test" + 36 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies37(){
    String path = "examples/test-cases/Test0" + 37 + ".java";
    String name = "Wies Test" + 37 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies38(){
    String path = "examples/test-cases/Test0" + 38 + ".java";
    String name = "Wies Test" + 38 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies39(){
    String path = "examples/test-cases/Test0" + 39 + ".java";
    String name = "Wies Test" + 39 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies40(){
    String path = "examples/test-cases/Test0" + 40 + ".java";
    String name = "Wies Test" + 40 + ".java";
    t.translateAndCompare(path, name);
  }


  @Test public void translateWies100(){
    String path = "examples/test-cases/Test" + 100 + ".java";
    String name = "Wies Test" + 100 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies101(){
    String path = "examples/test-cases/Test" + 101 + ".java";
    String name = "Wies Test" + 101 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies102(){
    String path = "examples/test-cases/Test" + 102 + ".java";
    String name = "Wies Test" + 102 + ".java";
    t.translateAndCompare(path, name);
  }
}
