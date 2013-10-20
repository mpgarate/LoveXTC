#pragma once
#include <stdint.h>
#include "java_lang.h"
#include <string>

using namespace java::lang;
using namespace std;

namespace subExamples {
	
	struct __HelloWorld;
	struct __HelloWorld_VT;

	struct __HelloUniverse;
	struct __HelloUniverse_VT;

	typedef __HelloWorld* HelloWorld;
	typedef __HelloUniverse* HelloUniverse;

	struct __Helloworld {
		__HelloWorld_VT* __vptr;
		static int32_t world;

		__Helloworld();

		static int32_t hashCode(HelloWorld);
      	static bool equals(HelloWorld, Object);
      	static Class getClass(HelloWorld);
      	static String toString(HelloWorld);
      	static int32_t createWorld();
      	static int32_t returnX(String);
      	static Class __class();

      	static __HelloWorld_VT __vtable;
	};

	struct __HelloWorld_VT {
      Class __isa;
      int32_t (*hashCode)(HelloWorld);
      bool (*equals)(HelloWorld, Object);
      Class (*getClass)(HelloWorld);
      String (*toString)(HelloWorld);
      int32_t (*createWorld)();
      int32_t (*returnX)(String);

      __HelloWorld_VT()
      : __isa(__HelloWorld::__class()),
        hashCode((int32_t(*)(HelloWorld)) &__Object::hashCode),
        equals((bool(*)(HelloWorld)) &__Object::equals),
        getClass((Class(*)(HelloWorld)) &__Object::getClass),
        toString((String(*)(HelloWorld)) &__Object::toString), 
        createWorld(&__HelloWorld::createWorld),
        returnX(&__HelloWorld::returnX) {
      }
    };

    struct __HelloUniverse {
    	__HelloUniverse_VT* __vptr;
    	static int32_t world;

    	__HelloUniverse();

    	static int32_t hashCode(HelloUniverse);
      	static bool equals(HelloUniverse, Object);
      	static Class getClass(HelloUniverse);
      	static String toString(HelloUniverse);
      	static int32_t createWorld();
      	static int32_t returnX(String);
      	static Class __class();

      	static __HelloUniverse_VT __vtable;

    };

    struct __HelloUniverse_VT {
      Class __isa;
      int32_t (*hashCode)(HelloUniverse);
      bool (*equals)(HelloUniverse, Object);
      Class (*getClass)(HelloUniverse);
      String (*toString)(HelloUniverse);
      int32_t (*createWorld)();
      int32_t (*returnX)(String);

      __HelloUniverse_VT()
      : __isa(__HelloUniverse::__class()),
        hashCode((int32_t(*)(HelloUniverse)) &__Helloworld::hashCode),
        equals((bool(*)(Helloworld)) &__Helloworld::equals),
        getClass((Class(*)(Helloworld)) &__Helloworld::getClass),
        toString(&__HelloUniverse::toString), 
        createWorld(&__Helloworld::createWorld),
        returnX(&__HelloUniverse::returnX) {
      }
    };
}