#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;

namespace subExamples {
	
	struct __HelloWorld;
	struct __HelloWorld_VT;

	typedef __rt::Ptr<__HelloWorld> HelloWorld;

	struct __HelloWorld {
		__HelloWorld_VT* __vptr;

		__HelloWorld();

		static int32_t hashCode(HelloWorld);
  	static bool equals(HelloWorld, Object);
  	static Class getClass(HelloWorld);
  	static String toString(HelloWorld);
    int32_t world;
  	static int32_t createWorld(HelloWorld);
  	static int32_t returnX(String);
  	static Class __class();

  	static __HelloWorld_VT __vtable;
	};

	struct __HelloWorld_VT {
      Class __isa;
      void (*__delete)(__HelloWorld*);
      int32_t (*hashCode)(HelloWorld);
      bool (*equals)(HelloWorld, Object);
      Class (*getClass)(HelloWorld);
      String (*toString)(HelloWorld);
      int32_t (*createWorld)(HelloWorld);
      int32_t (*returnX)(String);

      __HelloWorld_VT()
      : __isa(__HelloWorld::__class()),
        __delete(&__rt::__delete<__HelloWorld>),
        hashCode((int32_t(*)(HelloWorld)) &__Object::hashCode),
        equals((bool(*)(HelloWorld,Object)) &__Object::equals),
        getClass((Class(*)(HelloWorld)) &__Object::getClass),
        toString((String(*)(HelloWorld)) &__Object::toString), 
        createWorld((int32_t(*)(HelloWorld)) &__HelloWorld::createWorld),
        returnX(&__HelloWorld::returnX) {
      }
    };

}
namespace subExamples {
  struct __HelloUniverse;
  struct __HelloUniverse_VT;

  typedef __rt::Ptr<__HelloUniverse> HelloUniverse;

    struct __HelloUniverse {
    	__HelloUniverse_VT* __vptr;


    	__HelloUniverse();

    	static int32_t hashCode(HelloUniverse);
    	static bool equals(HelloUniverse, Object);
    	static Class getClass(HelloUniverse);
    	static String toString(HelloUniverse);
      int32_t world;
    	static int32_t createWorld(HelloUniverse);
    	static int32_t returnX(String);
    	static Class __class();

    	static __HelloUniverse_VT __vtable;

    };

    struct __HelloUniverse_VT {
      Class __isa;
      void (*__delete)(__HelloUniverse*);
      int32_t (*hashCode)(HelloUniverse);
      bool (*equals)(HelloUniverse, Object);
      Class (*getClass)(HelloUniverse);
      String (*toString)(HelloUniverse);
      int32_t (*createWorld)(HelloUniverse);
      int32_t (*returnX)(String);

      __HelloUniverse_VT()
      : __isa(__HelloUniverse::__class()),
        __delete(&__rt::__delete<__HelloUniverse>),
        hashCode((int32_t(*)(HelloUniverse)) &__Object::hashCode),
        equals((bool(*)(HelloUniverse, Object)) &__Object::equals),
        getClass((Class(*)(HelloUniverse)) &__Object::getClass),
        toString(&__HelloUniverse::toString), 
        createWorld((int32_t(*)(HelloUniverse)) &__HelloWorld::createWorld),
        returnX(&__HelloUniverse::returnX) {
      }
    };
}