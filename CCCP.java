/* CCCP is a C++ printer named for the centralized nature of 
the Soviet Union. */

package xtc.oop;

import java.lang.*;

/* Imports based on src/xtc/lang/CPrinter.java */
import java.util.Iterator;

import xtc.tree.LineMarker;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Pragma;
import xtc.tree.Printer;
import xtc.tree.SourceIdentity;
import xtc.tree.Token;
import xtc.tree.Visitor;
/* End imports based on src/xtc/lang/CPrinter.java */

public class CCCP extends Visitor {

	/* We should base this file on src/xtc/lang/CPrinter.java */

	/* This file will have a ton of methods of two types:
		
			1 - Visitor methods to go to each node
			2 - Helper methods to test for various contextual conditions for printing nodes  

	*/

	/** The printer for this C printer. */
  protected Printer printer;
  private String packageName;
  public GNode root;

	public CCCP(Printer printer){
		this.printer = printer;
		printer.register(this);
	}


  /***************************************************************/
  /********************  Visitor Methods  ************************/
  /***************************************************************/

	public void visitCompilationUnit(GNode n) {
    printer.pln("/* Visiting translation unit for " + n.getLocation().toString() + " */");
    visit(n);
  }

	public void visitClassDeclaration(GNode n) {
    printer.pln("/* visiting class declaration */");
    visit(n);
  }

  public String visitQualifiedIdentifier(GNode n){
    printer.pln("/* visiting qualified identifier */");
    StringBuilder sb = new StringBuilder();

    int size = n.size();
    for(int i = size-1; i > -1; i--){
      sb.append(n.getString(i));
      if(i > 0) sb.append(".");
    }
    visit(n);
    return sb.toString();
  }

  /* TRICKY need to have the namespace scope */
  public void visitPackageDeclaration(GNode n) {
    printer.pln("/* visiting package declaration */");
    packageName = dispatch(n.getNode(1)).toString();
    visit(n);
  }
  public void visitImportDeclaration(GNode n) {
    printer.pln("/* visiting Import declaration */");
    visit(n);
  }

	public void visitClassBody(GNode n) {
    printer.pln("/* visiting class body */");
    /* Begin the namespace. */
    printer.pln(unlessNull("namespace " + packageName + " {", packageName));
    visit(n);
    printer.pln(unlessNull("}",packageName)); //Closing namespace
  }

  public void visitMethodDeclaration(GNode n){
    printer.pln("/* visiting method declaration */");
    visit(n);
  }

  public void visitBlock(GNode n){
    printer.pln().pln("/* visiting block */");
    visit(n);
  }

  public void visitFieldDeclaration(GNode n){
    printer.pln("/* visiting Field Declaration */");
	  visit(n);
  }

  public void visitDeclarator(GNode n){
	  visit(n);
  }


  /***************************************************************/
  /********************   Helper Methods   ***********************/
  /***************************************************************/


  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  private String unlessNull(String s){
    return unlessNull(s, s);
  }

  private String unlessNull(String s, String compare){
    if (!(compare == null)) return s;
    else return "";
  }

}
