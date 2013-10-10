package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import xtc.lang.JavaFiveParser;
import xtc.lang.JavaPrinter;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Printer;

import xtc.util.Tool;


/**
 * A translator from (a subset of) Java to (a subset of) C++.
 */
public class Translator extends Tool {

  /** Create a new translator. */
  public Translator() {
    // Nothing to do.
  }

  public String getName() {
    return "Java to C++ Translator";
  }

  public String getCopy() {
    return "(C) 2013 <Group Name>";
  }

  public void init() {
    super.init();

    // Declare command line arguments.
    runtime.
      bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
      bool("printJavaCode", "printJavaCode", false, "Print Java code.").
      bool("printCPPTree", "printCPPTree", false, "Print the CPP AST Tree.").
      bool("printCPP", "printCPP", false, "Print the AST as a CPP file.");
  }

  public void prepare() {
    super.prepare();

    // Perform consistency checks on command line arguments.
  }

  public File locate(String name) throws IOException {
    File file = super.locate(name);
    if (Integer.MAX_VALUE < file.length()) {
      throw new IllegalArgumentException(file + ": file too large");
    }
    return file;
  }

  public Node parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser =
      new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (Node)parser.value(result);
  }

  public void process(Node node) {
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }

    if (runtime.test("printJavaCode")) {
      new JavaPrinter(runtime.console()).dispatch(node);
      runtime.console().flush();
    }

    if (runtime.test("printCPPTree")) {
      ASTBuilder CppT = new ASTBuilder(node);
      runtime.console().format(CppT.root).pln().flush();
    }

    if (runtime.test("printCPP")) {
      ASTBuilder CppT = new ASTBuilder(node);
      Writer writer = null;

      try {
          writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream("output.cpp"), "utf-8"));
          Printer p = new Printer(writer);
          new CCCP(p).dispatch(CppT.getRoot());
      } catch (IOException ex){
        // report
      } finally {
         try {writer.close();} catch (Exception ex) {}
      }
    }
  }
  /**
   * Run the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    Translator t = new Translator();
    
    t.run(args);

  }

}