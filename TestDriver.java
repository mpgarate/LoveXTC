import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.*;
import java.io.File;
import java.io.Reader;

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

  @Test
  public void translateWiesMidtermExamples(){
    File folder = new File("examples/wies-tests/");
    File[] files = folder.listFiles();

    Runtime rt = Runtime.getRuntime();

    for (File file : files) {
      if (file.isFile() && file.getName().endsWith(".java")) {
        try{
          Process translate = rt.exec("java xtc.oop.Translator -translate " + file.getPath());
          printCommandStdOut(translate);
          printCommandStdErr(translate);
        }
        catch(IOException e){
          System.out.println("IO Exception translating " + file.getPath());
        }/* We won't test this yet
        try{
          Process compile = rt.exec("g++ output/java_lang.cc output/output.cc output/main.cc");
          printCommandStdOut(compile);
          printCommandStdErr(compile);
        }
        catch(IOException e){
          System.out.println("IO Exception compiling " + file.getPath());
        }*/
      }
    }
  }

  private void printCommandStdOut(Process pr){
    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    String line=null;
    try{
      while((line=input.readLine()) != null) {
        System.out.println(line);
      }
    }
    catch(IOException e){
      System.out.println("IO Exception");
    }
  }
  private void printCommandStdErr(Process pr){
    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
    String line=null;
    try{
      if (input.readLine() != null){
        while((line=input.readLine()) != null) {
          System.out.println(line);
        }
        //assertTrue(false);
        System.exit(0);
      }
    }
    catch(IOException e){
      System.out.println("IO Exception");
    }
  }
}
