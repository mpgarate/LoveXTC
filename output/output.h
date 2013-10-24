#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;
namespace subExamples {
struct __HelloWorld;
struct __HelloWorld_VT;
typedef __HelloWorld* HelloWorld;
struct __HelloWorld {
__HelloUniverse_VT* __vptr;
static __HelloUniverse_VT __vtable;
HelloUniverse();
static int32_t hashCode();
static Class getClass();
static bool equals();
static Class __class();
int32_t world;
static int32_t createWorld();
static int32_t returnX();
static String toString();
};

struct __HelloWorld_VT {
Class __isa;
int32_t (*hashCode)(HelloWorld);
Class (*getClass)(HelloWorld);
bool (*equals)(HelloWorld);
int32_t (*createWorld)(HelloWorld);
int32_t (*returnX)(HelloWorld);
String (*toString)(HelloWorld);
__HelloWorld_VT()
: __isa(__HelloWorld::__class()),
hashCode((int32_t(*)(HelloWorld)) &__Object::hashCode),
getClass((Class(*)(HelloWorld)) &__Object::getClass),
equals((bool(*)(HelloWorld)) &__Object::equals),
createWorld((int32_t(*)(HelloWorld)) &__HelloWorld::createWorld),
returnX((int32_t(*)(HelloWorld)) &__HelloUniverse::returnX),
toString((String(*)(HelloWorld)) &__HelloUniverse::toString){
}
};

}

namespace subExamples {
struct __HelloUniverse;
struct __HelloUniverse_VT;
typedef __HelloUniverse* HelloUniverse;
struct __HelloUniverse {
__HelloUniverse_VT* __vptr;
static __HelloUniverse_VT __vtable;
HelloUniverse();
static int32_t hashCode();
static Class getClass();
static bool equals();
static Class __class();
int32_t world;
static int32_t createWorld();
static int32_t returnX();
static String toString();
};

struct __HelloUniverse_VT {
Class __isa;
int32_t (*hashCode)(HelloUniverse);
Class (*getClass)(HelloUniverse);
bool (*equals)(HelloUniverse);
int32_t (*createWorld)(HelloUniverse);
int32_t (*returnX)(HelloUniverse);
String (*toString)(HelloUniverse);
__HelloUniverse_VT()
: __isa(__HelloUniverse::__class()),
hashCode((int32_t(*)(HelloUniverse)) &__Object::hashCode),
getClass((Class(*)(HelloUniverse)) &__Object::getClass),
equals((bool(*)(HelloUniverse)) &__Object::equals),
createWorld((int32_t(*)(HelloUniverse)) &__HelloWorld::createWorld),
returnX((int32_t(*)(HelloUniverse)) &__HelloUniverse::returnX),
toString((String(*)(HelloUniverse)) &__HelloUniverse::toString){
}
};

}

