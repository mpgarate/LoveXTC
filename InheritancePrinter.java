package xtc.oop;
import java.lang.*;

/* Imports based on src/xtc/lang/CPrinter.java */
import java.util.Iterator;

import java.util.LinkedList;
import xtc.tree.LineMarker;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Pragma;
import xtc.tree.Printer;
import xtc.tree.SourceIdentity;
import xtc.tree.Token;
import xtc.tree.Visitor;
/* End imports based on src/xtc/lang/CPrinter.java */

/**
 * A visitor that goes through our custom Inheritance tree.
 * It prints out the information in the tree.
 */
public class InheritancePrinter extends Visitor {
  private static final boolean VERBOSE = true;
  /** The printer for this C printer. */
  protected Printer printer;
  /** the root of the tree. */
  public GNode root;
  /** The datalayout nodes. */
  public GNode dataLayout;

  private String packageName;
  private String className;
  private String javaClassName;
  private boolean isFirstVTMethod = true;

  /** 
  * Constructor for Inheritance Printer
  * 
  * @param p The printer to which we are prinitng.
  */
  public InheritancePrinter(Printer p){
    this.printer = p;
    printer.register(this);
  }


  /***************************************************************/
  /********************  Visitor Methods  ************************/
  /***************************************************************/

  /** Visiting Header Declaration and printing it. */
  public void visitHeaderDeclaration(GNode n){
    packageName = n.getString(0); //null if no package
    if (packageName != null){
      printer.pln("namespace " + n.getString(0) + " {");
      className = n.getString(1);
      visit(n);
      printer.pln("}").pln();
    }
    else{
      className = n.getString(1);
      visit(n);
    }
  }

  /** Visiting DataLayout Node and printing it. */
  public void visitDataLayout(GNode n){
    dataLayout = n;
    printer.pln("struct __" + className + ";");
    printer.p("struct __" + className + "_VT;").pln(); //
    printer.p("typedef __rt::Ptr<__" + className + "> " + className + ";").pln();
    printer.p("struct __" + className + " {").pln();
    visit(n);
    printer.pln("};").pln();
  }

  /** Visiting Field Declaration and printing it. */
  public void visitFieldDeclaration(GNode n){
    visit(n);
    printer.p(n.getString(1)).p(" ").p(n.getString(2));
    printer.pln(";");
  }

  /** Visiting Constructor Declaration and printing it. */
  public void visitConstructorDeclaration(GNode n){
    printer.pln("__" + n.getString(0) + "();");

    if (n.getNode(1).size() == 0){
      printer.pln("static " + className + " init(" + className + ");");
    }
    else {
      printer.p("static " + className + " init(" + className + ", ");
      printer.p(n.getNode(1));
      printer.pln(");");
    }

  }

  /** Visiting DataLayout Method Declaration and printing it. */
  public void visitDataLayoutMethodDeclaration(GNode n){
    if (!(n.get(0) == null)) {
      printer.p(n.getNode(0));
    }
    if (!(n.get(1) == null)) {
      if (n.getString(1).equals("int")){
        printer.p("int32_t").p(" ");
      }
      else{
        printer.p(n.getString(1)).p(" ");
      }
    }
    String methodName = n.getString(2);

    if (methodName.equals("main")){
      printer.pln("main(__rt::Ptr<__rt::Array<String> > args);");
      return;
    }

    printer.p(methodName);
    printer.p("(");
    
    printer.p(n.getNode(4));
    if ((n.getNode(4).size()==0) && !(n.getString(2).equals("__class"))){
        printer.p(className);
      }
    
          printer.pln(");");
  }

  /** Visiting Vtable and printing it. */
  public void visitVTable(GNode n){
    printer.pln("struct __" + className + "_VT {");
    printer.pln("Class __isa;");
    printer.pln("void (*__delete)(__" + className + "*);");

    new Visitor(){
      public void visitVTableMethodDeclaration(GNode n){
        if(!(n.getString(2).equals("__isa"))){
          if (n.getString(1) != null) {
            printer.p(n.getString(1) + " (*");
          }
          else {
            printer.p("void" + " (*");
          }
          printer.p(n.getString(2));
          printer.p(")(");
          //printer.p(className);
          printer.p(n.getNode(4));
          if ((n.getNode(4).size()==0) && !(n.getString(2).equals("__class"))){
            printer.p(className);
          }
          printer.p(");");
          printer.pln();
        }
      }

      public void visit(Node n) {
      for (Object o : n) if (o instanceof Node) dispatch((Node) o);
    }
    }.dispatch(n);
    printer.pln("__" + className + "_VT()");
    printer.p(": ");
    visit(n);
    printer.pln("{}");
    printer.pln("};");
    printer.pln();
  }

  /** Visiting Vtable(The function pointers) and printing it. */
  public void visitVTableMethodDeclaration(GNode n){
    if (isFirstVTMethod) {
      printer.p("__isa(__" + className + "::__class())");
      printer.p(",");
      printer.pln();
      printer.p("__delete(&__rt::__delete<__" + className + ">)");
      isFirstVTMethod = false;
    }
    else{
      printer.p(",");
      printer.pln();
      printer.p(n.getString(2)).p("(");
      if (n.get(1) != null) {
        printer.p("(");
        if(n.getString(1).equals("int")){
          printer.p("int32_t");
        }
        else{
          printer.p(n.getString(1));
        }
        printer.p("(*)(").p(n.getNode(4));
      }
      if (n.getNode(4).size()==0){
        printer.p(className);
      }
      printer.p("))");
      printer.p(" &__" + n.getString(3) + "::" + n.getString(2));
      printer.p(")");
    }
  }

  /** Visiting Modifiers and printing it. */
  public void visitModifiers(GNode n){
    if (n.size() == 1) printer.p(n.getString(0)).p(" ");
  }

  /** Visiting Parameters and printing it. */
  public void visitParameters(GNode n){
    for (int x = 0; x < n.size() ; x++){
      if (n.getString(x).equals("int")){
        printer.p("int32_t");
      }
      else{
        printer.p(n.getString(x));
      }
      if (x != (n.size()-1)){
        printer.p(",");
      }
    }
  }


  /***************************************************************/
  /********************  Helper Methods  *************************/
  /***************************************************************/

  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  /** Print verbose debug messages into output file */
  private void v(String s){
    if(VERBOSE) printer.pln(s);
  }

}