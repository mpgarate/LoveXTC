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
import java.util.LinkedList;

/* End Translator.java imports */

import static org.junit.Assert.*;

public class Inheritance {
	public GNode root;
    public GNode stackNode; //This is the top node of the stack of the nodes that have yet to be processed.
	public String class_name;
	String packageName = null;
	GNode targetNode;

	public Inheritance(LinkedList<GNode> nodeList) {
		// Program starts here, we begin by creating Object and String class
		// nodes.

		root = GNode.create("Object");
		GNode headerNode = GNode.create("HeaderDeclaration");
		GNode stringNode = GNode.create("String");
		GNode classNode = GNode.create("Class");

		root.add(headerNode);
		
		headerNode.add("null");
		headerNode.add("Object");
		headerNode.add(getObjectDataLayout());
		headerNode.add(getObjectVTable());

		root.add(stringNode);
		GNode stringHeader = GNode.create("HeaderDeclaration");
		stringHeader.add("null");
		stringHeader.add("String");
		stringNode.add(stringHeader);
		stringHeader.add(getStringDataLayout());
		stringHeader.add(getStringVTable());

		GNode classHeader = GNode.create("HeaderDeclaration");
		classHeader.add("null");
		classHeader.add("Class");
		classHeader.add(getClassDataLayout());
		classHeader.add(getClassVTable());
		classNode.add(classHeader);
		root.add(classNode);

		root.setProperty("type", "CompilationUnit");
		stringNode.setProperty("type", "CompilationUnit");
		classNode.setProperty("type", "CompilationUnit");
		for (int i = 0; i < nodeList.size(); i++) {
                        buildTree(nodeList.get(i));
                }
		    
	}

	public void buildTree(GNode node) {
		
		     //childCount keeps track of the location of the most recent child in the tree

		    new Visitor() {
		    	
		    public void visitPackageDeclaration(GNode n){
		    	packageName = n.getNode(1).getString(0);
		    }
		    
		    public void visitClassDeclaration(GNode n) {
				/*String classname = n.getString(1);
                GNode classNode = GNode.create(classname);
                classNode.setProperty("type", "CompilationUnit");*/
                String extenstion = null;
                if (n.getNode(3) != null) {
                	extenstion = n.getNode(3).getNode(0).getNode(0).getString(0);
                	GNode parent = findParentInLL(extenstion);
                	buildTree(parent);
                	addTotree(n,parent);
                }
                else {
                	addToTree(n, "Object");
                }

			}
			

			public void visitClassBody(GNode n) {
			  
			}


			public void visit(Node n) {
				for (Object o : n) {
					if (o instanceof Node)
						dispatch((Node) o);
				}
			}
		}.dispatch(node);
		/* need to add the inheritance tree structure not the java ast tree. */
		// root.add(node);
		/* no need for return */
		// return root;
	}
	public addTotree(GNode n, String parent) {
		String classname = n.getString(1);
        GNode classNode = GNode.create(classname);
        classNode.setProperty("type", "CompilationUnit");
		GNode headerNode = GNode.create("HeaderDeclaration");
		headerNode.add(packageName);
		headerNode.add(classname);
		headerNode.add(getObjectDataLayout());
		//redefineObjectdatalayout(headerNode.getNode(2), n);
		headerNode.add(getObjectVTable());
		//refineObjectVTable(headerNode.getNode(3), n);
		classNode.add(headerNode);
		root.add(classNode);

	}
	public redefineObjectdatalayout(GNode dataLayout, GNode javaAST){

	}
	public redefineObjectVTable(GNode vTable, GNode javaAST){
		
	}
	public addTotree(GNode n, GNode parent) {
		String classname = n.getString(1);
        GNode classNode = GNode.create(classname);
        classNode.setProperty("type", "CompilationUnit");
		GNode headerNode = GNode.create("HeaderDeclaration");
		headerNode.add(packageName);
		headerNode.add(classname);
		classNode.add(headerNode);
		GNode parent = findInTree(parent);
		parent.add(classNode);
	}
	public findInTree(GNode parent){
		String name = parent.getNode();
	}
	public GNode findParentInLL(String name){

	}

	

	public GNode getRoot() {
		return root;
	}

	private GNode getObjectVTable() {
		// Create the VTable here for Object Class
		GNode objectVTable = GNode.create("VTable");
		objectVTable.add(createVTMethod("__Class","Class", "Object"));
		objectVTable.add(createVTMethod("toString","String", "Object"));
		objectVTable.add(createVTMethod("hashcode","int32_t",
				"Object"));
		objectVTable.add(createVTMethod("getClass","Class", "Object"));
		objectVTable.add(createVTMethod("equals", "bool", "Object"));

		return objectVTable;
	}

	private GNode getObjectDataLayout() {
		// Create the Data Layout here for Object Class
		boolean isVTable = false;
		GNode objectDataLayout = GNode.create("DataLayout");
		objectDataLayout.add(createDataFieldEntry(null, "__Object_VT*",
				"__vptr", null));
		objectDataLayout.add(createDataFieldEntry("static", "__Object_VT",
				"vtable", null));
		objectDataLayout.add(createConstructor("__Object", null));
		String arg[] = { "Object" };
		String modifier[] = { "static" };
		objectDataLayout.add(createMethod(modifier, "toString", arg, "String",
				"Object"));
		objectDataLayout.add(createMethod(modifier, "hashcode", arg, "int32_t",
				"Object"));
		objectDataLayout.add(createMethod(modifier, "getClass", arg, "Class",
				"Object"));
		objectDataLayout.add(createMethod(modifier, "equals", new String[] {
				"Object", "Object" }, "bool", "Object"));
		objectDataLayout.add(createMethod(modifier, "__class", null, "Class",
				"Object"));
		return objectDataLayout;
	}

	private GNode getStringDataLayout() {
		// Create the Data Layout for the String Class
		boolean isVTable = false;
		GNode stringDataLayout = GNode.create("DataLayout");
		stringDataLayout.add(createDataFieldEntry(null, "__String_VT*",
				"__vptr", null));
		stringDataLayout.add(createDataFieldEntry("static", "__String_VT",
				"vtable", null));
		stringDataLayout.add(createConstructor("String", null));
		String arg[] = { "String" };
		String modifier[] = { "static" };
		stringDataLayout.add(createMethod(modifier, "toString", arg, "String",
				"String"));
		stringDataLayout.add(createMethod(modifier, "hashcode", arg, "int32_t",
				"String"));
		stringDataLayout.add(createMethod(modifier, "getClass", arg, "Class",
				"String"));
		stringDataLayout.add(createMethod(modifier, "equals", new String[] {
				"String", "Object" }, "bool", "String"));
		stringDataLayout.add(createMethod(modifier, "__class", null, "Class",
				"String"));
		stringDataLayout.add(createMethod(new String[] { "static" }, "length",
				new String[] { "__String" }, "int32_t", "String"));
		stringDataLayout.add(createMethod(new String[] { "static" }, "charAt",
				new String[] { "__String", "int32_t" }, "int32_t", "String"));
		return stringDataLayout;

	}

	private GNode getStringVTable() {
		GNode stringVTable = GNode.create("VTable");
		stringVTable.add(createVTMethod("__Class","Class", "String"));
		stringVTable.add(createVTMethod("toString","String", "String"));
		stringVTable.add(createVTMethod("hashcode","int32_t",
				"String"));
		stringVTable.add(createVTMethod("getClass","Class", "Object"));
		stringVTable.add(createVTMethod("equals", "bool", "String"));
		stringVTable.add(createVTMethod("length", "int32_t", "String")); 
		stringVTable.add(createVTMethod("charAt","int32_t", "String"));
		return stringVTable;
	}

	private GNode getClassDataLayout() {
		// Create the Data Layout for the java.lang.Class class
		boolean isVTable = false;
		GNode classDataLayout = GNode.create("DataLayout");
		classDataLayout.add(createDataFieldEntry(null, "__Class_VT*", "__vptr",
				null));
		classDataLayout.add(createDataFieldEntry(null, "String", "name", null));
		classDataLayout.add(createDataFieldEntry(null, "Class", "parent", null));
		classDataLayout.add(createDataFieldEntry("static", "__Class_VT",
				"vtable", null));
		classDataLayout.add(createConstructor("Class", new String[] { "name",
				"parent" }));
		String arg[] = {"Class"};
		String modifier[] = { "static" };
		classDataLayout.add(createMethod(modifier, "toString", arg, "String",
				"Class"));
		classDataLayout.add(createMethod(modifier, "getName", null, "String",
				"Class"));
		classDataLayout.add(createMethod(modifier, "getSuperclass", null,
				"Class", "Class"));
		classDataLayout.add(createMethod(new String[] { "Class", "Object" },
				"isInstance", null, "bool", "Class"));
		classDataLayout.add(createMethod(modifier, "__class", null, "Class",
				"Class"));
		return classDataLayout;

	}

	private GNode getClassVTable() {
		GNode classVTable = GNode.create("VTable");
		classVTable.add(createVTMethod("__Class","Class", "Class"));
		classVTable.add(createVTMethod("toString","String", "Class"));
		classVTable.add(createVTMethod("hashcode","int32_t",
				"Object"));
		classVTable.add(createVTMethod("getClass","Class", "Object"));
		classVTable.add(createVTMethod("equals", "bool", "Object"));
		classVTable.add(createVTMethod("getName","String", "Class"));
		classVTable.add(createVTMethod("getSuperclass","Class", "Class"));
		classVTable.add(createVTMethod("isInstance","bool", "Class"));

		return classVTable;
	}

	private GNode createMethod(String modifiers[], String name, String[] args,
			String returnType, String className) {
		// Create a GNode with method arguments and the returnType as children.
		// The returnType will always be the first child.
		GNode methodDeclaration = GNode.create("DataLayoutMethodDeclaration");
		GNode modifierDeclaration = GNode.create("Modifiers");
		GNode parameters = GNode.create("Parameters");

		if (modifiers != null) {
			for (String mod : modifiers) {
				modifierDeclaration.add(mod);
			}
		}
		methodDeclaration.add(modifierDeclaration);
		methodDeclaration.add(returnType);
		methodDeclaration.add(name);
		/*if(className != null)
			methodDeclaration.add(className);*/

		if (args != null) {
			for (String arg : args) {
				parameters.add(arg);
			}
		methodDeclaration.add(parameters);
	}
	return methodDeclaration;
}
private GNode createVTMethod (String name , String returnType, String point) {

		GNode methodDeclaration = GNode.create("VTableMethodDeclaration");
			methodDeclaration.add(returnType);
			methodDeclaration.add(name);
			GNode pointer = GNode.create("Pointer");
			pointer.add(point);
			methodDeclaration.add(pointer);

		return methodDeclaration;
	}

	private GNode createDataFieldEntry(String modifier, String type,
			String name, String declarator) {
		GNode node = GNode.create("FieldDeclaration");
		GNode modifiers = GNode.create("Modifiers");
		GNode declarators = GNode.create("Declarators");

		if (modifier != null) {
			modifiers.add(modifier);
		}

		node.add(modifiers);
		node.add(type);
		node.add(name);

		if (declarator != null) {
			declarators.add(declarator);
		}

		node.add(declarators);
		return node;
	}

	private GNode createMethodWithModifier(String modifier, String returnType,
			String[] args, String name) {
		GNode basic = GNode.create(name);
		basic.add(modifier);
		basic.add(returnType);
		if (args == null) {
			return basic;
		}

		for (int i = 0; i < args.length; i++) {
			basic.add(args[i]);
		}
		return basic;
	}

	private GNode createConstructor(String classname, String parameters[]) {
		GNode constructor = GNode.create("ConstructorDeclaration");
		GNode constructorParameters = GNode.create("Parameters");
		constructor.add(classname);

		if (parameters != null) {
			for (String param : parameters) {
				constructorParameters.add(param);
			}
		}
		constructor.add(constructorParameters);
		return constructor;
	}

	
    private GNode searchForNode(GNode node, String name) {
		// DOES A DEPTH-FIRST SEARCH THROUGH THE TREE FOR A NODE OF SPECIFIC NAME
	if (node.getNode(0).getString(1).equals(name)) {
	    return node;
	}
	else if (node.size() == 1) {
	    return null;
	}
	else {
	    for (int i=1;i<node.size();i++) {
		GNode foundNode = searchForNode((GNode)node.getNode(i), name);
		if (foundNode != null) {
		    return foundNode;
		}
	    }
	}
	return null;
    }
	public GNode parseNodeToInheritance(GNode n){
		new Visitor(){
			public void visitPackageDeclaration(GNode n){
				
			}
			
			public void visitClassDeclaration(GNode n){
				
			}
			
			public void visit(GNode n) {
				for (Object o : n) {
					if (o instanceof Node)
						dispatch((Node) o);
				}
			}
		}.dispatch(n);
		
		return n;
	}

	

	

	
	

	// Converts the java type to the corresponding C++ Type
	private String convertType(String javaType) {
		String cppType = javaType;
		if (javaType.equals("int"))
			cppType = "int32_t";
		return cppType;
	}

}
