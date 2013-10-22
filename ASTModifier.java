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

import java.util.logging.Logger;
import java.util.logging.Level;

public class ASTModifier extends Visitor {
  private final static Logger LOGGER = Logger.getLogger(Dependency.class .getName());

	/* We should base this file on src/xtc/lang/CPrinter.java */

	/* This file will have a ton of methods of two types:
		
			1 - Visitor methods to go to each node
			2 - Helper methods to test for various contextual conditions for printing nodes  

	*/
  public GNode root;
  public String className;
	public ASTModifier(GNode n){
    root = n;
	}
  public GNode getRoot(){
    return root;
  }



	public void visitCompilationUnit(GNode n) {
    visit(n);
  }

  public void visitPrimitiveType(GNode n){
    if(n.getString(0).equals("int")){
      n.set(0,"int32_t");
    }
  }

	public void visitClassDeclaration(GNode n) {
    className = n.getString(1);
    n.set(1, "__" + className);
    LOGGER.info("Class dec child 1 is " + n.getString(1));
    visit(n);
  }

  public void visitPackageDeclaration(GNode n) {
    visit(n);
  }
  public void visitImportDeclaration(GNode n) {
    visit(n);
  }

	public void visitClassBody(GNode n) {
    visit(n);
  }

  public void visitConstructorDeclaration(GNode n){
    String constructorName = n.getString(2);
    n.set(2, "__" + constructorName);
    GNode constructorBlock = GNode.create("ConstructorBlock");
    GNode constExpressionStatement = GNode.create("ConstructorExpression");
    for (int i = 0; i< n.getNode(5).size(); i++) {
      if(n.getNode(5).getNode(i).hasName("ExpressionStatement")) {
        String ls = n.getNode(5).getNode(i).getNode(0).getNode(0).getString(0);
        String rs = n.getNode(5).getNode(i).getNode(0).getNode(2).getString(0);
        constExpressionStatement.add(ls);
        constExpressionStatement.add(rs);
        constructorBlock.add(constExpressionStatement);
      }
    }
    n.set(5,constructorBlock);
    visit(n);
  }

  public void visitMethodDeclaration(GNode n){
    if (n.getNode(4).size() == 0){
      n.set(5, className);
    }

    visit(n);
  }

  public void visitBlock(GNode n){
    visit(n);
  }

  public void visitFieldDeclaration(GNode n){
	  visit(n);
  }

  public void visitDeclarator(GNode n){
	  visit(n);
  }

  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }
}
