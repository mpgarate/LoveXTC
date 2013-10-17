package xtc.oop;
import org.junit.*;
import static org.junit.Assert.*;

import xtc.tree.GNode;
import xtc.tree.Node;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import xtc.parser.ParseException;

public class TestDriver{
  
  @Before public void setUp() {
  	// Nothing to do.
  }

  @After public void tearDown() {
    // Nothing to do.
  }

  @Test public void PrintLnTest() throws IOException, ParseException {
    String name = "Println.java";
    String result = testFile(name);
    assertTrue(result.equals(""));
  }

  private String testFile(String name) throws IOException, ParseException {
    String path = "test/" + name;
    Translator t = new Translator();
    Node n = t.parse(path);
    t.printCPP(n);

    return readFile(n.getLocation().toString());
  }

  private String readFile(String filename) {
     String contents = null;
     filename = filename.replace(".java",".cpp");
     File file = new File("test/output/" + filename);
     try {
         FileReader reader = new FileReader(file);
         char[] chars = new char[(int) file.length()];
         reader.read(chars);
         contents = new String(chars);
         reader.close();
     } catch (IOException e) {
        System.out.println("Could not locate output file.");
     }
     return contents;
  }
}
