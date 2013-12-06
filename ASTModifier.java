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
  public String className;
	public ASTModifier(){

	}
  public String getName(){
    return className;
  }

	public void visitCompilationUnit(GNode n) {
    visit(n);
  }

  public void visitPrimitiveType(GNode n){
    if(n.getString(0).equals("int")){
      n.set(0,"int32_t");
    }
    else if(n.getString(0).equals("byte")){
      n.set(0,"unsigned char");
    }
  }

	public void visitClassDeclaration(GNode n) {
    className = n.getString(1);
    n.set(1, "__" + className);
    n.set(3, null);
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
    for (int i = 0; i< n.size(); i++) {
      if(n.getNode(i).hasName("FieldDeclaration")) {
        GNode loveFieldDeclaration = GNode.create("LoveFieldDeclaration");
        GNode loveType = GNode.create("LoveType");
        GNode loveDeclarator = GNode.create("LoveDeclarator");
        String ls = n.getNode(i).getNode(1).getNode(0).getString(0);
        String rs = n.getNode(i).getNode(2).getNode(0).getString(0);
        loveType.add(ls);
        loveDeclarator.add(rs);
        loveFieldDeclaration.add(loveType);
        loveFieldDeclaration.add(loveDeclarator);
        n.set(i,loveFieldDeclaration);
      }
    }
    
  }

  public void visitConstructorDeclaration(GNode n){
    String constructorName = n.getString(2);
    n.set(2, "__" + constructorName);
    /*GNode constructorBlock = GNode.create("ConstructorBlock");
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
    n.set(5,constructorBlock);*/
    visit(n);
  }

  public void visitMethodDeclaration(GNode n){
    if (n.getNode(4).size() == 0){
      n.set(5, className);
    }

    visit(n);
  }

  public void visitCallExpression(GNode n){
    /*if(n.getString(2).equals("println")){
      n.set(2, null);
      GNode cout = GNode.create("COUT");
      cout.add("cout");
      n.set(0, cout);
    }*/
    visit(n);
  }
  public void visitExpressionStatement(GNode n){
    if (n.getNode(0).hasName("CallExpression")){
      if (n.getNode(0).getString(2).equals("println")){
        GNode cout = GNode.create("CoutExpression");
        cout.add("Cout");
        cout.add(n.getNode(0).getNode(3));
        n.set(0, cout);
      }
    }
    visit(n);
  }

  public void visitCoutExpression(GNode n){
    GNode coutArgs = GNode.create("CoutArguments");
    for (int i = 0; i < n.getNode(1).size(); i++){
      coutArgs.add(n.getNode(1).getNode(i));
    }
    n.set(1, coutArgs);
    visit(n);
  }

  public void visitCoutArguments(GNode n){
    if (n.getNode(0).hasName("AdditiveExpression")){
      GNode coutAdd = GNode.create("CoutAdditiveExpression");
      coutAdd.add(n.getNode(0).getNode(0));
      coutAdd.add(n.getNode(0).getString(1));
      coutAdd.add(n.getNode(0).getNode(2));
      n.set(0,coutAdd);
    }
    else if(n.getNode(0).hasName("CallExpression")){
      GNode coutCall = GNode.create("CoutCallExpression");
      coutCall.add(n.getNode(0).getNode(0));
      coutCall.add(n.getNode(0).getString(2));
      coutCall.add(n.getNode(0).getNode(3));
      n.set(0,coutCall);
    }
    visit(n);
  }

  public void visitCoutAdditiveExpression(GNode n){
    if (n.getNode(0).hasName("AdditiveExpression")){
      GNode coutAdd = GNode.create("CoutAdditiveExpression");
      coutAdd.add(n.getNode(0).getNode(0));
      coutAdd.add(n.getNode(0).getString(1));
      coutAdd.add(n.getNode(0).getNode(2));
      n.set(0,coutAdd);
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
