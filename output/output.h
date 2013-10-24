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
__HelloWorld_VT* __vptr;
static __HelloWorld_VT __vtable;
static String toString();
static int32_t hashcode();
static Class getClass();
static bool equals();
static Class __class();
int32_t world;
HelloWorld();
static int32_t createWorld();
static int32_t returnX();
};

struct __HelloWorld_VT {
Class __isa;
String (*toString)(HelloWorld);
int32_t (*hashcode)(HelloWorld);
Class (*getClass)(HelloWorld);
bool (*equals)(HelloWorld);
int32_t (*createWorld)(HelloWorld);
int32_t (*returnX)(HelloWorld);
__HelloWorld_VT()
: __isa(__HelloWorld:: __class()),
toString((String(*)HelloWorld)) &__Object::toString),
hashcode((int32_t(*)HelloWorld)) &__Object::hashcode),
getClass((Class(*)HelloWorld)) &__Object::getClass),
equals((bool(*)HelloWorld)) &__Object::equals),
createWorld((int32_t(*)HelloWorld)) &__HelloWorld::createWorld),
returnX((int32_t(*)HelloWorld)) &__HelloWorld::returnX){
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
static String toString();
static int32_t hashcode();
static Class getClass();
static bool equals();
static Class __class();
HelloUniverse();
static int32_t returnX();
static String toString();
};

struct __HelloUniverse_VT {
Class __isa;
String (*toString)(HelloUniverse);
int32_t (*hashcode)(HelloUniverse);
Class (*getClass)(HelloUniverse);
bool (*equals)(HelloUniverse);
int32_t (*returnX)(HelloUniverse);
String (*toString)(HelloUniverse);
__HelloUniverse_VT()
: __isa(__HelloUniverse:: __class()),
hashcode((int32_t(*)HelloUniverse)) &__Object::hashcode),
getClass((Class(*)HelloUniverse)) &__Object::getClass),
equals((bool(*)HelloUniverse)) &__Object::equals),
returnX((int32_t(*)HelloUniverse)) &__HelloUniverse::returnX),
toString((String(*)HelloUniverse)) &__HelloUniverse::toString){
}
};

}

