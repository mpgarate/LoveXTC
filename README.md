OOP-Translator
==============
Translate a subset of Java to a limited subset of C++ using XTC, linux, and caffeinated beverages.

At the beginning of every XTC session, run in the XTC root:
```sh
source setup.sh
```

Convert a java file to C++ with:
```sh
java xtc.oop.Translator -printCPP examples/XEqualsFive.java
```
This will write a C++ file to ```output.cpp```

Print a java file's C++ AST with:
```sh
java xtc.oop.Translator -printCPPTree examples/XEqualsFive.java
```

TODO:
* Write some JUnit tests
  * Write class to scan a directory of java files, translate to c++, compile, and compare results.
* Use a logging framework
* use JavaDoc conventions when commenting
* enhance CPPAST and CCCP Printer
* work on dependencies and then vtables for each class = inheritance

WHAT ARE WE DOING RIGHT NOW?
* Adam:
* Ahmad: Dependency finder
* Bruno:
* Carl:
* Michael: Set up testing framework and write first few tests; will update CCCP to print AST

FILE STATUS
* Dependency.java : empty
* Inheritance.java : empty
* ASTBuilder.java: need to create many many different nodes making NO ASSUMPTIONS.
* CCCP.java: waiting for the AST to be finished.
* Translator.java: add the "translate" command line option and the appropriate code.
