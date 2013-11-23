package xtc.oop;

import java.lang.*;

/* Imports based on src/xtc/lang/CPrinter.java */
import java.util.Iterator;

import xtc.tree.LineMarker;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Pragma;
import xtc.tree.Printer;
import xtc.tree.SourceIdentity;
import xtc.tree.Token;
import xtc.tree.Visitor;
/* End imports based on src/xtc/lang/CPrinter.java */

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.LinkedList;

public class OverloadingASTModifier extends Visitor {

	public String className;
	private LinkedList<GNode> methodList = new LinkedList<GNode>();
	private LinkedList<String> parameterList = new LinkedList<String>();
	private Boolean ready = false;

	public OverloadingASTModifier() {

	}

	public String getName() {
		return className;
	}

	public void visitClassBody(GNode n) {
		visit(n);

		sortList();
		for (int i=0;i<methodList.size();i++) {
			executeOverloading((GNode)methodList.get(i));
		}
	}

	//Removes elements from the list that aren't overloaded.
	public void sortList() {
		boolean match = false;

		for (int k=methodList.size()-1;k>-1;k--) {
			GNode endNode = methodList.get(k);
			for (int i=0;i<methodList.size()-1;i++) {
				if (endNode.get(3).equals(methodList.get(i).get(3))) {
					match = true;
					break;
				}
			}
			if (!match) {
				methodList.remove(k);
			}
			match = false;
		}
	}

	public void visitMethodDeclaration(GNode n) {
		if (n.size()>3 && n.get(3) instanceof String) {
			methodList.add(n); //creates a list with all the method nodes.
		}

		visit(n);
	}

	public void visitQualifiedIdentifier(GNode n) {
		if (!ready) {return;}
		parameterList.add(n.getString(0));
	}

	public void visitPrimitiveType(GNode n) {
		if (!ready) {return;}
		parameterList.add(n.getString(0));
	}

	public void visit(Node n) {
    	for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  	}


	protected void executeOverloading(GNode overload) {
		
      	String newNodeString = overload.getString(3);
      	if (overload.getNode(4).size() > 0) {
      		ready=true;
        	visit(overload.getNode(4));
        	ready=false;
        	for (int i=0;i<parameterList.size();i++) {
       			newNodeString = newNodeString+"_"+parameterList.get(i);
       		}
       		parameterList = new LinkedList<String>();
      	}
      	overload.set(3, newNodeString);
    }
}