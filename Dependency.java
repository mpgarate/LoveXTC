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

import java.util.LinkedList;

public class Dependency extends Visitor {

  LinkedList<GNode> depList = new LinkedList<GNode>();
  LinkedList<String> addressList = new LinkedList<String>();

	/** The printer for this C printer. */

  public GNode root;

	public Dependency(LinkedList<GNode> ll){
    depList = ll;
	}

  /* fills the addresslist with the addresses of the dependencies */ 
  public void makeAddressList() {
    for (int i = 0; i < depList.size(); i++) {
      if (depList.get(i) != null) {
        new Visitor() {
          public void visitPackageDeclaration(GNode n) {
          /* Here we have to get the package name and scan for files
          in the same directory with the same package name declared.

          These files then have to each pass through Dependency.java
          before adding themselves to the list. */

          /* Package name: n.getNode(1).getString(0) */
          visit(n);
          }

          public void visitImportDeclaration(GNode n) {

          /*
            These files have to each pass through Dependency.java
            before adding themselves to the list.
          */

          visit(n);
          }

          public void visitExtension(GNode n) {
            addDependency(n.getNode(0).getNode(0).getString(0));
            visit(n);
          }

          public void visit(Node n) {
            for (Object o : n) if (o instanceof Node) dispatch((Node) o);
          }
        }.dispatch(depList.get(i));
      }
    }
  }
  
  /* uses the addressList to return the nodeList */
  public LinkedList<GNode> makeNodeList() {
    return depList;
  }
  
  /*
    Use this method for adding dependencies in case we need to 
    perform some sanitization. Take a String, use that to locate
    the file, create a java AST, and add this AST to depList.
  */
  public void addDependency(String dep){
    /* We must use this string to locate the file and build a java AST */
    addressList.add(dep);
  }

  /* Remove any dependencies that are not actually used */
  public void trimDependencies(){
    /*
      We can check if a class is actually used with
      visitCallExpression(Gnode n)
    */
  }
}
