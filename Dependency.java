package xtc.oop;

import java.lang.*;
import java.io.IOException;
import xtc.parser.ParseException;
import xtc.lang.JavaFiveParser;
import xtc.parser.Result;
import java.io.File;
import java.io.Reader;

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

import java.util.logging.Logger;

public class Dependency extends Visitor {

  private final static Logger LOGGER = Logger.getLogger(Dependency.class .getName()); 

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
        LOGGER.info("Looping through dependencies");
        new Visitor() {

          /* visitExtension(GNode) will be called in the Inheritance Tree */

          public void visitPackageDeclaration(GNode n) {
            String inputDir = System.getProperty("user.dir");

            /* Here we have to get the package name and scan for files
            in the same directory with the same package name declared.

            These files then have to each pass through Dependency.java
            before adding themselves to the list. */

            /* Package name: n.getNode(1).getString(0) */

            /* Pass an address to processAddress() */

          }

          public void visitImportDeclaration(GNode n) {

          /*
            These files have to each pass through Dependency.java
            before adding themselves to the list.
          */

          }

        }.dispatch(depList.get(i));
      }
    }
  }
  
  /* uses the addressList to return the nodeList */
  public LinkedList<GNode> makeNodeList() {
    return depList;
  }

  /* return a Java AST Gnode */
  public GNode parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser =
      new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (GNode)parser.value(result);
  }

  public void processAddress(String address){
    /* Check if the path ends in .java */
    /* If it does not, call process directory */
    /* If it does, call process file */
    addDependencyPath(address);
  }

  public void processDirectory(){
    /* Scan a directory of files */
    /* Call processFile() on each */
  }

  public void processFile(){
    /* Check if the address is in the list */
    /* Store the address in a linked list */
    /* Pass the address into parse() to create a GNode */
    /* Store the GNode in depList */
  }
  
  /*
    Use this method for adding dependencies in case we need to 
    perform some sanitization. Take a String, use that to locate
    the file, create a java AST, and add this AST to depList.
  */
  public void addDependencyPath(String dep){
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
