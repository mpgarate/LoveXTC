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
		GNode classBody = GNode.create("ClassBody");
		GNode classModifier = GNode.create("ClassModifier");
		GNode classExtension = GNode.create("ClassExtension");

		classDec.addNode(classBody);
		classDec.addNode(classModifier);
		classDec.addNode(classExtension);

		GNode constructorDeclaration = GNode.create("ConstructorDeclaration");
		GNode functionDeclaration = GNode.create("FunctionDeclaration");
		GNode fieldDeclaration = GNode.create("FieldDeclaration");
		GNode block = GNode.create("Block");

		classBody.addNode(constructorDeclaration);
		classBody.addNode(functionDeclaration);
		classBody.addNode(fieldDeclaration);
		classBody.addNode(block);

		return classDec;
	}

	public static void main(String args[]){
		ASTBuilder cpp = new ASTBuilder();
		System.out.println(cpp.root);
	}
}