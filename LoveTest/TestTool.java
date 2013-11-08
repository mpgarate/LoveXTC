import java.io.IOException;
import java.io.*;
import java.io.File;
import java.io.Reader;

public class TestTool{

  public TestTool(){

  }

  private Runtime rt = Runtime.getRuntime();

  /***************************************************************/
  /**********************  Public Tools  ************************/
  /***************************************************************/

  /* Translate a file */
  public void translateFile(File file){
    try{
      Process translate = rt.exec("java xtc.oop.Translator -translate " + file.getPath());
      printCommandStdOut(translate);
      printCommandStdErr(translate);
    }
    catch(IOException e){
      System.out.println("IO Exception translating " + file.getPath());
    }
  }

  public void compileOutput(){
      try{
        Process compile = rt.exec("g++ output/java_lang.cc output/output.cc output/main.cc");
        printCommandStdOut(compile);
        printCommandStdErr(compile);
      }
      catch(IOException e){
        System.out.println("IO Exception trying to compile.");
      }
  }



  /***************************************************************/
  /*********************  Private Helpers  ***********************/
  /***************************************************************/

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