#include "output.h"
#include <sstream>
import subExamples.*;

public class __TranslateMe {
  public static void main(String[] args) {
    HelloWorld hw = new HelloWorld();

    System.out.println(hw.toString());

    HelloUniverse hu = new HelloUniverse();

    System.out.println(hu.toString());
    System.out.println(hu.createWorld());
  }
}
package subExamples;

public class __HelloWorld {
  public int world;

  public __HelloWorld() {
    world = 0;
  }

  public int createWorld() {
    world = 1;
    return world;
  }

  public int returnX(String a) {
    int x = 5;

    return x;
  }
}
package subExamples;

public class __HelloUniverse extends HelloWorld {
  public __HelloUniverse() {
    super();
  }

  public int returnX(String b) {
    int x = 10;

    return x;
  }

  public String toString() {
    return "x";
  }
}
