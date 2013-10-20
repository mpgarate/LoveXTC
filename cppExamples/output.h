#pragma once
#include <stdint.h>
#include <string>

namespace subExamples {
	
	struct HelloWorld;
	struct HelloWorld_VT;

	struct HelloUniverse;
	struct HelloUniverse_VT;

	typedef HelloWorld* HelloWorld;
	typedef HelloUniverse* HelloUniverse;

	struct Helloworld {
		HelloWorld_VT* __vptr;
		static int32_t world;

		Helloworld();

		static int32_t hashCode(Helloworld);
      	static bool equals(Helloworld, Object);
      	static Class getClass(Helloworld);
      	static String toString(Helloworld);
      	static int32_t createWorld();
      	static int32_t returnX(String);
      	static Class __class();

      	static HelloWorld_VT __vtable;
	};

	struct HelloWorld_VT {
      Class __isa;
      int32_t (*hashCode)(Helloworld);
      bool (*equals)(Helloworld, Object);
      Class (*getClass)(Helloworld);
      String (*toString)(Helloworld);
      int32_t (*createWorld)();
      int32_t (*returnX)(String);

      __Object_VT()
      : __isa(Helloworld::__class()),
        hashCode((int32_t(*)(Helloworld)) &__Object::hashCode),
        equals((bool(*)(Helloworld)) &__Object::equals),
        getClass((Class(*)(Helloworld)) &__Object::getClass),
        toString((String(*)(Helloworld)) &__Object::toString), 
        createWorld(&Helloworld::createWorld),
        returnX(&Helloworld::returnX) {
      }
    };

    struct HelloUniverse {
    	HelloUniverse_VT* __vptr;
    	static int32_t world;

    	HelloUniverse();

    	static int32_t hashCode(HelloUniverse);
      	static bool equals(HelloUniverse, Object);
      	static Class getClass(HelloUniverse);
      	static String toString(HelloUniverse);
      	static int32_t createWorld();
      	static int32_t returnX(String);
      	static Class __class();

      	static HelloUniverse_VT __vtable;

    };

    struct HelloUniverse_VT {
      Class __isa;
      int32_t (*hashCode)(HelloUniverse);
      bool (*equals)(HelloUniverse, Object);
      Class (*getClass)(HelloUniverse);
      String (*toString)(HelloUniverse);
      int32_t (*createWorld)();
      int32_t (*returnX)(String);

      __Object_VT()
      : __isa(HelloUniverse::__class()),
        hashCode((int32_t(*)(HelloUniverse)) &Helloworld::hashCode),
        equals((bool(*)(Helloworld)) &Helloworld::equals),
        getClass((Class(*)(Helloworld)) &Helloworld::getClass),
        toString(&HelloUniverse::toString), 
        createWorld(&Helloworld::createWorld),
        returnX(&HelloUniverse::returnX) {
      }
    };
}