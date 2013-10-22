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
  public GNode root;


  private String packageName;
  private String className;

	public CCCP(Printer p){
    this.printer = p;
    printer.register(this);
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
    className = n.getString(1);
    visit(n);
  }

  /* TRICKY need to have the namespace scope */
  public void visitPackageDeclaration(GNode n) {
    v("/* visiting package declaration */");

    GNode qid  = n.getGeneric(1);
    int   size = qid.size();
    packageName = fold(qid,size);
    visit(n);
  }
  public void visitImportDeclaration(GNode n) {
    v("/* visiting Import declaration */");
    visit(n);
  }

	public void visitClassBody(GNode n) {
    v("/* visiting class body */");
    /* Begin the namespace. */
    printer.incr();
    printlnUnlessNull("namespace " + packageName + " {", packageName);
    visit(n);
    printlnUnlessNull("}",packageName); //Closing namespace
    printer.decr();
  }

  public void visitMethodDeclaration(GNode n){
    v("/* visiting method declaration */");
    visit(n);
  }

  public void visitBlock(GNode n){
    v("/* visiting block */");
    printer.p("{").incr();
    visit(n);
    printer.decr().p("}");
  }

  public void visitConstructorDeclaration(GNode n){
    String constructorName = n.getString(2);
    /* TODO: Allow constructor to accept parameters */
    printer.p(className + "::" + constructorName + "()" );
    printer.p(" : ");
    printer.p("__vptr(&__vtable) ");
    printer.pln();
    visit(n);
  }

  public void visitFieldDeclaration(GNode n){
    v("/* visiting Field Declaration */");
	  visit(n);
  }

  public void visitExpressionStatement(GNode n){
    printer.indent().p(n.getNode(0)).pln(';');
  }

  public void visitExpression(GNode n) {
    printer.p(n.getNode(0));
    printer.p(' ').p(n.getString(1)).p(' ').p(n.getNode(2));
  }
  public void visitPrimaryIdentifier(GNode n) {
    printer.p(n.getString(0));
  }  

  public void visitDeclarator(GNode n){
	  visit(n);
  }


  /***************************************************************/
  /*******************  XTC Helper Methods  **********************/
  /***************************************************************/


  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  /**
   * Fold the specified qualified identifier.
   *
   * @param qid The qualified identifier.
   * @param size Its size.
   */
  protected String fold(GNode qid, int size) {
    StringBuilder buf = new StringBuilder();
    for (int i=0; i<size; i++) {
      buf.append(qid.getString(i));
      if (i<size-1) buf.append('.');
    }
    return buf.toString();
  }

  /***************************************************************/
  /***************** Custom Helper Methods  **********************/
  /***************************************************************/

  /* Unless the string is null, print it out as a line */
  private void printlnUnlessNull(String s){
    printlnUnlessNull(s, s);
  }

  /* Unless the compare string is null, print another string out as a line */
  private void printlnUnlessNull(String s, String compare){
    if (!(compare == null)) printer.pln(s);
    else return;
  }

  /* Print verbose debug messages into output file */
  private void v(String s){
    if(VERBOSE) printer.pln(s);
  }

}
