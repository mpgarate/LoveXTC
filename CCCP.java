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
  private static final boolean VERBOSE = true;
	/* We should base this file on src/xtc/lang/CPrinter.java */

	/* This file will have a ton of methods of two types:
		
			1 - Visitor methods to go to each node
			2 - Helper methods to test for various contextual conditions for printing nodes  

	*/

	/** The printer for this C printer. */
  protected Printer printer;
  protected Printer header;
  private String packageName;
  public GNode root;

	public CCCP(Printer pDotCC, Printer pDotH){
    this.printer = pDotCC;
    this.header = pDotCC;
    printer.register(this);
    header.register(this);
	}


  /***************************************************************/
  /********************  Visitor Methods  ************************/
  /***************************************************************/

	public void visitCompilationUnit(GNode n) {
    v("/* Visiting translation unit for " + n.getLocation().toString() + " */");
    visit(n);
  }

	public void visitClassDeclaration(GNode n) {
    v("/* visiting class declaration */");
    visit(n);
  }

  public String visitQualifiedIdentifier(GNode n){
    v("/* visiting qualified identifier */");
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
    v("/* visiting package declaration */");
    packageName = dispatch(n.getNode(1)).toString();
    visit(n);
  }
  public void visitImportDeclaration(GNode n) {
    v("/* visiting Import declaration */");
    visit(n);
  }

	public void visitClassBody(GNode n) {
    v("/* visiting class body */");
    /* Begin the namespace. */
    printlnUnlessNull("namespace " + packageName + " {", packageName);
    visit(n);
    printlnUnlessNull("}",packageName); //Closing namespace
  }

  public void visitMethodDeclaration(GNode n){
    v("/* visiting method declaration */");
    visit(n);
  }

  public void visitBlock(GNode n){
    v("/* visiting block */");
    visit(n);
  }

  public void visitFieldDeclaration(GNode n){
    v("/* visiting Field Declaration */");
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

  private void printlnUnlessNull(String s){
    printlnUnlessNull(s, s);
  }

  private void printlnUnlessNull(String s, String compare){
    if (!(compare == null)) printer.pln(s);
    else return;
  }
  private void v(String s){
    if(VERBOSE) printer.pln(s);
  }

}
