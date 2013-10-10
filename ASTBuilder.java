package xtc.oop;

import xtc.tree.GNode;

/* All the imports from Translator.java */


import java.io.File;
import java.io.IOException;
import java.io.Reader;

import xtc.lang.JavaFiveParser;
import xtc.lang.JavaPrinter;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.util.Tool;

/* End Translator.java imports */

/* Make assertions for debugging */
/* WE BUILD CPP AST AS WE VIST JAVA AST*/
import static org.junit.Assert.*;

public class ASTBuilder{
	public GNode root, packageDeclaration, importDeclaration, classDeclaration, classBody;
	public String class_name;
	/* constructor
	 * @param n The JavaAST Node.
	 */
	public ASTBuilder(Node n){
		root = GNode.create("TranslationUnit");
		makeCPPTree(n);
	}

	public GNode getRoot() {
		return root;
	}

	public String getName() {
		return class_name;
	}

	public void makeCPPTree(Node node){
    new Visitor() {
      public void visitCompilationUnit(GNode n) {
      	visit(n);
      }

      public void visitPackageDeclaration(GNode n) {
      	createPackageDeclaration(n);
      }

      public void visitImportDeclaration(GNode n) {
      	createImportDeclaration(n);
      }

      public void visitClassDeclaration(GNode n) {
        createClassDeclaration(n);
      }

      public void visit(Node n) {
        for (Object o : n) if (o instanceof Node) dispatch((Node) o);
      }
    }.dispatch(node);
  	}
  	/* creating the packageDeclaration Node */
  	public void createPackageDeclaration(GNode n) {
  		packageDeclaration = GNode.create("PackageDeclaration");
  		int num = n.getNode(1).size();
  		// looping over the number of children
  		for (int i = 0; i < num; i++) {
  			String a = n.getNode(1).getString(i);
  			packageDeclaration.add(i,a);
  		}
  		root.addNode(packageDeclaration);
  	}

  	/* creating the includeDeclaration Node */
  	public void createImportDeclaration(GNode n) {
  		importDeclaration = GNode.create("ImportDeclaration");
  		int num = n.getNode(1).size();
  		// looping over the number of children
  		for (int i = 0; i < num; i++) {
  			String a = n.getNode(1).getString(i);
  			importDeclaration.add(i,a);
  		}
  		root.addNode(importDeclaration);
  	}


	public void createClassDeclaration(GNode n){
		GNode classDeclaration = GNode.create("ClassDeclaration");
		/* 0 get the name of the class */
		class_name = n.getString(1);
		classDeclaration.add(0, class_name);
		/* 1 modifiers */
		String modifier = n.getNode(0).getNode(0).getString(0);
		classDeclaration.add(1, modifier);
		/* 2 extension 
		   creating it as a node so that in the future we can add a pointer to it.
		   NEED : a separate method for this
		*/
		GNode extension = GNode.create("Extension");
		classDeclaration.add(2, extension);
		String parent = n.getNode(3).getNode(0).getNode(0).getString(0);
		extension.add(0, parent);
		/* 3 classBody */
		classBody = GNode.create("ClassBody");
		classDeclaration.add(3, classBody);
		createClassBody(n.getNode(5));

		root.addNode(classDeclaration);
	}
	public void createClassBody(Node n) {
		int num = n.size();
		for (int i = 0; i < num; i++){
			if (n.getNode(i).hasName("FieldDeclaration")){
				addFieldDeclaration(n.getNode(i), classBody);
			}
			else if (n.getNode(i).hasName("MethodDeclaration")){
				addMethodDeclaration((GNode)n.getNode(i));
			}
			else if (n.getNode(i).hasName("ConstructorDeclaration")){
				addConstructorDeclaration(n.getNode(i));
			}
		}
	}
	private void addConstructorDeclaration(Node n) {
		GNode constructorDeclaration = GNode.create("ConstructorDeclaration");
		String name = class_name;
		constructorDeclaration.add(0, name);
		GNode arguments = GNode.create("Arguments");
		constructorDeclaration.add(1, arguments);
		int num = n.getNode(3).size();
		for (int i = 0; i < num; i++){
			String type = n.getNode(3).getNode(i).getNode(1).getNode(0).getString(0);
			arguments.add(0,type);
			String parameter = n.getNode(3).getNode(i).getString(3);
			arguments.add(1,parameter);
		}
		addBlock(n.getNode(5), constructorDeclaration);
		classBody.addNode(constructorDeclaration);

	}

	public void addMethodDeclaration(GNode n){
		GNode methodDeclaration = GNode.create("MethodDeclaration");

		/* 0 Return type */
		String return_type = n.getNode(2).getNode(0).getString(0);
		methodDeclaration.add(0, return_type);
		/* 1 Name */
		String method_name = n.getString(3);
		methodDeclaration.add(1, method_name);
		/* 2 Arguments 
		   creating it as a separate node.
		*/
		GNode arguments = GNode.create("Arguments");
		methodDeclaration.add(2, arguments);
		int num = n.getNode(4).size();
		for (int i = 0; i < num; i++){
			String type = n.getNode(4).getNode(i).getNode(1).getNode(0).getString(0);
			arguments.add(0,type);
			String parameter = n.getNode(4).getNode(i).getString(3);
			arguments.add(1,parameter);
		}
		/* 3 Block */
		addBlock(n.getNode(7), methodDeclaration);
		classBody.addNode(methodDeclaration);
	}

	private void addBlock(Node n, GNode parent){
		/* 0 unordered */
		GNode block = GNode.create("Block");
		parent.addNode(block);
		int num = n.size();
		for (int i = 0; i < num; i++){
			if (n.getNode(i).hasName("FieldDeclaration")){
				addFieldDeclaration(n.getNode(i), block);
			}
			if (n.getNode(i).hasName("ExpressionStatement")){
				addExpressionStatement(n.getNode(i),block);
			}
			if (n.getNode(i).hasName("ReturnStatement")){
				addReturnStatement(n.getNode(i), block);
			}
		}

	}

	private void addFieldDeclaration(Node n, GNode parent){
		GNode fieldDeclaration = GNode.create("FieldDeclaration");
		/* 0 Modifiers */
		String modifier = "";
		int num = n.getNode(0).size();
		for(int i = 0; i < num; i++){
			modifier = modifier + n.getNode(0).getNode(i).getString(0) + " ";
		}
		fieldDeclaration.add(0, modifier);
		/* 1 Type */
		String type = n.getNode(1).getNode(0).getString(0);
		fieldDeclaration.add(1, type);

		/* 2 Declarators */
		if (n.getNode(1).getNode(0).hasName("PrimitiveType")){
			addPrimitiveDeclarator(n.getNode(2), fieldDeclaration);
		}
		/*if (n.getNode(1).getNode(0).hasName("QualifiedIdentifier")){
			addObjectDeclarator(n.getNode(2), fieldDeclaration);
		}*/

		parent.addNode(fieldDeclaration);
	}
	private void addExpressionStatement(Node n, GNode parent) {
		GNode expressionStatement = GNode.create("ExpressionStatement");
		if (n.getNode(0).getNode(0).hasName("primaryIdentifier")) {
			String left_side = n.getNode(0).getNode(0).getString(0);
			expressionStatement.add(0, left_side);
			String op = n.getNode(0).getString(1);
			expressionStatement.add(1,op);
			String right_side = n.getNode(0).getNode(2).getString(0);
			expressionStatement.add(2, right_side);
		}
		else {
			String left_side = "this." + n.getNode(0).getNode(0).getString(1);
			expressionStatement.add(0, left_side);
			String op = n.getNode(0).getString(1);
			expressionStatement.add(1,op);
			String right_side = n.getNode(0).getNode(2).getString(0);
			expressionStatement.add(2, right_side);
		}
		parent.addNode(expressionStatement);
	}
	private void addReturnStatement(Node n, GNode parent) {
		GNode returnStatement = GNode.create("ReturnStatement");
		String ret = n.getNode(0).getString(0);
		returnStatement.add(0,ret);
		parent.addNode(returnStatement);
	}

	private void addPrimitiveDeclarator(Node n, GNode parent){
		GNode declarator = GNode.create("PrimitiveDeclarator");

		String left_side = n.getNode(0).getString(0);
		declarator.add(0,left_side);
		/*if (n.getNode(0).getNode(2).getString(0) != null) {
			String right_side = n.getNode(0).getNode(2).getString(0);
			declarator.add(1,right_side);
		}*/

		parent.add(2, declarator);
	}
	/*private void addObjectDeclarator(Node n, GNode parent){
		GNode declarator = GNode.create("ObjectDeclarator");

		String left_side = n.getNode(0).getString(0);
		declarator.add(0,left_side);

		String right_side = n.getNode(0).getNode(2).getString(0);
		declarator.add(1,right_side);

		parent.add(2, declarator);
	}*/
}