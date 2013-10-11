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

  public GNode root;

	public CCCP(Printer printer){
		this.printer = printer;
		printer.register(this);
	}

	public void visitTranslationUnit(GNode n) {
    printer.pln("/* Visiting translation unit */ ");
    visit(n);
  }

	public void visitClassDeclaration(GNode n) {
    printer.pln("/* visiting class declaration */");
    printer.p("Class ").p(n.getString(0)).pln(" {");
    visit(n);
  }

  /* TRICKY need to have the namespace scope */
  public void visitPackageDeclaration(GNode n) {
    printer.pln("/* visiting package declaration */");
    visit(n);
  }
  public void visitImportDeclaration(GNode n) {
    printer.pln("/* visiting Import declaration */");
    printer.p("using ");
    int num = n.size();
    for (int i = 0; i < num; i++) {
      printer.p(n.getString(i));
      if (i < num-1) {
        printer.p("::");
      }
    }
    printer.pln(";");
    visit(n);
  }

	public void visitClassBody(GNode n) {
    printer.pln("/* visiting class body */");
      visit(n);
    printer.pln("}");
  }

  public void visitMethodDeclaration(GNode n){
    printer.pln("/* visiting method declaration */");
    printer.p(n.getString(0)).p(' ');
    printer.p(n.getString(1)).p(" (");
    printer.p(n.getNode(2).getString(0)).p(' ');
    printer.p(n.getNode(2).getString(1)).p(")");
    if (n.get(3) == null) printer.pln(";");
    visit(n);
  }

  public void visitBlock(GNode n){
    printer.pln().pln("/* visiting block */");
    printer.pln("{");
	    visit(n);
    printer.pln("}");
  }

  public void visitFieldDeclaration(GNode n){
    printer.pln("/* visiting Field Declaration */");
    printer.p(n.getString(1)).p(' ');
	  visit(n);
  }

  public void visitDeclarator(GNode n){
  	printer.p(n.getString(0)).p(" = ");
  	printer.p(n.getString(1)).pln(";");
	  visit(n);
  }

  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }
}