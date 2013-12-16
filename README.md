LoveXTC
==============
Translate a subset of Java to a limited subset of C++ using XTC, linux, and caffeinated beverages.

At the beginning of every XTC session, run ```source setup.sh``` in the xtc root.

Place our code in a directory like ```~/xtc/oop/```. Within this directory:

 * Compile all code with ```make```

 * Run our test suite with ```make test```

 * Create javadocs with ```make loveDocs``` and open ```docs/index.html``` in your browser. 

 * Convert a java file to C++ with:
```sh
make love FILE=examples/TranslateMe.java
```
This is equivalent to running:
```sh
java xtc.oop.Translator -translate examples/TranslateMe.java
```
This will write out C++ to  ```output/output.cc``` and ```output/output.h```

 * Compile the output code with ```make compile``` and run it with ```make run```

--------
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
