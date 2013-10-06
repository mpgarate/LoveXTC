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

public class ASTBuilder{
	public GNode root, packageDeclaration, includeDeclaration, classDeclaration, classBody;

	public ASTBuilder(GNode n){
		root = GNode.create("TranslationUnit");
	}

	public void createClassDeclaration(GNode n){
		classDeclaration = GNode.create("ClassDeclaration");
		/* 0 get the name of the class */
		/* 1 modifiers */
		/* 2 extension */
		/* 3 classBody */
		classBody = GNode.create("ClassBody");
		classDeclaration.add(3, classBody);

		root.addNode(classDeclaration);
	}

	public void createMethodDeclaration(GNode n){
		GNode methodDeclaration = GNode.create("MethodDeclaration");

		/* 0 Return type */
		/* 1 Name */
		/* 2 Arguments */
		/* 3 Block */

		addBlock(n.get(7), methodDeclaration);
		classBody.addNode(methodDeclaration);
	}

	private void addBlock(Object o, GNode parent){
		GNode n = (GNode)o;

		/* 0 unordered */
		GNode block = GNode.create("Block");
		parent.add(3, block);
		addFieldDeclaration(n.get(0), block);

	}

	private void addFieldDeclaration(Object o, GNode parent){
		GNode n = (GNode)o;

		GNode fieldDeclaration = GNode.create("Field Declaration");
		/* 0 Modifiers */
		String modifier = null;
		fieldDeclaration.add(0, null);
		/* 1 Type */
		String type = n.getGeneric(1).getGeneric(0).getString(0);
		fieldDeclaration.add(1, type);

		/* 2 Declarators */
		addDeclarator(n.get(2), fieldDeclaration);

		parent.addNode(fieldDeclaration);
	}

	private void addDeclarator(Object o, GNode parent){
		GNode n = (GNode)o;
		GNode declarator = GNode.create("Declarator");

		String left_side = n.getGeneric(2).getGeneric(0).getString(0);
		declarator.add(0,left_side);

		String right_side = n.getGeneric(2).getString(0);
		declarator.add(1,right_side);

		parent.add(2, declarator);
	}
}