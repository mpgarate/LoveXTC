import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
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
      Process process = rt.exec("java xtc.oop.Translator -translate " + file.getPath());
      printCommandStdOut(process);
      printCommandStdErr(process);
    }
    catch(IOException e){
      System.out.println("IO Exception translating " + file.getPath());
    }
  }

  public void compileOutput(){
    try{
      Process process = rt.exec("g++ output/java_lang.cc output/output.cc");
      printCommandStdOut(process);
      printCommandStdErr(process);
    }
    catch(IOException e){
      System.out.println("IO Exception trying to compile.");
    }
  }

  public String runJavaFile(File file){ 
    System.out.println("Running java file:");
    System.out.println("-------------------------------------");

    String className = file.getName().replace(".java","");
    String directory = file.getPath().replace(".java","").replace(className,"");
    try{
      Process process = rt.exec("javac -cp " + directory + " " + file.getPath());
      printCommandStdOut(process);
      printCommandStdErr(process);

      Process process2 = rt.exec("java -cp " + directory + " " + className);
      String stdOut = printCommandStdOut(process2);
      printCommandStdErr(process2);
      System.out.println("-------------------------------------");
      return stdOut;
    }
    catch(IOException e){
      System.out.println("IO Exception trying to run the java file.");
      return null;
    }
  }

  public String runCPPOutput(){ 
    System.out.println("Running CPP output file:");
    System.out.println("-------------------------------------");

    try{
      Process process = rt.exec("./a.out");
      String stdOut = printCommandStdOut(process);
      printCommandStdErr(process);

      System.out.println("-------------------------------------");
      return stdOut;
    }
    catch(IOException e){
      System.out.println("IO Exception trying to run the CPP file.");
      return null;
    }
  }

  public void compareOutputs(String a, String b){
    //assertTrue(a.equals(b));

    int openA = a.indexOf("@");
    String splitA = a.substring(openA);
    int closeA = splitA.indexOf("\n");
    a = a.replace(a.subSequence(openA,closeA), "@[xxxxxx]");

    int openB = b.indexOf("@");
    String splitB = b.substring(openB);
    int closeB = splitB.indexOf("\n");
    b = b.replace(b.subSequence(openB,closeB), "@[xxxxxx]");

    assertEquals("Outputs did not match.",a,b);
  }




  /***************************************************************/
  /*********************  Private Helpers  ***********************/
  /***************************************************************/

  private String printCommandStdOut(Process pr){
    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    String line=null;
    StringBuilder sb = new StringBuilder();
    try{
      while((line=input.readLine()) != null) {
        sb.append("\n");
        System.out.println(line);
        sb.append(line);
      }
        sb.append("\n");
    }
    catch(IOException e){
      System.out.println("IO Exception");
    }
    return sb.toString();
  }
  private void printCommandStdErr(Process pr){
    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
    String line=null;
    try{
      if (input.readLine() != null){
        while((line=input.readLine()) != null) {
          System.out.println(line);
        }
        //assertTrue(1==0);
        System.exit(0);
      }
    }
    catch(IOException e){
      System.out.println("IO Exception");
    }
  }
}