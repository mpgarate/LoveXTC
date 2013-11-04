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

public class InheritancePrinter extends Visitor {
  private static final boolean VERBOSE = true;
	/** The printer for this C printer. */
  protected Printer printer;
  public GNode root;
  public GNode dataLayout;

  private String packageName;
  private String className;
  private String javaClassName;
  private boolean isFirstVTMethod = true;

	public InheritancePrinter(Printer p){
    this.printer = p;
    printer.register(this);
	}


  /***************************************************************/
  /********************  Visitor Methods  ************************/
  /***************************************************************/

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

  public void visitDataLayout(GNode n){
  	dataLayout = n;
  	printer.pln("struct __" + className + ";");
  	printer.p("struct __" + className + "_VT;").pln(); //
  	printer.p("typedef __" + className + "* " + className + ";").pln();
  	printer.p("struct __" + className + " {").pln();
  	visit(n);
  	printer.pln("};").pln();
  }

  public void visitFieldDeclaration(GNode n){
  	visit(n);
  	printer.p(n.getString(1)).p(" ").p(n.getString(2));
  	printer.pln(";");
  }

  public void visitConstructorDeclaration(GNode n){
  	printer.pln("__" + n.getString(0) + "();");
  }

  public void visitDataLayoutMethodDeclaration(GNode n){
          if (!(n.get(0) == null)) printer.p(n.getNode(0));
          if (!(n.get(1) == null)) printer.p(n.getString(1)).p(" ");
    String methodName = n.getString(2);
          printer.p(methodName);
          printer.p("(");
    if(methodName.equals("equals")){
      printer.p(className + ", Object");
    }
    else if (methodName.equals("__class")){
      printer.p("");
    }
    else if (methodName.equals("returnX")){
      /* TODO: We should not be calling this at all. */
      printer.p("String");
    }
    else{
      printer.p(className);
    }
          printer.pln(");");
  }

  public void visitVTable(GNode n){
  	printer.pln("struct __" + className + "_VT {");
  	printer.pln("Class __isa;");

  	new Visitor(){
  		public void visitDataLayoutMethodDeclaration(GNode n){
  			if(!(n.getString(2).equals("__class"))){
  				printer.p(n.getString(1) + " (*");
  				printer.p(n.getString(2));
  				printer.p(")(");
  				printer.p(className);
          //printer.p(n.getNode(4));
  				printer.p(");");
  				printer.pln();
  			}
  		}

      public void visitParameters(GNode n){
        if (n.size() > 0) printer.p(n.getString(0));
      }

		  public void visit(Node n) {
	    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
	  }
  	}.dispatch(dataLayout);
    printer.pln("__" + className + "_VT()");
    printer.p(": ");
    visit(n);
    printer.pln("{").pln("}");
  	printer.pln("};");
  	printer.pln();
  }



  public void visitVTableMethodDeclaration(GNode n){
    if (isFirstVTMethod) {
      printer.p("__isa(__" + className + "::__class())");
      isFirstVTMethod = false;
    }
    else{
      printer.p(",");
      printer.pln();
      printer.p(n.getString(2)).p("(");
      if (n.get(1) != null) printer.p("(").p(n.getString(1)).p("(*)(").p(className).p("))");
      printer.p(" &__" + n.getString(3) + "::" + n.getString(2));
      printer.p(")");
    }
  }

  public void visitModifiers(GNode n){
  	if (n.size() == 1) printer.p(n.getString(0)).p(" ");
  }
  public void visitParameters(GNode n){
  	if (n.size() == 1) printer.p(n.getString(0)).p(" ");
  }


  /***************************************************************/
  /********************  Helper Methods  *************************/
  /***************************************************************/

  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  /* Print verbose debug messages into output file */
  private void v(String s){
    if(VERBOSE) printer.pln(s);
  }

}
