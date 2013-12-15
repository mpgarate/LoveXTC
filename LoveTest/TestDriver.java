package xtc.oop.LoveTest;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.*;
import java.io.File;
import java.io.Reader;
import java.util.Arrays;

/** Runs test case java files through the test suite */
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
  @Test public void translateArrays() {
    String path = "examples/Arrays.java";
    String name = "Arrays.java";
    t.translateAndCompare(path, name);
  }
  /*
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
  @Test public void translateWies7(){
    String path = "examples/test-cases/Test00" + 7 + ".java";
    String name = "Wies Test" + 7 + ".java";
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


  @Test public void translateWies18(){
    String path = "examples/test-cases/Test0" + 18 + ".java";
    String name = "Wies Test" + 18 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies19(){
    String path = "examples/test-cases/Test0" + 19 + ".java";
    String name = "Wies Test" + 19 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies20(){
    String path = "examples/test-cases/Test0" + 20 + ".java";
    String name = "Wies Test" + 20 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies21(){
    String path = "examples/test-cases/Test0" + 21 + ".java";
    String name = "Wies Test" + 21 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies22(){
    String path = "examples/test-cases/Test0" + 22 + ".java";
    String name = "Wies Test" + 22 + ".java";
    String args = "lorem ipsum dolor sit amet";
    t.translateAndCompare(path, name, args);
  }
  @Test public void translateWies23(){
    String path = "examples/test-cases/Test0" + 23 + ".java";
    String name = "Wies Test" + 23 + ".java";
    String args = "lorem ipsum dolor sit amet caesar vir meus est";
    t.translateAndCompare(path, name, args);
  }
  
  @Test public void translateWies32(){
    String path = "examples/test-cases/Test0" + 32 + ".java";
    String name = "Wies Test" + 32 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies33(){
    String path = "examples/test-cases/Test0" + 33 + ".java";
    String name = "Wies Test" + 33 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies34(){
    String path = "examples/test-cases/Test0" + 34 + ".java";
    String name = "Wies Test" + 34 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies35(){
    String path = "examples/test-cases/Test0" + 35 + ".java";
    String name = "Wies Test" + 35 + ".java";
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
  @Test public void translateWies41(){
    String path = "examples/test-cases/Test0" + 41 + ".java";
    String name = "Wies Test" + 41 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies42(){
    String path = "examples/test-cases/Test0" + 42 + ".java";
    String name = "Wies Test" + 42 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies43(){
    String path = "examples/test-cases/Test0" + 43 + ".java";
    String name = "Wies Test" + 43 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies44(){
    String path = "examples/test-cases/Test0" + 44 + ".java";
    String name = "Wies Test" + 44 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies45(){
    String path = "examples/test-cases/Test0" + 45 + ".java";
    String name = "Wies Test" + 45 + ".java";
    t.translateAndCompare(path, name);
  }
  @Test public void translateWies46(){
    String path = "examples/test-cases/Test0" + 46 + ".java";
    String name = "Wies Test" + 46 + ".java";
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
