/*NodeHandler handles the creation of the vTable and dataLayout nodes from javaASTs.

NOTE: NodeHandler is meant to be accessed only from VTableCreator or DataLayoutCreator.

Methods provided in this class:
handleClassBody
handleFieldDeclaration
handleMethodDeclaration
handleConstructorDeclaration

createConstructor
createMethod
createDataFieldEntry
convertType
*/

package xtc.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.util.Tool;

import static org.junit.Assert.*;

public class NodeHandler {

	public NodeHandler() {
	}

    // Parses the ClassBody node into children of the class root in the
    // InheritanceTree
	protected GNode handleClassBody(GNode inheritNode, GNode astNode, boolean isVTable) {
		boolean foundConstructor = false;
		if (inheritNode.size() > 0) {
			for (int k=0;k<inheritNode.size();k++) {
				if (inheritNode.getNode(k).size() == 5 && inheritNode.getNode(k).getNode(4).size() != 0 && inheritNode.getNode(k).getString(3)=="Object") {
				//Renames the parameters in a method to be the classname type
					inheritNode.getNode(k).getNode(4).set(0,inheritNode.getProperty("parent"));
					inheritNode.getNode(k).set(2, changeObjectMethodNames((GNode)inheritNode.getNode(k).getNode(4), inheritNode.getNode(k).getString(2)));
				}
			}
		}

		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if (child.hasName("FieldDeclaration") && !isVTable) {
					handleFieldDeclaration(inheritNode, (GNode) child);
				} else if (child.hasName("MethodDeclaration")) {
						handleMethodDeclaration(inheritNode, (GNode) child,
							isVTable);
			    // METHOD OVERWRITING
						boolean isOverwritten = false;
						for (int j = 0; j < inheritNode.size() - 1; j++) {
							if (inheritNode.getNode(j).size() == 5) {
								String searchName = (String) inheritNode.getNode(j).get(2);
								String checkName = (String) inheritNode.getNode(inheritNode.size() - 1).get(2);
								if (searchName.equals(checkName)) {
									inheritNode.set(j, inheritNode.getNode(inheritNode.size()-1));
									isOverwritten = true;
									break;
								}
							}
						}
						if(isOverwritten){
							inheritNode.remove(inheritNode.size() -1);
						}
					}
					else if (child.hasName("ConstructorDeclaration") && !isVTable) {
					foundConstructor = true;
					handleConstructorDeclaration(inheritNode, (GNode) child);
				}
			}
		}

		if (!isVTable && !foundConstructor){
			/* Manually create a constructor */
			String className = inheritNode.getProperty("parent").toString();
			inheritNode.add(2,createConstructor(className, null));	
		}
		return inheritNode;
	}

	protected String changeObjectMethodNames(GNode parameters, String currentName) {
		String newName = "";
		for (int i=0;i<currentName.length();i++) {
			if (currentName.charAt(i) == '_') {
				break;
			}
			else {
				newName = newName+currentName.charAt(i);
			}
		}

		return newName;
	}

    // Parses a FieldDeclaration from JavaAST to a similar one in the
    // InheritanceTree
	protected GNode handleFieldDeclaration(GNode inheritNode, GNode astNode) {
		String modifier = null, type = null, name = null, declarator = null;
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
		if (child.hasName("Type")) { // Gets the field type
			type = convertType(((GNode) child.get(0)).getString(0));
		} else if (child.hasName("Declarators")) {
			GNode dec = (GNode) child.getNode(0);
			name = dec.getString(0);
		    if (dec.getNode(2) != null)// verifies if there is an
			// initial value to the variable
		    declarator = dec.getNode(2).getString(0);
		  }
		}
	}
	inheritNode.add(createDataFieldEntry(modifier, type, name, declarator));
	return inheritNode;
	}

	    // Parses a MethodDeclaration from JavaAST to a similar one in the
	    // InheritanceTree
	protected void handleMethodDeclaration(GNode inheritNode, GNode astNode,
		boolean isVTable) {
		String[] parameters = null, modifiers = null;
		String classname = null;
		if (!isVTable) {
			modifiers = new String[1];
			modifiers[0] = "static";
		}
		String name = null, returnType = null;
		if (astNode.getString(3) != null) {
			name = astNode.getString(3);
			if (name.equals("main")) return;
		}
		classname = (String) inheritNode.getProperty("parent");
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if (child.hasName("Type")) {
					returnType = convertType(((GNode) child.get(0))
						.getString(0));
				} else if (child.hasName("FormalParameters")) {
					Node param = child;
					if (param.size() > 0)
						parameters = new String[param.size()];
					for (int j = 0; j < param.size(); j++) {
						if (param.get(j) != null
							&& param.get(j) instanceof Node) {
							Node paramChild = param.getNode(j);
						if (paramChild.hasName("FormalParameter")) {
							parameters[j] = convertType(paramChild
								.getNode(1).getNode(0).getString(0));
						}
					}
				}
			}
		}
	}
	inheritNode.add((createMethod(modifiers, name, parameters, returnType,
		classname, isVTable)));
	}

	    // Parses a ConstructorDeclaration from JavaAST to a similar one in the
	    // InheritanceTree
	protected GNode handleConstructorDeclaration(GNode inheritNode, GNode astNode) {
		String name = null;
		String parameters[] = null;
		if (astNode.getString(2) != null) {
			name = astNode.getString(2);
		}
		for (int i = 0; i < astNode.size(); i++) {
			if (astNode.get(i) != null && astNode.get(i) instanceof Node) {
				Node child = astNode.getNode(i);
				if (child.hasName("FormalParameters")) {
					Node param = child;
					if (param.size() > 0)
						parameters = new String[param.size()];
					for (int j = 0; j < param.size(); j++) {
						if (param.get(j) != null
							&& param.get(j) instanceof Node) {
							Node paramChild = param.getNode(j);
						if (paramChild.hasName("FormalParameter")) {
							parameters[j] = convertType(paramChild
								.getNode(1).getNode(0).getString(0));
						}
					}
				}
			}
		}
	}
	inheritNode.add(2, createConstructor(name, parameters));
	return inheritNode;
	}


	protected GNode createConstructor(String classname, String parameters[]) {
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

	    //Begin Helper Methods

	protected GNode createMethod(String modifiers[], String name, String[] args, String returnType, String className, boolean isVTable) {
		// Create a GNode with method arguments and the returnType as children.
		// The returnType will always be the first child.
		GNode methodDeclaration = null;
		if(isVTable)
			methodDeclaration = GNode.create("VTableMethodDeclaration");
		else {
			methodDeclaration = GNode.create("DataLayoutMethodDeclaration");
		}

		GNode modifierDeclaration = GNode.create("Modifiers");
		GNode parameters = GNode.create("Parameters");
		
		if (modifiers != null) {
			for (String mod : modifiers) {
				modifierDeclaration.add(mod);
			}
		}
		methodDeclaration.add(modifierDeclaration);
		methodDeclaration.add(returnType);

		if (args != null) {
			for (String arg : args) {
				parameters.add(arg);
				name = name+"_"+arg;
			}
		}

		methodDeclaration.add(name);
		if(className != null)
			methodDeclaration.add(className);

		methodDeclaration.add(parameters);
		
		return methodDeclaration;
	}

	protected GNode createDataFieldEntry(String modifier, String type, String name, String declarator) {
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

	    // Converts the java type to the corresponding C++ Type
	protected String convertType(String javaType) {
		String cppType = javaType;
		if (javaType.equals("int"))
			cppType = "int32_t";
		return cppType;
	}

	    //End Helper Methods
}