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

import java.util.logging.Logger;

public class Overloader extends Visitor {
  public final static Logger LOGGER = Logger.getLogger(Dependency.class .getName());
  final private SymbolTable table;
  Inheritance inheritanceTree;
  private static final boolean VERBOSE = false;

  /* making a linked list of primitive types for personal purposes
     and a methos which return if a string is primitive or not
     may or maynot be useful*/
  LinkedList<String> primTypes = new LinkedList<String>();
  public boolean isPrim(String s){
    return primTypes.contains(s);
  }


 
  private String className;
  private String javaClassName;
  private LinkedList<String> overloadedNames;
  private LinkedList<String> staticMethods;


	public Overloader(SymbolTable table, Inheritance inh, LinkedList<String> oNames, LinkedList<String> sNames){
    this.table = table;
    this.inheritanceTree = inh;
    this.overloadedNames = oNames;
    this.staticMethods = sNames;
    primTypes.add("int");
    primTypes.add("byte");
    primTypes.add("long");
    primTypes.add("boolean");
    primTypes.add("double");
    primTypes.add("float");
    primTypes.add("short");
    primTypes.add("char");
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

  /* main work for overloading starts at this point
     basically first we find if a method is overloaded or not.
     if yes, then we visit the arguments to determine the correct name of method*/
  public void visitCallExpression(GNode n){
    boolean overloaded = false;

    for (String o : overloadedNames) { //Detects if there's overloading
      if (o.equals(n.getString(2))) {
        overloaded=true;
        LOGGER.info("Overloading happening for method " + o);
        break;
      }
    }

    //If there's no overloading going on, we don't need to do anything.
    if (overloaded==false) {
      return;
    }
    // name of class is the class name incase of static methods
    String nameOfClass = className;
    // else it is the class of the primary identifier 
    if (n.getNode(0) != null){
      String variableName = n.getNode(0).getString(0);
      if (table.current().isDefined(variableName)) {
      Type type = (Type) table.current().lookup(variableName);
      nameOfClass = type.toAlias().getName();
      LOGGER.info("variableName " + variableName + " of className " + nameOfClass);
      }
    }
    /* if method is overloaded the change the method name in the node 
      else do nothing*/
    if (overloaded){
      LinkedList<String> argumentList = new LinkedList<String>();
      String methodName = n.getString(2);
      String actual_method = n.getString(2);
      LinkedList<String> methods = inheritanceTree.getVTableForNode(nameOfClass);
      argumentList = visitArguments((GNode)n.getNode(3));
      LOGGER.info("ideal method is = " + actual_method);
      for (int i = 0; i < argumentList.size(); i++){
        actual_method = actual_method + "_" + argumentList.get(i);
      }
      /* if the method name just found is legal then we change the name
         else we look for a more suitable method */
      if (methods.contains(actual_method)){
        n.set(2,actual_method);
      }
      /* else if the method found is static inaddition to being overloaded
         then we just use it*/
      else if (staticMethods.contains(actual_method)){
        n.set(2,actual_method);
      }
      /* FIXME: have a more robust way of finding out the suitable method
         For now i am just making it work for this example */
      else{
        LOGGER.info("ALERT: NO METHOD FOUND. Ideal method " + actual_method);
        LOGGER.info("ALERT: Looking for someother suitable method");
        /* WARNING: DOING THIS JUST FOR THIS EXAMPLE */
        LinkedList<String> parentNames = new LinkedList<String>();
        for (int i = 0; i < argumentList.size(); i++){
          String parent = inheritanceTree.getParentOfNode(argumentList.get(i));
          LOGGER.info("ALERT: parent of " + argumentList.get(i) + "is" + parent);
          parentNames.add(parent);
        }
        String suitable_method = find_suitable_method(n, methods, argumentList, parentNames);
        if (suitable_method != null){
          n.set(2,suitable_method);
        }
      }
    }
  }
  /* This method puts the right casting in front of the right primary identifier.
     We will not need to put the implicit casting after implementing smart pointers.
     But for now we have to put the implicit casting ourselves.
     WE HAVE SMART POINTERS NOW. DON'T NEED THIS!!
     
  public void changeArguments(GNode n, String cast){
    for (int i = 0; i < n.size() ; i++){
      if (n.getNode(i).hasName("PrimaryIdentifier")){
        String name = n.getNode(i).getString(0);
        String nameOfClass = "";
        if (table.current().isDefined(name)) {
          Type type = (Type) table.current().lookup(name);
          if (type.hasAlias()){
          nameOfClass = type.toAlias().getName();
          }
          else {
          WrappedT wtype = (WrappedT) table.current().lookup(name);
          nameOfClass = wtype.getType().toString();
          }
        }
        String parent = inheritanceTree.getParentOfNode(nameOfClass);
        LOGGER.info("name = "+name+ " parent = "+parent+ " cast = "+cast);
        if (parent.equals(cast)){
          LOGGER.info("INFO: Applying casting inside the right identifier");
          String newname = "("+cast+")" + name;
          n.getNode(i).set(0, newname);
        }
      }
    }
  }*/
  private String find_suitable_method(GNode n, LinkedList<String> methods, LinkedList<String> children, LinkedList<String> parent){
    String actual_method = n.getString(2);
    boolean found1 = false;
    outerloop:
    for (int i = 0; i < children.size(); i++){
      actual_method = n.getString(2);
      for(int j = 0; j < i; j++ ){
        actual_method = actual_method + "_" + children.get(j);
      }
      int x;
      for (x = i; x < i+1; x++){
          actual_method = actual_method + "_" + parent.get(i);
      }
      for(int k = x; k < children.size(); k++ ){
        actual_method = actual_method + "_" + children.get(k);
      }
      if (methods.contains(actual_method)){
        found1 = true;
        break outerloop;
      }
    }
    if (found1){
      return actual_method;
    }

      LOGGER.info("BIG ALERT: 2ND TRY FAILED. Ideal method " + actual_method);
      actual_method = n.getString(2);
      for (int i = 0; i < parent.size(); i++){
        actual_method = actual_method + "_" + parent.get(i);
      }
    return actual_method;
  }

  public LinkedList<String> visitArguments(GNode n){
    LinkedList<String> answer = new LinkedList<String>();
    if (n.size() == 0){
      return answer;
    }
    for (int i = 0; i < n.size() ; i++){
      if (n.getNode(i).hasName("AdditiveExpression")){
        answer.add(visitAdditiveExpression((GNode)n.getNode(i)));
      }
      if (n.getNode(i).hasName("NewClassExpression")){
        answer.add(visitNewClassExpression((GNode)n.getNode(i)));
      }
      if (n.getNode(i).hasName("PrimaryIdentifier")){
        answer.add(visitPrimaryIdentifier((GNode)n.getNode(i)));
      }
      if (n.getNode(i).hasName("CastExpression")){
        answer.add(visitCastExpression((GNode)n.getNode(i)));
      }
      if (n.getNode(i).hasName("StringLiteral")){
        answer.add(visitStringLiteral((GNode)n.getNode(i)));
      }
    }
    return answer;
  }  

  public String visitAdditiveExpression(GNode n){
    String answer = "";
    LinkedList<String> type = new LinkedList<String>();
    for (int i = 0; i < n.size(); i++){
      if (!(n.get(i) instanceof String) && n.getNode(i).hasName("PrimaryIdentifier")){
        type.add(visitPrimaryIdentifier((GNode)n.getNode(i)));
      }
    }
    /* FIXME: we have to follow java specification to figure out the dominant type
       For now i am just making my own*/
    if (isPrim(type.get(0))){
      if (type.contains("long")){
        answer = "long";
      }
      else if (type.contains("double")){
        answer = "double";
      }
      else{
        answer = "int32_t";
      }
    }
    else {
      LOGGER.info("ALERT: ADDING SOMETHING THAT IS NOT PRIMITIVE");
    }

    return answer;
  } 

  
  public String visitPrimaryIdentifier(GNode n) {
    String variableName = n.getString(0);
    String nameOfClass = "";
    if (table.current().isDefined(variableName)) {
      Type type = (Type) table.current().lookup(variableName);
      if (type.hasAlias()){
        nameOfClass = type.toAlias().getName();
      }
      else {
        WrappedT wtype = (WrappedT) table.current().lookup(variableName);
        nameOfClass = wtype.getType().toString();
      }
    }
    LOGGER.info("variableName " + variableName + " of className " + nameOfClass);
    return nameOfClass;
  } 

  

  public String visitNewClassExpression(GNode n){
    return n.getNode(2).getString(0);
  }
  public String visitCastExpression(GNode n){
    return n.getNode(0).getNode(0).getString(0);
  }
  public String visitStringLiteral(GNode n){
    return "String";
  }




  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }
}