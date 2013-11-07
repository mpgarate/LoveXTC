/* How Inheritance Creates the tree?
1. Create nodes for each class and place them as children of Object
2. Any class with "extends" gets a copy added to a stack.
3. For everything in the stack, we look in the tree for it's parent and add the node as a child.
4. We create the headers (dataLayouts and vTables) for each class node.

Methods provided in this class:
buildTree: places a GNode in the inheritance tree
findParentNode: finds a node with a given name in a tree with the given root.
buildTreeHeaders: Runs through the entire tree and builds a header for each node.
buildHeader: Builds a header for a given node.
getRoot: Returns the root of the entire tree.
searchForNode: finds a node with the given name in the tree.  Ideally this will be combined with findParentNode into one method in the near future.
parseNodeToInheritance: Returns the header information for a given node.

getParentDataLayout: returns a copy of the parent's dataLayout for a given node.
getParentVTable: returns a copy of the parent's vTable for a given node.
copyNode: returns a copy of a node and all it's children and children's children, etc.  Note this won't copy any properties you've placed on the nodes.
*/

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

    public GNode stackNode; //A stack of nodes that have the extends keyword and thus must be moved in the tree to their correct place.
	int childCount = 3; // When building the tree, keeps track of which child we're creating.

	String packageName = null;

   	DataLayoutCreator dataLayout = new DataLayoutCreator();
   	VTableCreator vTable = new VTableCreator();

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
		headerNode.add(dataLayout.getObjectDataLayout());
		headerNode.add(vTable.getObjectVTable());

		root.add(stringNode);
		GNode stringHeader = GNode.create("HeaderDeclaration");
		stringHeader.add("null");
		stringHeader.add("String");
		stringNode.add(stringHeader);
		stringHeader.add(dataLayout.getStringDataLayout());
		stringHeader.add(vTable.getStringVTable());

		GNode classHeader = GNode.create("HeaderDeclaration");
		classHeader.add("null");
		classHeader.add("Class");
		classHeader.add(dataLayout.getClassDataLayout());
		classHeader.add(vTable.getClassVTable());
		classNode.add(classHeader);
		root.add(classNode);

		root.setProperty("type", "CompilationUnit");
		stringNode.setProperty("type", "CompilationUnit");
		classNode.setProperty("type", "CompilationUnit");
		
		stackNode = GNode.create("FirstStackNode");

		//Places each node in the tree as a child of Object.
		for (int i = 0; i < nodeList.size(); i++) {
			buildTree(nodeList.get(i));
		}

		//BUILD THE INHERITANCE TREE-------
		//Goes through a stack of all nodes with the extends keyword and moves them in three to be a child of their correct parent.
		for (GNode s=stackNode;s.getName()!="FirstStackNode";s=(GNode)s.getNode(0)) {
		    GNode parent = findParentNode(root, (String)s.getProperty("parentString"));
		    if (parent == null) {
				System.out.println("NO PARENT FOUND IN THE TREE!");
				continue;
		    }

		    GNode thisNode = (GNode)root.getNode((Integer)s.getProperty("numberInRoot"));
		    thisNode.setProperty("parent", parent);
		    parent.add(thisNode);
		    root.remove((Integer)s.getProperty("numberInRoot"));
		}

		//BUILD THE HEADERS FOR THE TREE
		for (int i=1;i<root.size();i++) {
		    buildTreeHeaders((GNode)root.getNode(i));
		}
				    
	}

	public void buildTree(GNode node) {
		    new Visitor() {
		    	
		    public void visitPackageDeclaration(GNode n){
		    	packageName = n.getNode(1).getString(0);
		    }
		    
		    public void visitClassDeclaration(GNode n) {
				String classname = n.getString(1);
				GNode classNode = GNode.create(classname);
				classNode.setProperty("type", "CompilationUnit"); //all class nodes should have type compilationunit so we can easily identify them.
				root.add(classNode);
				childCount++;
				classNode.setProperty("javaAST", n);
				
				visit(n);
				return;
			}
			
			public void visitExtension(GNode n) {
			    //Places the node in the tree and also a copy of the node in a stack on top of stackNode to be looked at later.
			    GNode thisNode = (GNode)root.getNode(childCount-1);
			    String parentString = n.getNode(0).getNode(0).getString(0);
			    GNode copiedNode = copyNode(thisNode);
			    copiedNode.setProperty("numberInRoot", childCount-1);
			    copiedNode.setProperty("parentString", parentString);
			    copiedNode.add(stackNode);
			    stackNode = copiedNode;
			    return;
			}

			public void visitClassBody(GNode n) {
			    if (childCount > root.size()) {
					return;
			    }

			    childCount--;
			    GNode node = (GNode)root.getNode(childCount);
			    node.setProperty("parent", null);
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
	}

	private GNode findParentNode(GNode startNode, String name) {
		// Finds a node of a given name in the tree whose root is startNode.
		if (startNode.getName().equals(name)) {
			return startNode;
		} else if (!startNode.hasProperty("type")) {
			return null;
		} else if (startNode.size() == 0) {
			return null;
		} else { // DEPTH FIRST SEARCH THROUGH THE TREE TO FIND THE PARENT NODE
			for (int i = 0; i < startNode.size(); i++) {
			     GNode solution = findParentNode((GNode) startNode.getNode(i),
						name);
				if (solution != null) {
					return solution;
				}
			}
			return null;
		}
	}

	private void buildTreeHeaders(GNode startNode) {
	    //Builds the header for each node in the inheritance tree.
	    if (startNode.size()<=0 || !startNode.getNode(0).getName().equals("HeaderDeclaration")) {
			startNode.add(0, buildHeader((GNode)startNode.getProperty("javaAST"), (GNode)startNode.getProperty("parent"))); //this builds the header
	    }

	    if (startNode.size()<=1) {
			return;
	    }
	    
	    //If the node has any children, it builds the header for it's children.
	    for (int i=1;i<startNode.size();i++) {
			buildTreeHeaders((GNode)startNode.getNode(i));
	    }
	    return;
	}


	// Builds the header in the Inheritance tree
    public GNode buildHeader(GNode astNode, GNode parentNode) {
		GNode header = GNode.create("HeaderDeclaration");
		header.add(packageName);
		header.add(astNode.getString(1));
		header.add(dataLayout.getNodeDataLayout(astNode, getParentDataLayout(parentNode)));
		header.add(vTable.getNodeVTable(astNode, getParentVTable(parentNode)));
		return header;
	}

	public GNode getRoot() {
		return root;
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

	public LinkedList<GNode> parseNodeToInheritance(GNode n){
		final LinkedList<GNode> returnList = new LinkedList<GNode>();
		new Visitor(){
			public void visitPackageDeclaration(GNode n){
				packageName = n.getNode(1).getString(0);
			}
			
			public void visitClassDeclaration(GNode n){
				GNode targetNode = (GNode) n;
				GNode classNode = searchForNode(root, targetNode.getString(1));
				GNode returnNode = GNode.create(targetNode.getString(1));
				returnNode.add((GNode)classNode.getNode(0));

				returnList.add(returnNode);
			}
			
			public void visit(GNode n) {
				for (Object o : n) {
					if (o instanceof Node)
						dispatch((Node) o);
				}
			}
		}.dispatch(n);
		
		return returnList;
	}

	// Returns the parent node Data Layout for inheritance. If parent == null,
	// assumes it's java.lang.Object. Added code to get parent node dataLayout.
	private GNode getParentDataLayout(GNode parent) {
		GNode dataLayoutNode = null;
		if (parent == null) {
			GNode temp = dataLayout.getObjectDataLayout();
			dataLayoutNode = copyNode(temp);
		} else {
			GNode olddataLayout = (GNode) parent.getNode(0).getNode(2);
			dataLayoutNode = copyNode(olddataLayout);
		}
		return dataLayoutNode;
	}

	// Returns the parent node VTable for inheritance. If parent == null,
	// assumes it's java.lang.Object. Added code to get parent node VTable.
	private GNode getParentVTable(GNode parent) {
	        GNode vTableNode = null;
		if (parent == null) { 
			GNode temp = vTable.getObjectVTable();
			vTableNode = copyNode(temp);
		} else {
			GNode oldvTable = (GNode) parent.getNode(0).getNode(3);
			vTableNode = copyNode(oldvTable);
		}
		return vTableNode;
	}

    private GNode copyNode(GNode oldNode) {
	GNode newNode = GNode.create(oldNode.getName());
	if (oldNode.size() > 0) {
	    for (int i=0;i<oldNode.size();i++) {
		if (oldNode.get(i) instanceof String) {
		    newNode.add(oldNode.get(i));
		}
		else {		    
		    newNode.add(copyNode((GNode)oldNode.get(i)));
		}
	    }
	}
	return newNode;
    }

}
