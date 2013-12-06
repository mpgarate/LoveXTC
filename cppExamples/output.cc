/* Print these automatically */
#include "output.h"
#include <sstream>


#include <iostream>
#include "java_lang.h"

using namespace java::lang;
using namespace subExamples;
using namespace std;

	/************************************************/
	/********* Define Items for HelloWorld **********/
	/************************************************/
	/* Print namespace by using the package name in the tree */
namespace subExamples {
		
	__HelloWorld::__HelloWorld() : __vptr(&__vtable) {}

	HelloWorld __HelloWorld::init(HelloWorld __this){
		__Object::init(__this);
		__this->world = 0;
		return __this;
	}

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
}

	/************************************************/
	/******** Define Items for HelloUniverse ********/
	/************************************************/

namespace subExamples {

	__HelloUniverse::__HelloUniverse() : __vptr(&__vtable) {}

	HelloUniverse __HelloUniverse::init(HelloUniverse __this){
		__HelloWorld::init(__this);
		return __this;
	}

	int32_t __HelloUniverse::returnX(String b) {
		int32_t x = 10;
		return x;
	}

	String __HelloUniverse::toString(HelloUniverse __this) {
				return new __String("x");
    }
    __HelloUniverse_VT __HelloUniverse::__vtable;


	Class __HelloUniverse::__class() {
      static Class k =
        new __Class(__rt::literal("subExamples.HelloUniverse"), (Class) __rt::null());
      return k;
  }

}

int main(void){

	HelloWorld hw = __HelloUniverse::init(new __HelloUniverse());
	__rt::checkNotNull(hw);
	cout << hw->__vptr->toString(hw) <<endl;
	cout << hw->world <<endl;

	HelloUniverse hu = __HelloUniverse::init(new __HelloUniverse());
	__rt::checkNotNull(hu);
	cout << hu->__vptr->toString(hu) <<endl;

	Object o = (Object)hu;
	cout << o->__vptr->toString(hu) <<endl;
	cout << hu->__vptr->createWorld(hu) <<endl;

	return 0;
}
