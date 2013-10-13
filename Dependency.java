/* CCCP is a C++ printer named for the centralized nature of 
the Soviet Union. */

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

public class Dependency extends Visitor {

  LinkedList<String> depList = new LinkedList<String>();

	/** The printer for this C printer. */

  public GNode root;

	public Dependency(LinkedList<String> ll){
    depList = ll;
	}

  public void visitPackageDeclaration(GNode n) {
    /* Here we have to get the package name and scan for files
    in the same directory with the same package name declared.

    These files then have to each pass through Dependency.java
    before adding themselves to the list. */

    /* Package name: n.getNode(1).getString(0) */
    visit(n);
  }

  public void visitImportDeclaration(GNode n) {

    /* These files have to each pass through Dependency.java
    before adding themselves to the list. */

    visit(n);
  }

  public void visitExtension(GNode n) {
    depList.add(n.getNode(0).getNode(0).getString(0));
    visit(n);
  }

  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }
}
