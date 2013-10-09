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
* Use a logging framework
