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

import static org.junit.Assert.*;

public class Inheritance {
    public GNode root;
    public String class_name;


    public Inheritance(Node n) {
	//Program starts here, we begin by creating Object and String class nodes.
	root = GNode.create("Object");
	GNode headerNode = GNode.create("HeaderDeclaration");
	GNode stringNode = GNode.create("String");

	root.add(headerNode);
	headerNode.add(getObjectDataLayout());
	root.add(stringNode);
	stringNode.add(GNode.create("HeaderDeclaration").add(getStringDataLayoutAndVTable()));

	//buildTree((GNode)n);
    }

    public GNode buildTree(GNode n) { //IGNORE THIS FOR NOW, EVENTUALLY THIS METHOD WILL PARSE THROUGH LINKED LIST
	System.out.println("Building Tree!");
	Visitor LLVisitor = new Visitor() {
		String modifier = "";
		public void visitCompilationUnit(GNode n) {
		    System.out.println("Visiting Compilation Unit");
		    visit(n);
		}
		public void visitClassDeclaration(GNode n) {
		    System.out.println(n.toString());
		    modifier = n.getNode(3).toString();
		    System.out.println(modifier);
		    visit(n);
		}

		public void visit(Node n) {
		    for (Object o : n) {
			if (o instanceof Node) dispatch((Node)o);
		    }
		}
	    };
	return root;
    }

    public GNode getRoot() {
	return root;
    }

    private GNode getObjectVTable() {
	//Create the VTable here for Object Class
	GNode objectVTable = GNode.create("VTable");
	String arg[] = new String[1];
	arg[0] = "Object";
	objectVTable.add(createMethod("__isa", null, "Class"));
	objectVTable.add(createMethod("toString", arg, "String"));
	objectVTable.add(createMethod("hashCode", arg, "int32_t"));
	objectVTable.add(createMethod("getClass", arg, "Class"));
	objectVTable.add(createMethod("equals", new String[]{"Object", "Object"}, "bool"));
	return objectVTable;
    }

    private GNode getObjectDataLayout() {
	//Create the Data Layout here for Object Class
	GNode objectDataLayout = GNode.create("DataLayout");
	objectDataLayout.add(getObjectVTable());
	objectDataLayout.add(GNode.create("Object_FieldDeclaration"));
	return objectDataLayout;
    }

    private GNode getStringDataLayoutAndVTable() {
	//Create the Data Layout for the String Class
	GNode stringDataLayout = getObjectDataLayout();
	stringDataLayout.add(GNode.create("String_FieldDeclaration"));
	stringDataLayout.getNode(0).add(createMethod("length", new String[]{"Object"}, "int32_t"));
	stringDataLayout.getNode(0).add(createMethod("charAt", new String[]{"Object", "int32_t"}, "char"));
	return stringDataLayout;
    }

    private GNode createMethod(String name, String[] args, String returnType) {
	//Create a GNode with method arguments and the returnType as children.  The returnType will always be the first child.
	GNode basic = GNode.create(name);
	basic.add(returnType);
	if (args == null) {
	    return basic;
	}

	for (int i=0;i<args.length;i++) {
	    basic.add(args[i]);
	}
	return basic;
    }

    private GNode createMethodWithModifier(String modifier, String returnType, String[] args, String name) {
	GNode basic = GNode.create(name);
	basic.add(modifier);
	basic.add(returnType);
	if (args == null) {
	    return basic;
	}

	for (int i=0;i<args.length;i++) {
	    basic.add(args[i]);
	}
	return basic;
    }
}