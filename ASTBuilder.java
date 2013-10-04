package xtc.oop;

import xtc.tree.GNode;

public class ASTBuilder{
	public GNode root, packageDeclaration, includeDeclaration, classDeclaration;

	public ASTBuilder(){
		root = 								GNode.create("TranslationUnit");
		packageDeclaration = 	GNode.create("PackageDeclaration");
		includeDeclaration = 	GNode.create("IncludeDeclaration");
		classDeclaration = 		classNodes();

		root.addNode(packageDeclaration);
		root.addNode(includeDeclaration);
		root.addNode(classDeclaration);
	}

	public GNode classNodes(){
		GNode classDec = GNode.create("ClassDeclaration");
		// classDec.addNode(xyz);

		return classDec;
	}

	public static void main(String args[]){
		ASTBuilder cpp = new ASTBuilder();
		System.out.println(cpp.root);
	}
}