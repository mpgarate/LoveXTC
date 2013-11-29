#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;
typedef unsigned char byte;
namespace subExamples {
struct __TranslateMe;
struct __TranslateMe_VT;
typedef __TranslateMe* TranslateMe;
struct __TranslateMe {
__TranslateMe_VT* __vptr;
static __TranslateMe_VT __vtable;
__TranslateMe();
static String toString(TranslateMe);
static int32_t hashCode(TranslateMe);
static Class getClass(TranslateMe);
static bool equals(TranslateMe,Object);
static Class __class();
};

struct __TranslateMe_VT {
Class __isa;
String (*toString)(TranslateMe);
int32_t (*hashCode)(TranslateMe);
Class (*getClass)(TranslateMe);
bool (*equals)(TranslateMe,Object);
__TranslateMe_VT()
: __isa(__TranslateMe::__class()),
toString((String(*)(TranslateMe)) &__Object::toString),
hashCode((int32_t(*)(TranslateMe)) &__Object::hashCode),
getClass((Class(*)(TranslateMe)) &__Object::getClass),
equals((bool(*)(TranslateMe,Object)) &__Object::equals){}
};

}

namespace subExamples {
struct __HelloWorld;
struct __HelloWorld_VT;
typedef __HelloWorld* HelloWorld;
struct __HelloWorld {
__HelloWorld_VT* __vptr;
static __HelloWorld_VT __vtable;
__HelloWorld();
static String toString(HelloWorld);
static int32_t hashCode(HelloWorld);
static Class getClass(HelloWorld);
static bool equals(HelloWorld,Object);
static Class __class();
int32_t world;
static int32_t createWorld(HelloWorld);
static int32_t returnX(String);
};

struct __HelloWorld_VT {
Class __isa;
String (*toString)(HelloWorld);
int32_t (*hashCode)(HelloWorld);
Class (*getClass)(HelloWorld);
bool (*equals)(HelloWorld,Object);
int32_t (*createWorld)(HelloWorld);
int32_t (*returnX)(String);
__HelloWorld_VT()
: __isa(__HelloWorld::__class()),
toString((String(*)(HelloWorld)) &__Object::toString),
hashCode((int32_t(*)(HelloWorld)) &__Object::hashCode),
getClass((Class(*)(HelloWorld)) &__Object::getClass),
equals((bool(*)(HelloWorld,Object)) &__Object::equals),
createWorld((int32_t(*)(HelloWorld)) &__HelloWorld::createWorld),
returnX((int32_t(*)(String)) &__HelloWorld::returnX){}
};

}

namespace subExamples {
struct __HelloUniverse;
struct __HelloUniverse_VT;
typedef __HelloUniverse* HelloUniverse;
struct __HelloUniverse {
__HelloUniverse_VT* __vptr;
static __HelloUniverse_VT __vtable;
__HelloUniverse();
static String toString_HelloUniverse(HelloUniverse);
static int32_t hashCode(HelloUniverse);
static Class getClass(HelloUniverse);
static bool equals(HelloUniverse,Object);
static Class __class();
int32_t world;
static int32_t createWorld(HelloUniverse);
static int32_t returnX(String);
static String toString(HelloUniverse);
};

struct __HelloUniverse_VT {
Class __isa;
String (*toString_HelloUniverse)(HelloUniverse);
int32_t (*hashCode)(HelloUniverse);
Class (*getClass)(HelloUniverse);
bool (*equals)(HelloUniverse,Object);
int32_t (*createWorld)(HelloUniverse);
int32_t (*returnX)(String);
String (*toString)(HelloUniverse);
__HelloUniverse_VT()
: __isa(__HelloUniverse::__class()),
toString_HelloUniverse((String(*)(HelloUniverse)) &__Object::toString_HelloUniverse),
hashCode((int32_t(*)(HelloUniverse)) &__Object::hashCode),
getClass((Class(*)(HelloUniverse)) &__Object::getClass),
equals((bool(*)(HelloUniverse,Object)) &__Object::equals),
createWorld((int32_t(*)(HelloUniverse)) &__HelloWorld::createWorld),
returnX((int32_t(*)(String)) &__HelloUniverse::returnX),
toString((String(*)(HelloUniverse)) &__HelloUniverse::toString){}
};

}

