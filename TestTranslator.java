package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import xtc.lang.JavaFiveParser;
import xtc.lang.JavaPrinter;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.util.Tool;


/**
 * A translator from (a subset of) Java to (a subset of) C++.
 */
public class TestTranslator extends Tool {

  /** Create a new translator. */
  public TestTranslator() {
    // Nothing to do.
  }

  public ASTBuilder t = new ASTBuilder();

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
      bool("makeCppTree", "makeCppTree", false, "Count all Java methods.");
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

    if (runtime.test("makeCppTree")) {

      new Visitor() {
        public void visitCompilationUnit(GNode n) {
          visit(n);
        }

        public void visitClassDeclaration(GNode n) {
          t.createClassDeclaration(n);
          visit(n);
        }

        public void visitMethodDeclaration(GNode n) {
          t.createMethodDeclaration(n);
          visit(n);
        }

        public void visit(Node n) {
          for (Object o : n) if (o instanceof Node) dispatch((Node) o);
        }
      }.dispatch(node);
      runtime.console().format(t.root).pln().flush();
    }
  }

  /**
   * Run the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    TestTranslator t = new TestTranslator();
    
    t.run(args);

  }

}