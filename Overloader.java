
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
  public String visitCallExpression(GNode n){
    boolean overloaded = false;
    String methodName = n.getString(2);

    for (String o : overloadedNames) { //Detects if there's overloading
      if (o.equals(n.getString(2))) {
        overloaded=true;
        LOGGER.info("Overloading happening for method " + o);
        break;
      }
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

    //If there's no overloading going on, we return the return type.
    if (overloaded==false) {
      String returntype = inheritanceTree.getReturnType(methodName, nameOfClass);
      return returntype;
    }

    /* if method is overloaded then change the method name in the node 
      and then return the return type of that method*/
    if (overloaded){
      LinkedList<String> argumentList = new LinkedList<String>();
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
      /* At this point we have to find the best possible method blindly by 
         trying out all the possibilities. */
      else{
        LOGGER.info("ALERT: NO METHOD FOUND. Ideal method " + actual_method);
        LOGGER.info("ALERT: Looking for someother suitable method");
        // lets call the first helper method to get the best method
        String best_method = find_best(n, methods, argumentList);
        if (best_method != null){
          n.set(2,best_method);
          return inheritanceTree.getReturnType(best_method, nameOfClass);
        }
        // We are screwed at this point.
        else{
          LOGGER.warning("MASSIVE FAILURE: DID NOT FIND THE BEST METHOD");
        }
      }
    }
    return null;
  }
  /* This method gets the parents of the ideal arguments and then calls another helper method to 
     find a suitable method. It keeps doing this over and over until all the parents are Objects
     There is a possibility of infinite loop here. BEAWARE */
  private String find_best(GNode n, LinkedList<String> methods, LinkedList<String> argumentList){
    LinkedList<String> parentNames = new LinkedList<String>();
    LinkedList<String> tmpLL = duplicateLL(argumentList);
    boolean not_found = true;
    String suitable_method = null;
    while (not_found){
      // This loop finds the parents of the tmpLL which is initially the argument list.
      for (int i = 0; i < argumentList.size(); i++){
        String parent = inheritanceTree.getParentOfNode(tmpLL.get(i));
        LOGGER.info("ALERT: parent of " + argumentList.get(i) + "is" + parent);
        if (argumentList.get(i).equals("Object")){
          parentNames.add(argumentList.get(i));
        }
        else{
          parentNames.add(parent);
        }
      }
      // lets call the helper method to find the suitable method.
      suitable_method = find_suitable_method(n, methods, argumentList, parentNames);
      if (suitable_method != null){
        return suitable_method;    
      }
      // If the parent names are all "Objects" then we have to stop.
      int x = 0;
      for (int i = 0; i < parentNames.size(); i++){
        if (parentNames.get(i).equals("Object")){
          x++;
        }
      }
      if (x == parentNames.size()){
        not_found = false;
      }
      // don't forget to set the tmpLL to parentNames so we actually make progress
      tmpLL = parentNames;
    }
    // this should be returning null >> we are screwed.
    return suitable_method;
  }
  // helper method to duplicate a linked list
  private LinkedList<String> duplicateLL(LinkedList<String> old){
    LinkedList<String> newLL = new LinkedList<String>();
    for(String s : old){
      newLL.add(s);
    }
    return newLL;
  }
  /* this helper method calls another helper method to permute the different combinations of the
     arguments*/
  private String find_suitable_method(GNode n, LinkedList<String> methods, LinkedList<String> children, LinkedList<String> parent){
    String actual_method = n.getString(2);
    boolean found = false;
    // This loop calls the helper method by giving it the number of arguments to permute on
    // The first step is to permute 1 arguments and the last step is to permute them all.
    for (int i = 1; i <= children.size(); i++ ){
      String answer = permute_combinations(n, i, methods, children, parent);
      if (answer != null){
        found = true;
        actual_method = answer;
        break;
      }
    }
    if (found){
      return actual_method;
    }
    else 
      return null;
  }
  /* This is where we do the actual permutation and we continually check if the method is in
     the methods list. The for loops are a little confusing but seems to work */
  private String permute_combinations(GNode n, int m, LinkedList<String> methods, LinkedList<String> children, LinkedList<String> parent){
    String actual_method = n.getString(2);
    boolean found1 = false;
    // It takes some time to understand this loop but what it basically does is that
    // it builds a method name based on the int m passed to it and checks if it is in methods list.
    outerloop:
    for (int i = 0; i < children.size(); i++){
      actual_method = n.getString(2);
      for(int j = 0; j < i; j++ ){
        actual_method = actual_method + "_" + children.get(j);
      }
      int x;
      for (x = i; x < i+m; x++){
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
    else
      return null;
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
      if (n.getNode(i).hasName("CallExpression")){
        answer.add(visitCallExpression((GNode)n.getNode(i)));
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