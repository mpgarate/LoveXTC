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

  private String packageName;
  private String className;
  private String javaClassName;

	public InheritancePrinter(Printer p){
    this.printer = p;
    printer.register(this);
	}


  /***************************************************************/
  /********************  Visitor Methods  ************************/
  /***************************************************************/

  public void visitObject(GNode n){
  	LinkedList<String> globalNames = new LinkedList<String>();
  	for(int i = 0; i<n.size(); i++){
  		if (!(globalNames.contains(n.getNode(0).getName()))){
  			printer.p("got here: " + n.getNode(0).getName());
  		}
  	}
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
