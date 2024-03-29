package xtc.oop;

import java.lang.*;
import xtc.lang.JavaEntities;

/* Imports based on src/xtc/lang/CPrinter.java */
import java.util.Iterator;
import xtc.Constants;
import java.util.LinkedList;
import xtc.tree.LineMarker;
import xtc.tree.Attribute;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Pragma;
import xtc.tree.Printer;
import xtc.tree.SourceIdentity;
import xtc.tree.Token;
import xtc.tree.Visitor;
import xtc.util.SymbolTable;
import xtc.util.SymbolTable.Scope;
import xtc.type.*;
/* End imports based on src/xtc/lang/CPrinter.java */

import java.util.logging.Logger;
/**
 * CCCP is a C++ printer using the visitor pattern. This file contains Visitor methods to go to each node
 * and helper methods to handle various contextual conditions for printing nodes.
 */

public class CCCP extends Visitor {
  final private SymbolTable table;
  Inheritance inheritanceTree;
  private static final boolean VERBOSE = false;
  LinkedList<GNode> classFields;
  LinkedList<String> constructorargs = new LinkedList<String>();
  private final static Logger LOGGER = Logger.getLogger(Dependency.class .getName());

	/** The XTC Printer instance for this CPP printer. */
  protected Printer printer;
  /** Node on which the current CCCP instance is dispatched. */
  public GNode root;
  private LinkedList<String> staticMethods;


  private String packageName;
  private String className;     // has prefix underscores, ie __HelloWorld
  private String javaClassName; // has no underscoresm, ie HelloWorld

  /** Remember certain states within the tree for logic across visitor methods. */
  private boolean visitedConstructor = false;
  private boolean visitedNewClassExp = false;
  private boolean visitedConstructorFormalParam = false;
  private boolean createdInitMethod = false;
  private boolean insideConstBlock = false;
  private boolean inForControl = false;
  private boolean inMainMethod = false;
  private boolean inInitMethod = false;
  private boolean visitedSelectionExpression = false;
  private boolean inLoveField = false;

 /** 
  * Constructor for CCCP
  * 
  * @param p XTC Printer instance for this C++ printer.
  * @param table SymbolTable for referencing scopes.
  * @param inh Inheritance Tree for the current project in translation.
  * @param staticMethodNames A linked list of static method names. 
  */

	public CCCP(Printer p, SymbolTable table, Inheritance inh, LinkedList<String> staticMethodNames){
    this.printer = p;
    this.table = table;
    printer.register(this);
    this.inheritanceTree = inh;
    this.staticMethods = staticMethodNames;
	}


  /***************************************************************/
  /********************  Visit Love AST  *************************/
  /***************************************************************/

  public void visitLoveFieldDeclaration(GNode n){
    classFields.add(n);
  }

  /***************************************************************/
  /********************  Visitor Methods  ************************/
  /***************************************************************/

	public void visitCompilationUnit(GNode n) {
    v("/* Visiting translation unit for " + n.getLocation().toString() + " */");
    //visit(n);
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
    v("/* visiting class declaration */");
    table.enter(n);
    className = n.getString(1);
    javaClassName = className.substring(2);
    visit(n);
    table.exit();
  }

  /* TRICKY need to have the namespace scope */
  public void visitPackageDeclaration(GNode n) {
    v("/* visiting package declaration */");
    if (! (n == null)){
      table.enter(n);
      GNode qid  = n.getGeneric(1);
      int   size = qid.size();
      packageName = fold(qid,size);
    }

  }
  public void visitImportDeclaration(GNode n) {
    v("/* visiting Import declaration */");
    printer.p("using namespace ");
    visit(n);
    printer.pln(";");
    printer.pln("");
  }

	public void visitClassBody(GNode n) {
    v("/* visiting class body */");
    /* Begin the namespace. */
    printer.incr();
    printlnUnlessNull("namespace " + packageName + " {", packageName);
    classFields = new LinkedList<GNode>();
    visitedConstructor = false;
    visit(n);
    if (visitedConstructor == false) {
      printFallbackinit();
    }
    printClassMethod(); 

    printlnUnlessNull("}",packageName); //Closing namespace
    printer.decr();
  }


  public void visitMethodDeclaration(GNode n){
    v("/* visiting method declaration */");
    table.enter(n);
    String methodName = n.getString(3);
    if (methodName.equals("main")) {
      inMainMethod = true;
      printer.pln("void " + className + "::main(__rt::Ptr<__rt::Array<String> > args) {");
      printer.pln(javaClassName + " __this = " + className + "::init(new " + className + "());");
      printer.p(n.getNode(7));
      inMainMethod = false;
      printer.pln("}");
      printDefaultMainMethod();
    }
    else {
    printer.p(n.getNode(2));
    if (n.getNode(2).hasName("VoidType")){
      printer.p("void");
    }
    printer.p(" ");

    printer.p(className + "::" + methodName);
    if (n.getNode(4).size() !=0) {
      //printer.p("(").p(n.getNode(4)).p(") {");
      printer.p(n.getNode(4)).p(" {");
      //printer.p("(parameters please)" + " {");
      printer.incr();
    }
    else {
      //javaClassName = n.getString(5);
      printer.p("(" + javaClassName + " __this)" + " {");
    }
    printer.pln();
    printer.p(n.getNode(7));
    printer.pln("}");
  }
  table.exit();
  }
  /** Visit the specified type. */
  public void visitType(GNode n) {
    String dimensions = null;

    if(inLoveField){
      /* Already printed "__this" in printLoveField */
      return;
    }

    if(null != n.getNode(1)){
      dimensions = n.getNode(1).getString(0);
    }
    if(dimensions != null && dimensions.equals("[")){ //create an array
      if(n.getNode(0).hasName("QualifiedIdentifier")){
        //__rt::Ptr<__rt::Array<String> > 
        printer.p("__rt::Ptr<__rt::Array<");
        printer.p(n.getNode(0).getString(0));
        printer.p("> >");
      }
      else{
        printer.p("__rt::Array<");
        printer.p(n.getNode(0));
        printer.p(">*");
      }
    }
    else{
      printer.p(n.getNode(0));
    }
  }

  /** Visit the specified primitive type. */
  public void visitPrimitiveType(GNode n) {
    if(n.getString(0).equals("int")){
      printer.p("int32_t");
    }
    else{
      printer.p(n.getString(0));
    }
  } 

  public void visitStringLiteral(GNode n){
    printer.p(n.getString(0));
  }
  public void visitCastS(GNode n){
    printer.p(n.getString(1));
  }


  public void visitBlock(GNode n){
    v("/* visiting block */");
    table.enter(n);
    printer.pln("{");
    visit(n);
    printer.decr();
    printer.pln("}");
    printer.pln();
    table.exit();
  }

  public void visitBasicForControl(GNode n){
    inForControl = true;
    printer.p("(");
    visit(n);
    inForControl = false;
    printer.p(")");
    printer.pln();
  }

  public void visitForStatement(GNode n){
    table.enter(n); 
    printer.p("for");
    visit(n);
    table.exit();
  }

  public void visitWhileStatement(GNode n){
    printer.p("while(");
    dispatch(n.getNode(0));
    printer.p(")"); 
    dispatch(n.getNode(1));
  }

  public void visitPostfixExpression(GNode n){
    visit(n);
    printer.p(n.getString(1));
  }

  public void visitRelationalExpression(GNode n){
    dispatch(n.getNode(0));
    printer.p(n.getString(1));
    dispatch(n.getNode(2));
    if(inForControl){
      printer.p(";");
    }
  }

  public void visitSubscriptExpression(GNode n){
    dispatch(n.getNode(0));
    printer.p("->__data");
    printer.p("[");
    dispatch(n.getNode(1));
    printer.p("]");
  }

  public void visitConstructorDeclaration(GNode n){
    visitedConstructor = true;
    printFallbackConstructor();
    String constructorName = n.getString(2);
    if (n.getNode(3).size() == 0){
      printer.p(javaClassName + " " + className + "::init(" +javaClassName + " __this" );
      createdInitMethod = true;
    }
    else {
      visitedConstructorFormalParam = true;
      printer.p(javaClassName + " " + className + "::init(" +javaClassName + " __this, " );
    }
    printer.p(n.getNode(3));
    for (int i = 0; i < n.getNode(3).size(); i++){
      constructorargs.add(n.getNode(3).getNode(i).getString(3));
    }
    printer.pln("){");
    inInitMethod = true;
    String parent = inheritanceTree.getParentOfNode(javaClassName);
    /* What if the parent constructor also has arguments? May need to fix this later*/
    if (parent != null){
      printer.pln("__"+ parent + "::init(__this);");
    }
    insideConstBlock = true;
    visit(n.getNode(5));
    insideConstBlock = false;
    for (int i = 0; i < classFields.size(); i++){
      printLoveField(classFields.get(i));
    }
    printer.pln("return __this;");
    inInitMethod = false;
    printer.p("}");
    printer.pln();
    printer.pln();
  }

  public void visitSelectionExpression(GNode n){
    visit(n);
    printer.p("->" + n.getString(1));
    visitedSelectionExpression = true;
  }

  private void printLoveField(GNode n){
    if (n.getNode(2).hasName("Declarators")){
      GNode declaration = (GNode)n.getNode(2).getNode(0);
      if (declaration.getNode(2) != null){
        if (declaration.getNode(2).hasName("StringLiteral")){
          printer.p("__this->" + declaration.getString(0));
          printer.p(" = ");
          //printer.p("__String::init(new __String(");
          printer.p(declaration.getNode(2));
          printer.p(";");
          printer.pln();  
        }
        else{
          printer.p("__this->");
          inLoveField = true;
          visit(n);
          inLoveField = false;
          printer.p(";");
          printer.pln();
        }
      }
    }
  }

  private void printFallbackinit(){
    printer.pln();
    printFallbackConstructor();
    printer.pln(javaClassName + " " + className + "::init(" +javaClassName + " __this){" );
    String parent = inheritanceTree.getParentOfNode(javaClassName);
    if (parent != null){
      printer.pln("__"+ parent + "::init(__this);");
    }
    for (int i = 0; i < classFields.size(); i++){
      printLoveField(classFields.get(i));
    }
    printer.pln("return __this;");
    printer.p("}");
    printer.pln();
    printer.pln();

  }

  public void visitFieldDeclaration(GNode n){
    v("/* visiting Field Declaration */");
    
	  visit(n);
    printer.p(";");
    printer.pln();
    if (visitedNewClassExp || visitedSelectionExpression){
      printer.p("__rt::checkNotNull(");
      String id = n.getNode(2).getNode(0).getString(0);
      printer.p(id+");");
      printer.pln();
      visitedNewClassExp = false;
    }
  }
  public void visitReturnStatement(GNode n){
    printer.p("return ");
    if (n.getNode(0).getName().equals("StringLiteral")){
      printer.p("new __String(");
      visit(n);
      printer.p(")");
    }
    else{
      visit(n);
    }
    printer.p(";").pln();
  }

  public void visitExpressionStatement(GNode n){
    visit(n);
    printer.pln(";");
  }

  public void visitCallExpression(GNode n){
    if (n.getNode(0).hasName("SuperExpression")){
      String parent = inheritanceTree.getParentOfNode(javaClassName);
      printer.pln(parent +" tmp = __"+ parent + "::init(new __"+ parent+"());");
    }
    //printer.p(n.getNode(0));
    if (n.getNode(0).hasName("CallExpression")){
      String methodName = "";
      printer.p(n.getNode(0));
      String variableName = "_empty";
      String name_of_class = "_empty";
      if (n.getNode(0).getNode(0).hasName("PrimaryIdentifier")){
        methodName = n.getNode(0).getString(2);
        variableName = n.getNode(0).getNode(0).getString(0);
        if (table.current().isDefined(variableName)) {
          Type type = (Type) table.current().lookup(variableName);
          name_of_class = type.toAlias().getName();
        }
      }
      String returntype = "";
      if (!(name_of_class.equals("_empty"))){
        returntype = inheritanceTree.getReturnType(methodName, name_of_class);
      }
      printer.p("->__vptr->");
      printer.p(n.getString(2) + "(");
      printer.p("__" + returntype + "::init(new __" + returntype + "())");
      if(n.getNode(3).size() > 0)
      printer.p(", ");  
      printer.p(n.getNode(3));    
      printer.p(")");

    }
    else {
      String variableName = "";
      if (n.getNode(0).hasName("PrimaryIdentifier")){
        variableName = n.getNode(0).getString(0);
      }
      boolean static_name = false;
      boolean using_static_class_name = false;
      if (staticMethods.contains(n.getString(2))){
        static_name = true;
        if (!(table.current().isDefined(variableName))){
          using_static_class_name = true;
        }
      }
      if (static_name){
        if(using_static_class_name){
          printer.p("__"+n.getNode(0).getString(0)+"::");
        }
        else{
          printer.p(n.getNode(0));
          printer.p("->");
        }
      }
      else{
        printer.p(n.getNode(0));
        printer.p("->__vptr->");
      }
      printer.p(n.getString(2) + "(");
      if (using_static_class_name){
        printer.p("__" + variableName + "::init(new __" + variableName + "())");
      }
      else{
        printer.p(n.getNode(0));
      }
      if(n.getNode(3).size() > 0)
      printer.p(", ");	
      printer.p(n.getNode(3));    
      printer.p(")");
    }
    /*LinkedList<GNode> methods = inheritanceTree.getVTableForNode(javaClassName);
    printer.p(methods.toString());*/
  }

  public void visitSuperExpression(GNode n){
    String parent = inheritanceTree.getParentOfNode(javaClassName);
    printer.p("tmp");
  }

  public void visitInstanceOfExpression(GNode n){
    String variableName = n.getNode(0).getString(0);
    String start = null;
    if (table.current().isDefined(variableName)) {
      Type type = (Type) table.current().lookup(variableName);
      if (type.hasAlias()){
        start = type.toAlias().getName();
      }
    }
    String target =  n.getNode(1).getNode(0).getString(0);
    if (start != null){
      if (getDistance(start,target) >= 0){
        printer.p("\"true\"");
      }
    }
  }

  public void visitFloatingPointLiteral(GNode n){
    printer.p(n.getString(0));
  }

  private int getDistance(String start, String target){

    if(start.equals(target)) return 0;
   
    int distance = 0;
    boolean found = false;
    String parent = start;

    while (!parent.equals(target)){
      parent = inheritanceTree.getParentOfNode(parent);
      if (parent.equals(target)){
        found = true;
      }
      distance++;

      if (parent.equals("Object")){
        break;
      }
      if (parent.equals("No Parent Found")){
        found = false;
        break;
      }
    }

    if (found) {
      return distance;
    }
    else{
      return -1; //could not find
    }
  }

  public void visitArguments(GNode n){

      for (int i = 0; i < n.size() ; i++){
        if (n.getNode(i).hasName("StringLiteral")){
          printer.p("__String::init(new __String(");
          printer.p(n.getNode(i));
          printer.p("))");
        }
        else{
          printer.p(n.getNode(i));
        }
        if (! (i==n.size()-1)){
          printer.p(",");
        }
      }

  }  

  public void visitAdditiveExpression(GNode n){
    if (n.getNode(0).hasName("visitAdditiveExpression")){
      visit(n);
    }
    else {
      printer.p(n.getNode(0));
    }
    printer.p(n.getString(1));
    printer.p(n.getNode(2));
  } 

  public void visitCoutExpression(GNode n){
    printer.p("cout << ");
    visit(n);
    printer.p(" <<endl");
  }

  public void visitCoutCallExpression(GNode n){
    String functionName = n.getString(1);
    if (inMainMethod && (table.current().isDefined(functionName))){
      //cout << __Test020::x(new __Test020) <<endl;
      printer.p(className + "::"  + functionName);
      printer.p("(");
      printer.p("new " + className);

      Node arguments = n.getNode(2);
      if(arguments.size() > 0){
        printer.p(",");
      }

      visit(n);
      printer.p(")");
    }
    else{   
      printer.p(n.getNode(0));
      printer.p("->__vptr->");
      printer.p(functionName);
      printer.p("(");
      visit(n);
      printer.p(")");
      /*if (functionName.equals("toString")){
        printer.p("->data");
      }*/ 
    }

  }
  public void visitCoutAdditiveExpression(GNode n){
    String variableName = n.getNode(2).getString(0);
    if (n.getNode(0).hasName("visitCoutAdditiveExpression")){
      visit(n);
    }
    else {
      printer.p(n.getNode(0));
    }
    printer.p(" << ");
    boolean primT = false;
    boolean castAsInt = false;

    if (table.current().isDefined(variableName)) {
      Type type = (Type) table.current().lookup(variableName);
      if (!type.hasAlias()){
        primT = true;
      }
      String typeAsString = type.toString();
      if (typeAsString.contains("(byte,")){
        castAsInt = true;
      }
    }
    if (castAsInt) printer.p("(int)");
    printer.p(n.getNode(2));
    if ((!primT) && n.getNode(2).hasName("PrimaryIdentifier")){
      printer.p("->__vptr->toString(");
      printer.p(n.getNode(2));
      printer.p(")");
    }
  }

  public void visitExpression(GNode n) {
    printer.p(n.getNode(0));
    if (n.get(1) != null) printer.p(" = ");
    if (n.get(2) != null) printer.p(n.getNode(2));
    //visit(n);
  }

  public void visitPrimaryIdentifier(GNode n) {
    String variableName = n.getString(0);
    if (variableName.equals(javaClassName)){
      variableName = "__this";
    }
    if (insideConstBlock){
      printHandleConst(n);
      return;
    }
    if (table.current().isDefined(variableName)) {
      Type type = (Type) table.current().lookup(variableName);
      if (JavaEntities.isFieldT(type)){
        if (table.current().isDefined(variableName)){
          printer.p("__this->" + variableName);
        }
      }
      else {
        printer.p(variableName);
      }
    }
    else{
        printer.p(variableName);
    }
  } 

  private void printHandleConst(GNode n){
    String variableName = n.getString(0);
    if(constructorargs.size() == 0){
      if (table.current().isDefined(variableName)) {
        Type type = (Type) table.current().lookup(variableName);
        if (JavaEntities.isFieldT(type)){
          printer.p("__this->" + variableName);
        }
        else {
          printer.p(variableName);
        }
      }
      else{
        printer.p(variableName);
      }
    }
    else {
      for (int i = 0; i < constructorargs.size(); i++){
        if (variableName.equals(constructorargs.get(i))){
          printer.p(variableName);
        }
        else {
          printer.p("__this->" + variableName);
        }
      }
    }
  }

  public void visitDeclarators(GNode n){
    if (n.size() == 1) {
      visit(n);
    }
	  else {
      for (int i = 0; i < n.size(); i++) {
        printer.p(n.getNode(i));
        if (! (i == n.size()-1)){
          printer.p(",");
        }

      }
    }

    if (inForControl){
      printer.p(";");
    }
  }

  public void visitThisExpression(GNode n){
    printer.p("__this");
  }

  public void visitDeclarator(GNode n){
    String decName = n.getString(0);
    if (null != n.getNode(2) && n.getNode(2).hasName("NewArrayExpression")){
      String arrayType = n.getNode(2).getNode(0).getString(0);
      if (arrayType.equals("int")){
        arrayType = "int32_t";
      }
      printer.p(decName + " = new __rt::Array<");
      printer.p(arrayType);
      printer.p(">(");
      dispatch(n.getNode(2).getNode(1)); //usually IntegerLiteral
      printer.p(")");
    }
    else if (n.getNode(2) != null){
      printer.p(" " + decName);
      printer.p(" = ");
      visit(n);
    }
    else{
      printer.p(" " + decName);
      visit(n);
    }
  }
  public void visitNewArrayExpression(GNode n){
    String arrayType = n.getNode(0).getString(0);
    printer.p("__rt::Array<");
    printer.p(arrayType);
    printer.p(">* a = new __rt::Array<");
    printer.p(">(");
    dispatch(n.getNode(1));
    printer.p(");");

  }

  public void visitIntegerLiteral(GNode n){
    printer.p(n.getString(0));
  }
  public void visitCastExpression (GNode n){
    printer.p("(");
    printer.p(n.getNode(0));
    printer.p(")");
    printer.p(n.getNode(1));
  }

  public void visitNewClassExpression(GNode n){
    visitedNewClassExp = true;
    String className = fold((GNode)n.getNode(2), n.getNode(2).size());
    printer.p("__"+className+"::init(");
    if (className.equals("Exception")){
      printer.p("new " + className + "(");
    }
    else{
      printer.p("new __" + className + "()");
    }
    if (n.getNode(3).size() > 0){
      printer.p(", ");
      printer.p(n.getNode(3)); 
    }      
    printer.p(")");
  }


  /***************************************************************/
  /*****************  XTC-based Visitor Methods  *****************/
  /***************************************************************/

  /** Visit the specified formal parameter. */
  public void visitFormalParameter(GNode n) {
    final int size = n.size();
    printer.p(n.getNode(0)).p(n.getNode(1));
    for (int i=2; i<size-3; i++) { // Print multiple catch types.
      printer.p(" | ").p(n.getNode(i));
    }
    if (null != n.get(size-3)) printer.p(n.getString(size-3));
    printer.p(' ').p(n.getString(size-2)).p(n.getNode(size-1));
  }
  

  public void visitFormalParameters(GNode n) {
    if (visitedConstructorFormalParam){
      Iterator<Object> iter = n.iterator();
      if (iter.hasNext()){
        for (iter = n.iterator(); iter.hasNext(); ) {
          printer.p((Node)iter.next());
          if (iter.hasNext()) printer.p(", ");
        }
      }
      visitedConstructorFormalParam = false;
    }
    else{
  	Iterator<Object> iter = n.iterator();
  	if(!createdInitMethod){		
        		printer.p('(');
        		printer.p(javaClassName + " __this");			
  	}else{
  		createdInitMethod = false;
  	}
        if (iter.hasNext()){      
  	printer.p(", ");
        for (iter = n.iterator(); iter.hasNext(); ) {
          printer.p((Node)iter.next());
          if (iter.hasNext()) printer.p(", ");
        }
        printer.p(')'); 
      }
    }
  }

  /** Visit the specified qualified identifier. */
  public void visitQualifiedIdentifier(GNode n) {
    if (1 == n.size()) {
        printer.p(n.getString(0));
    } 
    else {
      for (Iterator<Object> iter = n.iterator(); iter.hasNext(); ) {
        printer.p(Token.cast(iter.next()));
        if (iter.hasNext()) printer.p('.');
      }
    }
  }

  /***************************************************************/
  /*******************  XTC Helper Methods  **********************/
  /***************************************************************/


  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  /**
   * Fold the specified qualified identifier.
   *
   * @param qid The qualified identifier.
   * @param size Its size.
   */
  protected String fold(GNode qid, int size) {
    StringBuilder buf = new StringBuilder();
    for (int i=0; i<size; i++) {
      buf.append(qid.getString(i));
      if (i<size-1) buf.append('.');
    }
    return buf.toString();
  }

  /***************************************************************/
  /***************** Custom Helper Methods  **********************/
  /***************************************************************/

  private void printDefaultMainMethod(){
    /* Prints a main method like the following: */
    /*
      int main(int argc, char* argv[]) {
        __rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc - 1);

        for (int32_t i = 1; i < argc; i++) {
          (*args)[i] = __rt::literal(argv[i]);
        }
        
        __Test022::main(args);
        
        return 0;
      }
    */

    printer.pln("int main(int argc, char* argv[]) {");
    printer.pln("__rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc - 1);");
    printer.pln();
    printer.pln("for (int32_t i = 1; i < argc; i++) {");
    printer.pln("(*args)[i-1] = __rt::literal(argv[i]);");
    printer.pln("}");
    printer.pln();
    printer.pln(className + "::main(args);");
    printer.pln("return 0;");
    printer.pln("}");
  }

  /* Unless the string is null, print it out as a line */
  private void printlnUnlessNull(String s){
    printlnUnlessNull(s, s);
  }

  /* Unless the compare string is null, print another string out as a line */
  private void printlnUnlessNull(String s, String compare){
    if (!(compare == null)) printer.pln(s);
    else return;
  }

  private void printClassMethod(){
    printer.pln(className + "_VT " + className + "::__vtable;");
    printer.pln();
    printer.pln("Class " + className + "::__class() {");
    printer.p("static Class k = ");
    printer.p("new __Class(__rt::literal(\"");
    if (packageName != null){
      printer.p(packageName + ".");
    }
    printer.p(javaClassName + "\"), (Class) __rt::null());");
    printer.pln();

    printer.pln("return k;");
    printer.pln("}");
  }

  private void printFallbackConstructor(){
      printer.p(className + "::" + className + "()" );
      printer.p(" : ");
      printer.p("__vptr(&__vtable)");
      printer.p("{");
      printer.p("}");
      printer.pln();
      printer.pln();
  }

  /* Print verbose debug messages into output file */
  private void v(String s){
    if(VERBOSE) printer.pln(s);
  }

}
