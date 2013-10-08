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

/* End Translator.java imports *

/* Make assertions for debugging */
import static org.junit.Assert.*;

public class ASTBuilder{
	public GNode root, packageDeclaration, includeDeclaration, classDeclaration, classBody;

	public ASTBuilder(){
		root = GNode.create("TranslationUnit");
	}
	public GNode getRoot() {
		return root;
	}

	public void createClassDeclaration(GNode n){
		GNode classDeclaration = GNode.create("ClassDeclaration");
		/* 0 get the name of the class */
		String class_name = n.getString(1);
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

		root.addNode(classDeclaration);
	}

	public void createMethodDeclaration(GNode n){
		GNode methodDeclaration = GNode.create("MethodDeclaration");

		/* 0 Return type */
		String return_type = n.getNode(2).getNode(0).getString(0);
		methodDeclaration.add(0, return_type);
		/* 1 Name */
		String method_name = n.getString(3);
		methodDeclaration.add(1, method_name);
		/* 2 Arguments 
		   creating it as a separate node.
		   NEED: a separate method for this (multiple parameters).
		*/
		GNode arguments = GNode.create("Arguments");
		methodDeclaration.add(2, arguments);
		String modifier = n.getNode(4).getNode(0).getNode(1).getNode(0).getString(0);
		arguments.add(0,modifier);
		String parameter = n.getNode(4).getNode(0).getString(3);
		arguments.add(1,parameter);
		/* 3 Block */
		addBlock(n.getNode(7), methodDeclaration);
		classBody.addNode(methodDeclaration);
	}

	private void addBlock(Node n, GNode parent){
		/* 0 unordered */
		GNode block = GNode.create("Block");
		parent.add(3, block);
		addFieldDeclaration(n.getNode(0), block);

	}

	private void addFieldDeclaration(Node n, GNode parent){
		GNode fieldDeclaration = GNode.create("Field Declaration");
		/* 0 Modifiers */
		String modifier = null;
		fieldDeclaration.add(0, null);
		/* 1 Type */
		String type = n.getNode(1).getNode(0).getString(0);
		fieldDeclaration.add(1, type);

		/* 2 Declarators */
		addDeclarator(n.getNode(2), fieldDeclaration);

		parent.addNode(fieldDeclaration);
	}

	private void addDeclarator(Node n, GNode parent){
		GNode declarator = GNode.create("Declarator");

		String left_side = n.getNode(0).getString(0);
		declarator.add(0,left_side);

		String right_side = n.getNode(0).getNode(2).getString(0);
		declarator.add(1,right_side);

		parent.add(2, declarator);
	}
}