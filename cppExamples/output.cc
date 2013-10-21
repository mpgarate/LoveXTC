/* Print these automatically */
#include "output.h"
#include <sstream>

/* Print namespace by using the package name in the tree */
namespace subExamples {

	/************************************************/
	/********* Define Items for HelloWorld **********/
	/************************************************/
	__HelloWorld::__HelloWorld() : __vptr(&__vtable), world(0) {}

	int32_t __HelloWorld::createWorld(HelloWorld __this) {
		__this->world = 1;
		return __this->world;
	}

	int32_t __HelloWorld::returnX(String a) {
		int32_t x = 5;
		return x;
	}

	/* Print this statically, filling in only the class name as necessary */
	Class __HelloWorld::__class() {
      static Class k =
        new __Class(__rt::literal("subExamples.HelloWorld"), (Class) __rt::null());
      return k;
  }

	__HelloWorld_VT __HelloWorld::__vtable;

	/************************************************/
	/******** Define Items for HelloUniverse ********/
	/************************************************/

	__HelloUniverse::__HelloUniverse() : __vptr(&__vtable) {}

	int32_t __HelloUniverse::returnX(String b) {
		int32_t x = 10;
		return x;
	}

	String __HelloUniverse::toString(HelloUniverse hu) {
				return new __String("x");
    }
    __HelloUniverse_VT __HelloUniverse::__vtable;


	Class __HelloUniverse::__class() {
      static Class k =
        new __Class(__rt::literal("subExamples.HelloUniverse"), (Class) __rt::null());
      return k;
  }


}