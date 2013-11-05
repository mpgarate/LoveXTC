/* Purpose: builds the symbol table which allows us to keep track of which symbols 
(variables, methods, etc.) are currently in scope while traversing the AST, 
and associate useful information with each symbol (e.g. types of variables).
*/
package xtc.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;
import xtc.tree.Printer;

import xtc.util.SymbolTable;
import xtc.util.SymbolTable.Scope;

import xtc.lang.JavaFiveParser;
import xtc.lang.JavaPrinter;

import java.util.StringTokenizer;
import java.util.Iterator;


public class SymTab {
	SymbolTable table = new SymbolTable("SymRoot");

	public SymTab(GNode node){
		buildSymbols(node);
	}

	public void buildSymbols(GNode node){
		new Visitor() {

			public void visitMethodDeclaration(GNode n){
				String name = n.getString(3)+ "Scope";
				table.enter(table.freshName(name));
				table.mark(n);
            	visit(n);
            	table.exit();            
            }
            public void visitFormalParameter(GNode n){
            	String name = n.getString(3);
            	String type = n.getNode(1).getNode(0).getString(0);
            	table.current().addDefinition(name, type);
            	visit(n);
            }
            public void visitFieldDeclaration(GNode n) {
                                
                String name = n.getNode(2).getNode(0).getString(0);                                
                String type = n.getNode(1).getNode(0).getString(0);
                                
                table.current().addDefinition(name, type);
                visit(n);
            } 

			public void visit(GNode n) {
                for (Object o : n) if (o instanceof GNode) dispatch((GNode)o);
            }
		}.dispatch(node);
	}
}