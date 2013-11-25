/* Overloader - determines the right method name in method call */

package xtc.oop;

import java.lang.*;
import xtc.lang.JavaEntities;


import xtc.Constants;
import java.util.LinkedList;
import xtc.tree.LineMarker;
import xtc.tree.Attribute;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Pragma;
import xtc.tree.SourceIdentity;
import xtc.tree.Token;
import xtc.tree.Visitor;
import xtc.util.SymbolTable;
import xtc.util.SymbolTable.Scope;
import xtc.type.*;
/* End imports based on src/xtc/lang/CPrinter.java */

public class Overloader extends Visitor {
    final private SymbolTable table;
    Inheritance inheritanceTree;
  private static final boolean VERBOSE = false;
  LinkedList<String> temp = new LinkedList<String>();


 
  private String className;
  private String javaClassName;


	public Overloader(SymbolTable table, Inheritance inh){
    this.table = table;
    this.inheritanceTree = inh;

	}

	public void visitCompilationUnit(GNode n) {
    if (null == n.get(0))
      visitPackageDeclaration(null);
    else
      dispatch(n.getNode(0));

    table.enter(n);
    
    for (int i = 1; i < n.size(); i++) {
      GNode child = n.getGeneric(i);
      dispatch(child);
    }

    table.setScope(table.root());
  }

	public void visitClassDeclaration(GNode n) {
    table.enter(n);
    className = n.getString(1);
    visit(n);
    table.exit();
  }

  public void visitPackageDeclaration(GNode n) {
    if (! (n == null)){
      table.enter(n);
    }

  }
  public void visitImportDeclaration(GNode n) {
    visit(n);
  }

	public void visitClassBody(GNode n) {
    visit(n);
  }

  public void visitMethodDeclaration(GNode n){
    table.enter(n);
    
    if (!(n.getNode(4).size() !=0)) {
      javaClassName = n.getString(5);
    }
    visit(n);
    table.exit();
  }


  public void visitBlock(GNode n){

    table.enter(n);
    visit(n);
    table.exit();
  }

  public void visitExpressionStatement(GNode n){
    visit(n);
  }

  public void visitCallExpression(GNode n){
    if (n.getNode(0) != null){
      String variableName = n.getNode(0).getString(0);
      String nameOfClass;
      if (table.current().isDefined(variableName)) {
      Type type = (Type) table.current().lookup(variableName);
      nameOfClass = type.toAlias().getName();
      Translator.LOGGER.info("variableName " + variableName + " of className " + nameOfClass);
      }
    }
    /*LinkedList<String> methods = inheritanceTree.getVTableForNode(javaClassName);
    printer.p(methods.toString());
    String parent = inheritanceTree.getParentOfNode("C");
    printer.p(parent);*/
  }

  public void visitArguments(GNode n){
    
  }  

  public void visitAdditiveExpression(GNode n){
    
  } 

  
  public void visitPrimaryIdentifier(GNode n) {
    
  } 

  

  public void visitNewClassExpression(GNode n){
    
  }



  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }
}