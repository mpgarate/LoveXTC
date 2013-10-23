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
	public String class_name;
	int childCount = 3; //SEE Buildtree function
        int[] extraNodes = new int[50];

	    public Inheritance(LinkedList<GNode> nodeList) {
		    // Program starts here, we begin by creating Object and String class
		    // nodes.

		    root = GNode.create("Object");
		    GNode headerNode = GNode.create("HeaderDeclaration");
		    GNode stringNode = GNode.create("String");
		    GNode classNode = GNode.create("Class");

		    root.add(headerNode);

		    headerNode.add(getObjectDataLayout());
		    headerNode.add(getObjectVTable());

		    root.add(stringNode);
		    GNode stringHeader = GNode.create("HeaderDeclaration");
		    stringNode.add(stringHeader);
		    stringHeader.add(getStringDataLayout());
		    stringHeader.add(getStringVTable());

		    GNode classHeader = GNode.create("HeaderDeclaration");
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

		    for(int i=0;extraNodes[i]!=0;i++) { //Gonna replace this shit with something better later, this just makes sure HelloUniverse is placed in the tree as child of HelloWorld
			GNode parent = findParentNode(root, (String)root.getNode(extraNodes[i]).getProperty("parent"));
			parent.add(root.getNode(extraNodes[i]));
			root.remove(extraNodes[i]);
		    }
	    }

	    public void buildTree(GNode node) {
		
		     //childCount keeps track of the location of the most recent child in the tree

		    new Visitor() {


			public void visitClassDeclaration(GNode n) {
				String classname = n.getString(1);
				GNode classNode = GNode.create(classname);
				classNode.setProperty("type", "CompilationUnit"); //all class nodes should have type compilationunit so we can easily identify them.
				root.add(classNode);
				childCount++;
				classNode.setProperty("n", n);
				visit(n);
				/*
				 * if(n.get(3) instanceof Node){ Node child = n.getNode(3); if
				 * (child.hasName("Extension")){
				 * System.out.println("This class has a parent"); } }
				 */
				return;
			}
			
			public void visitExtension(GNode n) {
			    childCount--;
			    GNode thisNode = (GNode)root.getNode(childCount);
			    String parent = n.getNode(0).getNode(0).getString(0);
			    GNode parentNode = findParentNode(root, parent);
			    if (parentNode == null) {
				System.out.println("Did not find parent node for " + n.getLocation().toString());
				extraNodes[0] = childCount;
				thisNode.setProperty("parent", parent);
				childCount++;
				visit(n);
				return;
			    }
			    thisNode.setProperty("parent", parentNode);
			    parentNode.add(thisNode);
			    root.remove(childCount);
			    thisNode.add(buildHeader((GNode)thisNode.getProperty("n")));
			    childCount++;
			    return;
			    }

			public void visitClassBody(GNode n) {
			    if (childCount > root.size()) {
				return;
			    }
			    childCount--;
			    GNode node = (GNode)root.getNode(childCount);
			    node.add(buildHeader((GNode)node.getProperty("n")));
			    childCount++;
			    return;
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

        private GNode findParentNode(GNode startNode, String name) {
	//DOES A DEPTH-FIRST SEARCH THROUGH THE TREE
	//RETURNS THE GNODE IF IT FINDS THE PARENT, RETURNS NULL IF IT DOESN'T
	    if (startNode.getName().equals(name)) {
		return startNode;
	    }
	    else if (!startNode.hasProperty("type")) {
		return null;
	    }
	    else if (startNode.size() == 0) {
		return null;
	    }
	    else { //DEPTH FIRST SEARCH THROUGH THE TREE TO FIND THE PARENT NODE
		for (int i=0;i<startNode.size();i++) {
		    GNode solution = findParentNode((GNode)startNode.getNode(i), name);
		    if (solution != null) {
			return solution;
		    }
		}
		return null;
	    }
	}


	public GNode getRoot() {
		return root;
	}

	private GNode getObjectVTable() {
		// Create the VTable here for Object Class
		GNode objectVTable = GNode.create("VTable");
		String arg[] = { "__Object" };
		objectVTable.add(createMethod(null, "__isa", null, "Class", "Object"));
		objectVTable.add(createMethod(null, "toString", arg, "String", "Object"));
		objectVTable.add(createMethod(null, "hashcode", arg, "int32_t", "Object"));
		objectVTable.add(createMethod(null, "getClass", arg, "Class", "Object"));
		objectVTable.add(createMethod(null, "equals", new String[] { "Object",
									     "Object" }, "bool", "Object"));

		return objectVTable;
	}

	private GNode getObjectDataLayout() {
		// Create the Data Layout here for Object Class
		GNode objectDataLayout = GNode.create("DataLayout");
		objectDataLayout.add(createDataFieldEntry(null, "__Object_VT*",
				"__vptr", null));
		objectDataLayout.add(createDataFieldEntry("static", "__Object_VT",
				"vtable", null));
		objectDataLayout.add(createConstructor("Object", null));
		String arg[] = { "__Object" };
		String modifier[] = { "static" };
		objectDataLayout.add(createMethod(modifier, "toString", arg, "String", "Object"));
		objectDataLayout
		    .add(createMethod(modifier, "hashcode", arg, "int32_t", "Object"));
		objectDataLayout.add(createMethod(modifier, "getClass", arg, "Class", "Object"));
		objectDataLayout.add(createMethod(modifier, "equals", new String[] {
			    "Object", "Object" }, "bool", "Object"));
		objectDataLayout.add(createMethod(modifier, "__class", null, "Class", "Object"));
		return objectDataLayout;
	}

	private GNode getStringDataLayout() {
		// Create the Data Layout for the String Class
		GNode stringDataLayout = GNode.create("DataLayout");
        	stringDataLayout.add(createDataFieldEntry(null,"__String_VT*", "__vptr",null));
	        stringDataLayout.add(createDataFieldEntry("static","__String_VT","vtable",null));
        	stringDataLayout.add(createConstructor("String",null));
        	String arg[] = {"__String"};
        	String modifier[] = {"static"};
        	stringDataLayout.add(createMethod(modifier,"toString", arg,"String", "String"));
        	stringDataLayout.add(createMethod(modifier,"hashcode", arg, "int32_t", "String"));
        	stringDataLayout.add(createMethod(modifier,"getClass", arg, "Class", "String"));
        	stringDataLayout.add(createMethod(modifier,"equals",
						  new String[]{"__String", "__Object"}, "bool", "String"));
        	stringDataLayout.add(createMethod(modifier,"__class", null, "Class", "String"));
        	stringDataLayout.add(createMethod(new String[]{"static"}, "length", new String[]{"__String"}, "int32_t", "String"));
        	stringDataLayout.add(createMethod(new String[]{"static"}, "charAt", new String[]{"__String", "int32_t"}, "int32_t", "String"));
        	return stringDataLayout;
	
	}

	private GNode getStringVTable() {
		GNode stringVTable = getObjectVTable();
	        stringVTable.add(createMethod(null, "length", new String[]{"__Object"}, "int32_t", "String"));
	        stringVTable.add(createMethod(null, "charAt", new String[]{"__Object"}, "int32_t", "String"));
		return stringVTable;
	}

	private GNode getClassDataLayout() {
		//Create the Data Layout for the java.lang.Class class
        	GNode classDataLayout = GNode.create("DataLayout");
	        classDataLayout.add(createDataFieldEntry(null,"__Class_VT*", "__vptr",null));
       		classDataLayout.add(createDataFieldEntry(null,"String", "name",null));
        	classDataLayout.add(createDataFieldEntry(null,"Class", "parent",null));
        	classDataLayout.add(createDataFieldEntry("static","__Class_VT","vtable",null));
        	classDataLayout.add(createConstructor("Class",new String[]{"name", "parent"}));
        	String arg[] = {"__Object"};
        	String modifier[] = {"static"};
        	classDataLayout.add(createMethod(modifier,"toString", arg,"String", "Class"));
        	classDataLayout.add(createMethod(modifier,"getName", null, "String", "Class"));
        	classDataLayout.add(createMethod(modifier,"getSuperclass", null, "Class", "Class"));
        	classDataLayout.add(createMethod(new String[]{"Class", "Object"},"isInstance", null, "bool", "Class"));
        	classDataLayout.add(createMethod(modifier,"__class", null, "Class", "Class"));
		return classDataLayout;

	}

	private GNode getClassVTable() {
		GNode classVTable = getObjectVTable();
        	classVTable.add(createMethod(null, "getName", new String[]{"__Class"}, "String", "Class"));
        	classVTable.add(createMethod(null, "getSuperclass", new String[]{"__Class"}, "Class", "Class"));
       		classVTable.add(createMethod(null, "isInstance", new String[]{"__Class", "__Object"}, "bool", "Class"));

		return classVTable;
	}

	private GNode createMethod(String modifiers[], String name, String[] args,
				   String returnType, String className) {
		// Create a GNode with method arguments and the returnType as children.
		// The returnType will always be the first child.
		GNode methodDeclaration = GNode.create("MethodDeclaration");
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
		methodDeclaration.add(className);

		if (args != null) {
			for (String arg : args) {
				parameters.add(arg);
			}
		}
		methodDeclaration.add(parameters);

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
	//Builds the header in the Inheritance tree
	public GNode buildHeader(GNode astNode) {
		GNode header = GNode.create("HeaderDeclaration");
		header.add(getNodeDataLayout(astNode));
		header.add(getNodeVTable(astNode));
		return header;
	}
	//Create the node's DataLayout node
	private GNode getNodeDataLayout(GNode astNode){
	        GNode dataLayout = getParentDataLayout((GNode)astNode.getProperty("parent"));
		String parent = astNode.getString(1);
		dataLayout.setProperty("parent", parent);
		String type = "__" + astNode.getString(1) + "_VT";
		dataLayout.set(0,createDataFieldEntry(null, type + "*", "__vptr", null));
		dataLayout.set(1,createDataFieldEntry("static", type, "__vtable", null));
		for(int i = 0;i<dataLayout.size();i++){
			if(dataLayout.get(i) != null && dataLayout.get(i) instanceof Node){
				Node child = dataLayout.getNode(i);
				if(child.hasName("ConstructorDeclaration")){ //removes the parent constructor
					dataLayout.remove(i);
				}
			}
		}
		for(int i = 0;i<astNode.size();i++){
			if(astNode.get(i) != null && astNode.get(i) instanceof Node){
				Node child = astNode.getNode(i);
				if(child.hasName("ClassBody")){		
					handleClassBody(dataLayout,(GNode)child,false);
				}
			}
		}
		return dataLayout;
	}
	//Creates the node's VTable node
	private GNode getNodeVTable(GNode astNode){		
	        GNode vTable = getParentVTable((GNode)astNode.getProperty("parent"));
		String parent = astNode.getString(1);
		vTable.setProperty("parent", parent);
		for(int i = 0;i<astNode.size();i++){
			if(astNode.get(i) != null && astNode.get(i) instanceof Node){
			        GNode child = (GNode)astNode.getNode(i);
				if(child.hasName("ClassBody")){		
					handleClassBody(vTable,child,true);
				}
			}
		}
		return vTable;
	}
	//Parses the ClassBody node into children of the class root in the InheritanceTree
	private GNode handleClassBody(GNode inheritNode, GNode astNode, boolean isVTable) {		
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if (child.hasName("FieldDeclaration") && !isVTable) {
					handleFieldDeclaration(inheritNode, (GNode) child);
				} else if (child.hasName("MethodDeclaration")) {
					handleMethodDeclaration(inheritNode,(GNode)child, isVTable);
					if (isVTable) { //METHOD OVERWRITING
					    for (int j=0;j<inheritNode.size()-1;j++) {
						String searchName = (String)inheritNode.getNode(j).get(2);
						String checkName = (String)inheritNode.getNode(inheritNode.size()-1).get(2);
						if (inheritNode.getNode(j).getNode(4).size() != 0) {
						    inheritNode.getNode(j).getNode(4).set(0, inheritNode.getProperty("parent"));
						}
						if (searchName.equals(checkName)) {
						    inheritNode.remove(j);
						    break;
						}
					    }
					}
				} else if (child.hasName("ConstructorDeclaration") && !isVTable) {
					handleConstructorDeclaration(inheritNode, (GNode)child);
				}
			}
		}
		return inheritNode;
	}
	//Parses a FieldDeclaration from JavaAST to a similar one in the InheritanceTree
	private GNode handleFieldDeclaration(GNode inheritNode, GNode astNode) {		
		String modifier = null, type = null, name = null, declarator = null;
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if (child.hasName("Type")) { //Gets the field type
					type = convertType(((GNode) child.get(0)).getString(0));
				}else if(child.hasName("Declarators")){
					GNode dec = (GNode) child.getNode(0);
					name = dec.getString(0);
					if(dec.getNode(2) != null)//verifies if there is an initial value to the variable
						declarator = dec.getNode(2).getString(0);
				}
			}
		}
		inheritNode.add(createDataFieldEntry(modifier, type, name, declarator));
		return inheritNode;
	}
	//Parses a MethodDeclaration from JavaAST to a similar one in the InheritanceTree	
	private GNode handleMethodDeclaration(GNode inheritNode, GNode astNode, boolean isVTable) {
		String[] parameters = null, modifiers = null;
		if(!isVTable){
			modifiers = new String[1];
			modifiers[0] = "static";
		}
		String name = null, returnType = null;
		if(astNode.getString(3) != null){
			name = astNode.getString(3);
		}
		String className = (String)inheritNode.getProperty("parent");
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if(child.hasName("Type")){
					returnType = convertType(((GNode) child.get(0)).getString(0));				
				}else if (child.hasName("FormalParameters")){					
					Node param = child;
					if(param.size() > 0)
						parameters = new String[param.size()];
					for(int j = 0;j<param.size();j++){
						if(param.get(j) != null && param.get(j) instanceof Node){
							Node paramChild = param.getNode(j);
							if(paramChild.hasName("FormalParameter")){
								parameters[j] = convertType(paramChild.getNode(1).getNode(0).getString(0));
							}
						}
					}
				}
			}
		}
		inheritNode.add((createMethod(modifiers, name, parameters, returnType, className)));
		return inheritNode;
	}
	
	//Parses a ConstructorDeclaration from JavaAST to a similar one in the InheritanceTree
	private GNode handleConstructorDeclaration(GNode inheritNode, GNode astNode){
		String name = null;
		String parameters[] = null;
		if(astNode.getString(2) != null){
			name = astNode.getString(2);
		}
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if (child.hasName("FormalParameters")){					
					Node param = child;
					if(param.size() > 0)
						parameters = new String[param.size()];
					for(int j = 0;j<param.size();j++){
						if(param.get(j) != null && param.get(j) instanceof Node){
							Node paramChild = param.getNode(j);
							if(paramChild.hasName("FormalParameter")){
								parameters[j] = convertType(paramChild.getNode(1).getNode(0).getString(0));
							}
						}
					}
				}
			}
		}
		inheritNode.add(createConstructor(name, parameters));
		return inheritNode;
	}
	//Converts the java type to the corresponding C++ Type
	private String convertType(String javaType){
		String cppType = javaType;
		if(javaType.equals("int"))
			cppType = "int32_t";
		return cppType;
	}
	//Returns the parent node Data Layout for inheritance. If parent == null, assumes it's java.lang.Object.  Added code to get parent node dataLayout.
	private GNode getParentDataLayout(GNode parent){
		GNode dataLayout = null;
		if (parent == null){
			dataLayout = getObjectDataLayout();
		}
		else {
		        dataLayout = (GNode)parent.getNode(0).getNode(0);
		}
		return dataLayout;
	}
	//Returns the parent node VTable for inheritance. If parent == null, assumes it's java.lang.Object. Added code to get parent node VTable.
	private GNode getParentVTable(GNode parent){
		GNode vTable = null;
		if (parent == null){
			vTable = getObjectVTable();
		}
		else {
		        vTable = (GNode)parent.getNode(0).getNode(1);
		}
		return vTable;
	}
	

}
