package xtc.oop.LoveTest;

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

  public void translateAndCompare(String path, String name){
    translateAndCompare(path, name, "");
  }

  public void translateAndCompare(String path, String name, String args){
    System.out.println("Translating " + name);
    cleanup();
    File file = new File(path);
    translateFile(file);
    compileOutput();
    String javaOut = runJavaFile(file,name,args);
    String cppOut = runCPPOutput(name,args);
    compareOutputs(javaOut,cppOut);
  }

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
      Process process = rt.exec("make -s compile");
      printCommandStdOut(process);
      printCommandStdErr(process);
    }
    catch(IOException e){
      System.out.println("IO Exception trying to compile.");
    }
  }

  public String runJavaFile(File file, String name, String args){ 
    System.out.println("Running " + " java file:");
    System.out.println("-------------------------------------");

    String className = file.getName().replace(".java","");
    String directory = file.getPath().replace(".java","").replace(className,"");
    try{
      Process process = rt.exec("javac -cp " + directory + " " + file.getPath());
      printCommandStdOut(process);
      printCommandStdErr(process);

      Process process2 = rt.exec("java -cp " + directory + " " + className + " " + args);
      String stdOut = printCommandStdOut(process2);
      String stdErr = printCommandStdErr(process2);
      System.out.println("-------------------------------------");

      //if (stdErr.length() > 0) return stdErr;
      return stdOut;
    }
    catch(IOException e){
      System.out.println("IO Exception trying to run the java file.");
      return null;
    }
  }

  public String runCPPOutput(String name, String args){ 
    System.out.println("Running " + name + " CPP output file:");
    System.out.println("-------------------------------------");

    try{
      Process process = rt.exec("output/a.out" + " " + args);
      String stdOut = printCommandStdOut(process);
      String stdErr = printCommandStdErr(process);

      System.out.println("-------------------------------------");

      //if (stdErr.length() > 0) return stdErr;
      return stdOut;
    }
    catch(IOException e){
      System.out.println("IO Exception trying to run the CPP file.");
      return null;
    }
  }

  public String maskMemoryAddress(String a){
    int openA = 0;
    int closeA = 0;

    openA = a.indexOf("@");
    if (openA > 0){
      String splitA = a.substring(openA);
      closeA = openA + splitA.indexOf("\n");
      if ((openA > 0) && (closeA > 0)) {
        return a.replace(a.subSequence(openA,closeA), "@[xxxxxx]");
      }
    }
    return a;
  }

  public String maskException(String a){
    if (a.contains("NullPointerException")){
      return "NullPointerException";
    }
    return a;
  }

  public void compareOutputs(String a, String b){

    a = maskMemoryAddress(a);
    b = maskMemoryAddress(b);

    a = maskException(a);
    b = maskException(b);
    
    assertEquals("Outputs did not match.",a,b);
  }

  public void cleanup(){
    System.out.println("Cleaning up.");

    try{
      Process process = rt.exec("make -s cleanup");
      String stdOut = printCommandStdOut(process);
      printCommandStdErr(process);
    }
    catch(IOException e){
      System.out.println("IO Exception trying cleanup.");
    }
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
  private String printCommandStdErr(Process pr){
    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
    String line=null;
    StringBuilder sb = new StringBuilder();
    try{
      if (input.readLine() != null){
        while((line=input.readLine()) != null) {
          sb.append("\n");
          //System.out.println(line);
          sb.append(line);
        }
        sb.append("\n");
      }
    }
    catch(IOException e){
      System.out.println("IO Exception");
    }
    return sb.toString();
  }
}