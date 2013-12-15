package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import xtc.lang.JavaFiveParser;
import xtc.lang.JavaPrinter;
import xtc.lang.JavaAstSimplifier;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Printer;

import xtc.util.Tool;
import java.util.LinkedList;
import xtc.util.SymbolTable;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.ConsoleHandler;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 */
public class Translator extends Tool {
  public final static Logger LOGGER = Logger.getLogger(Dependency.class .getName());

  /** Create a new translator. */
  public Translator() {
    // Nothing to do.
  }

  public String getName() {
    return "LoveXTC: A Java to C++ Translator";
  }

  public String getCopy() {
    return "(C) 2013 <LoveXTC>";
  }

  public String getVersion() {
    return "1.0";
  }

  public void init() {
    super.init();

    // Declare command line arguments.
    runtime.
      bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
      bool("printJavaCode", "printJavaCode", false, "Print Java code.").
      bool("printCPPTree", "printCPPTree", false, "Print the CPP AST Tree.").
      bool("translate", "translate", false, "translate java to cpp").
      bool("symtab", "symtab", false, "create a symboltable and print it").
      bool("printInheritance", "printInheritance", false, "Print Basic Inheritance tree").
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

    if (runtime.test("printInheritance")) {
    	LinkedList<GNode> nodeList = new LinkedList<GNode>();
    	nodeList.add((GNode)node);
    	Dependency dep = new Dependency(nodeList);
    	dep.makeAddressList();
    	nodeList = dep.makeNodeList();

      printInheritanceNodes(nodeList);
    }

    if (runtime.test("printCPPTree")) {
      LinkedList<GNode> nodeList = new LinkedList<GNode>();
      nodeList.add((GNode)node);
      Dependency dep = new Dependency(nodeList);
      dep.makeAddressList();

      /* Store the found dependencies as AST */
      nodeList = dep.makeNodeList();
      for (int i=0; i<nodeList.size();i++){
        System.out.println(" -> " + nodeList.get(i).getLocation().toString());
      }
      Inheritance inheritanceTree = new Inheritance(nodeList);
      SymbolTable table = new SymbolTable();
      OverloadingASTModifier oModifier = new OverloadingASTModifier();
      oModifier.dispatch((GNode)node);
      new SymTab(runtime, table).dispatch(node);
      new ASTModifier().dispatch((GNode)node);
      //new Overloader(table, inheritanceTree, oModifier.getOverloadedList()).dispatch(node);
      runtime.console().format(node).pln().flush();
    }
    /* outputs a scope file which contains the symbol table/tree for a given node */
    if (runtime.test("symtab")) {
    SymbolTable table = new SymbolTable();

    // do some simplifications on the AST
    //new JavaAstSimplifier().dispatch(node);
    new ASTModifier().dispatch((GNode)node);
    
    // construct the symbol table
    new SymTab(runtime, table).dispatch(node);

    // alternatively, use xtc's functionality to build a symbol table
    //new JavaExternalAnalyzer(runtime, table).dispatch(node);

    // a more convenient way to print the symbol table
    //table.current().dump(runtime.console());
    try{
          PrintWriter fstream = new PrintWriter("Scope.sym");
          Printer aScope = new Printer(fstream);
          table.current().dump(aScope);
          aScope.flush();
          }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    if (runtime.test("translate")) {
      /* List to hold Java AST nodes */
      LinkedList<GNode> nodeList = new LinkedList<GNode>();

      /* Add input file to list at index 0 */
      nodeList.add((GNode)node);

      /* Scan for dependencies  */
      LOGGER.info("Calling Dependency.java on " + node.getName());
      Dependency dep = new Dependency(nodeList);
      dep.makeAddressList();

      /* Store the found dependencies as AST */
      nodeList = dep.makeNodeList();
      //for (int i=0; i<nodeList.size();i++){
      //  System.out.println(" -> " + nodeList.get(i).getLocation().toString());
      //}
      
      /* Build inheritance tree */
      LOGGER.info("Building inheritance tree:");
      Inheritance inheritanceTree = new Inheritance(nodeList);

      /* Print for debugging */
      //printInheritanceNodes(nodeList);
      //runtime.console().format(listNode).pln().flush();

      /* Write VTables to file 'output.h' */
      LOGGER.info("Writing VTables to output.h");
      writeInheritanceAsCPP(inheritanceTree, inheritanceTree.getNodeList());

      SymbolTable table = new SymbolTable();
      /* Make modifications to AST needed for printing */
      LOGGER.info("Modifying AST:");
      for (GNode listNode : nodeList){
        OverloadingASTModifier oModifier = new OverloadingASTModifier();
        oModifier.dispatch(listNode);
        LinkedList<String> overloadedNames = oModifier.getOverloadedList();
        LinkedList<String> staticMethods = inheritanceTree.getStaticMethods(listNode);

        //runtime.console().format(listNode).pln().flush();
        LOGGER.info("Building the Symbol Table:");
        new SymTab(runtime, table).dispatch(listNode);
        new ASTModifier().dispatch(listNode);
        new Overloader(table, inheritanceTree, overloadedNames, staticMethods).dispatch(listNode);

         //Example of how to get static methods:
        /*LinkedList<String> staticMethods = inheritanceTree.getStaticMethods(listNode);
        for (String s : staticMethods) {
          System.out.println(s);
        }*/

        //Example of how to get returnType:
        //Gives static methods now
        //Pass in the name of method as first parameter, name of class as second.
        /*String returnType = inheritanceTree.getReturnType("m_String", "Derived");
        System.out.println(returnType);*/
      }

      /* Write each AST in the list to output.cc as CPP */
        writeTreeAsCPP(nodeList, table, inheritanceTree);
    }
  }

  /* Write VTables to file 'output.h'  */
  /* This method must be called before ASTModifier runs. */
  private void writeInheritanceAsCPP(Inheritance inheritanceTree, LinkedList<GNode> nodeList){
    Writer outH = null;
    try {
      outH = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("output/output.h"), "utf-8"));
      Printer pH = new Printer(outH);
      
      LOGGER.info("calling initOutputHFile()");
      initOutputHFile(pH);
      for (GNode listNode : nodeList){
        LOGGER.info("Running InheritancePrinter on " + listNode.getLocation().toString());
        LinkedList<GNode> listNodeTree = inheritanceTree.parseNodeToInheritance(listNode);
        for (GNode node : listNodeTree) {
          new InheritancePrinter(pH).dispatch(node);
          //runtime.console().format(node).pln().flush();
        }
      }

    } catch (IOException ex){
      LOGGER.warning("IO Exception");
    } finally {
       try {outH.close();} catch (Exception ex) {LOGGER.warning("IO Exception");}
    }
  }

  /* Write each AST in the list to output.cc as CPP */
  /* This method should be run last in the 'translate' sequence. */
  private void writeTreeAsCPP(LinkedList<GNode> nodeList, SymbolTable table, Inheritance inh){
    Writer outCC = null;

    try {
      outCC = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("output/output.cc"), "utf-8"));
      Printer pCC = new Printer(outCC);

      initOutputCCFile(pCC);
      initMainFile(pCC);

      /* Print each GNode in the list into output.cc */
      for (GNode listNode : nodeList){
        LOGGER.info("Running CCCP on " + listNode.getLocation().toString());
        LinkedList<String> sNames = inh.getStaticMethods(listNode);
        new CCCP(pCC, table, inh, sNames).dispatch(listNode);
      }

    } catch (IOException ex){
      // report
    } finally {
       try {outCC.close();} catch (Exception ex) {}
    }
  }

  private void initOutputCCFile(Printer p){
    p.pln("#include \"output.h\"");
    p.pln("#include <sstream>");
  }
  private void initOutputHFile(Printer p){
    p.pln("#pragma once");
    p.pln("#include <stdint.h>");
    p.pln("#include <string>");
    p.pln("#include \"java_lang.h\"");
    p.pln("");
    p.pln("using namespace java::lang;");
    p.pln("using namespace std;");
    p.pln("typedef unsigned char byte;");
  }
  private void initMainFile(Printer p){
    p.pln("#include <iostream>");
    p.pln("#include \"java_lang.h\"");
    p.pln("#include \"output.h\"");
    p.pln("");
    p.pln("using namespace java::lang;");
    p.pln("using namespace std;");
    p.pln("");
  }
  


  private void printInheritanceNodes(LinkedList<GNode> nodeList) {
    for (int i=0; i<nodeList.size();i++){
      System.out.println(" -> " + nodeList.get(i).getLocation().toString());
    }
    Inheritance test = new Inheritance(nodeList);
    runtime.console().format(test.getRoot()).pln().flush();
  }
  /**
   * Run the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
  try {
    LoveXTCLogger.setup();
    } catch (IOException e){
      e.printStackTrace();
    }
    ConsoleHandler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(Level.WARNING);
    LOGGER.addHandler(consoleHandler);
    Translator t = new Translator();
    
    t.run(args);

  }

}
