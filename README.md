LoveXTC
==============
Translate a subset of Java to a limited subset of C++ using XTC, linux, and caffeinated beverages.

### Setup ###

At the beginning of every XTC session, run ```source setup.sh``` in the xtc root.

Place our code in a directory like ```~/xtc/oop/```.

### Basic Usage ###

Compile all code with ```make```

Run our test suite with ```make test```. This will run through our own Java examples and the tests provided by Professor Wies. Each file is translated and run to compare its output to that of the original Java file.

Create javadocs with ```make loveDocs``` and open ```docs/index.html``` in your browser. 

Convert a java file to C++ with:
```sh
make love FILE=examples/TranslateMe.java
```
This will write out C++ to  ```output/output.cc``` and ```output/output.h```

Compile the output code with ```make compile``` and run it with ```make run```


### Manual Override & Debugging ###

This is the full form for translating a Java file (without our ```make love``` helper)
```sh
java xtc.oop.Translator -translate examples/TranslateMe.java
```

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
