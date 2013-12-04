LoveXTC
==============
Translate a subset of Java to a limited subset of C++ using XTC, linux, and caffeinated beverages.

At the beginning of every XTC session, run in the XTC root:
```sh
source setup.sh
```

Compile all code with ```make```

Convert a java file to C++ with:
```sh
make love FILE=examples/TranslateMe.java
```
This is equivalent to running:
```sh
java xtc.oop.Translator -translate examples/TranslateMe.java
```
This will write out C++ to  ```output/output.cc``` and ```output/output.h```

Compile the output code with ```make compile``` and run it with ```make run```

Run our test suite with ```make test```

If you want to run our example java programs on their own, run the following in the examples folder:
```sh
javac -cp . TranslateMe.java
java -cp . TranslateMe
```

To compile & run converted code manually, without make:
```
cd output
g++ java_lang.cc output.cc
./a.out
```

Print a java file's C++ AST with:
```sh
java xtc.oop.Translator -printCPPTree examples/TranslateMe.java
```

TODO:

* Write class to scan a directory of java files, translate to c++, compile, and compare results.

WHAT ARE WE DOING RIGHT NOW?
* Adam:
* Ahmad: Symbol table
* Bruno:
* Carl:
* Michael: Wies Tests

FILE STATUS
* Dependency.java : working for HelloWorld.java and HelloUniverse.java inputs!
* Inheritance.java : some tree structure present, requires further testing
* ASTModifier : manipulates tree for C++ output
* CCCP.java: prints much of output.cc correctly
* Translator.java: needs to be cleaned up
* SymTab.java : should accept the linked list instead of a single node 
