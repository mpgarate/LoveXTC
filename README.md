LoveXTC
==============
Translate a subset of Java to a limited subset of C++ using XTC, linux, and caffeinated beverages.

At the beginning of every XTC session, run in the XTC root:
```sh
source setup.sh
```

Convert a java file to C++ with:
```sh
java xtc.oop.Translator -translate examples/XEqualsFive.java
```
This will write a C++ file to ```output.cpp```

Print a java file's C++ AST with:
```sh
java xtc.oop.Translator -printCPPTree examples/XEqualsFive.java
```

Run the test suite and compile all code with ```make```

TODO:

* Work on vtables for each class = inheritance
* Configure logging framework to output to file
* use JavaDoc conventions when commenting
* Write class to scan a directory of java files, translate to c++, compile, and compare results.
* enhance CPPAST and CCCP Printer

WHAT ARE WE DOING RIGHT NOW?
* Adam:
* Ahmad: Dependency finder
* Bruno:
* Carl:
* Michael: Dependency.java

FILE STATUS
* Dependency.java : working for HelloWorld.java and HelloUniverse.java inputs!
* Inheritance.java : some tree structure present, requires further testing
* ASTBuilder.java: Handle dependencies and create many many different nodes making NO ASSUMPTIONS.
* CCCP.java: waiting for the AST to be finished.
* Translator.java: continue building out -translate option
