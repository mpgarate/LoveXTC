package xtc.oop;

import java.lang.*;
import java.io.IOException;
import xtc.parser.ParseException;
import xtc.lang.JavaFiveParser;
import xtc.parser.Result;
import java.io.File;
import java.io.Reader;

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
import xtc.util.Tool;
/* End imports based on src/xtc/lang/CPrinter.java */

import java.util.LinkedList;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Dependency extends Tool {

  private final static Logger LOGGER = Logger.getLogger(Dependency.class .getName()); 

  LinkedList<GNode> depList = new LinkedList<GNode>();

	/** The printer for this C printer. */

	public Dependency(LinkedList<GNode> ll){
    depList = ll;
	}

  public String getName(){
    return "Dependency";
  }

  public String getCopy(){
    return "LoveXTC 2013";
  }

  /* fills the addresslist with the addresses of the dependencies */ 
  public void makeAddressList() {
    LOGGER.setLevel(Level.INFO); 
    for (int i = 0; i < depList.size(); i++) {
      if (depList.get(i) != null) {
        LOGGER.info("Looping through dependencies");
        new Visitor() {

          /* visitExtension(GNode) will be called in the Inheritance Tree */

          public void visitPackageDeclaration(GNode n) {
            /* WISH LIST: Check if we have already handled the package */
            String currentDir = System.getProperty("user.dir");
            String packageName;
            String path = getRelativePath(n);
            packageName = path.substring(1).replace("/",".");
            LOGGER.info("Package name: " + packageName);
            LOGGER.info("Package path: " + currentDir + path);

            processDirectory(currentDir + path, packageName);
            

            /* These files then have to each pass through Dependency.java
            before adding themselves to the list. */

            /* Pass a path to processPath() */

          }

          public void visitImportDeclaration(GNode n) {
            LOGGER.setLevel(Level.INFO); 
            LOGGER.info("Visiting import declaration");

            /* There is no '*' character */
            if (n.getString(2) == null){
            LOGGER.info("Importing a file");
              String path = getNodeLoc(n) + getRelativePath(n);
              File file = new File(path + ".java");
              LOGGER.info("got file " + file.getAbsoluteFile());
              processFile(file);
            }
            else {
              LOGGER.info("Importing an entire folder");
              String path = getRelativePath(n);

              processDirectory(path.substring(1));
            }
          }

          public void visit(Node n) {
            for (Object o : n) if (o instanceof Node) dispatch((Node) o);
          }

        }.dispatch(depList.get(i));
      }
    }
  }
  
  /* return the depList */
  public LinkedList<GNode> makeNodeList() {
    return depList;
  }

  /* return a Java AST GNode */
  public GNode parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser =
      new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (GNode)parser.value(result);
  }

  private String getPackageName(GNode n){
    GNode qualId = (GNode)n.getNode(1);
    String name = "";
    for (int i = 0; i<qualId.size(); i++){
      if (i > 1){
        name += ".";
      }
      name += qualId.get(i).toString();
    }
    return name;
  }

  private String getRelativePath(GNode n){
    String path = "";
    Node qualId = n.getNode(1);
    for (int i = 0; i<qualId.size(); i++){
      LOGGER.info("Appending: " + "/" + qualId.get(i).toString());
      path += "/" + qualId.get(i).toString();
    }
    LOGGER.info("Got folder path: " + path);
    return path;
  }

  private String getNodeLoc(GNode n){
    String nodeLoc = n.getLocation().toString();
    nodeLoc = nodeLoc.substring(0, nodeLoc.lastIndexOf("/"));
    LOGGER.info("node loc is " + nodeLoc);
    return nodeLoc;
  }

  private void processDirectory(String path){
    processDirectory(path, "");
  }
  private void processDirectory(String path, String packageName){
    File folder = new File(path);
    File[] files = folder.listFiles();
    if (files == null) {LOGGER.warning("Found no files in directory path");}
    LOGGER.info("Scanning for java files in " + folder.toString());

    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile() && files[i].getName().endsWith(".java")) {
        try {
          Reader in = runtime.getReader(files[i]);
          GNode node = parse(in, files[i]);
          if (node.getNode(0) != null){
            if (packageName.length() > 0) {
              if (getPackageName((GNode)node.getNode(0)).equals(packageName)){
                LOGGER.info(files[i] + " is in the package.");
                processNode(node);
              }
            }
            else{
              processNode(node);
            }
          }
        }
        catch (IOException e){
          LOGGER.warning("IO Exception on " + files[i].getPath());
        }
        catch (ParseException e){
          LOGGER.warning("Parse Exception");          
        }
      } 
    }
  }

  private void processFile(File file){
    try {
          Reader in = runtime.getReader(file);
          GNode node = parse(in, file);
          processNode(node);
        }
        catch (IOException e){
          LOGGER.warning("IO Exception on " + file.getPath());
        }
        catch (ParseException e){
          LOGGER.warning("Parse Exception");          
        }
  }

  private void processNode(GNode n){
    if (!depList.contains(n)){
      depList.add(n);
      LOGGER.info("--- Added " + n.getLocation().toString() + " to the list.");
    }
  }
  

  /* Remove any dependencies that are not actually used */
  public void trimDependencies(){
    /*
      We can check if a class is actually used with
      visitCallExpression(Gnode n)
    */
  }
}
