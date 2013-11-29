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
static int32_t hashCode(TranslateMe);
static bool equals(TranslateMe,Object);
static Class getClass(TranslateMe);
static String toString(TranslateMe);
static Class __class();
};

struct __TranslateMe_VT {
Class __isa;
int32_t (*hashCode)(TranslateMe);
bool (*equals)(TranslateMe,Object);
Class (*getClass)(TranslateMe);
String (*toString)(TranslateMe);
__TranslateMe_VT()
: __isa(__TranslateMe::__class()),
hashCode((int32_t(*)(TranslateMe)) &__Object::hashCode),
equals((bool(*)(TranslateMe,Object)) &__Object::equals),
getClass((Class(*)(TranslateMe)) &__Object::getClass),
toString((String(*)(TranslateMe)) &__Object::toString){}
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
static int32_t hashCode(HelloWorld);
static bool equals(HelloWorld,Object);
static Class getClass(HelloWorld);
static String toString(HelloWorld);
static Class __class();
int32_t world;
static int32_t createWorld(HelloWorld);
static int32_t returnX(String);
};

struct __HelloWorld_VT {
Class __isa;
int32_t (*hashCode)(HelloWorld);
bool (*equals)(HelloWorld,Object);
Class (*getClass)(HelloWorld);
String (*toString)(HelloWorld);
int32_t (*createWorld)(HelloWorld);
int32_t (*returnX)(String);
__HelloWorld_VT()
: __isa(__HelloWorld::__class()),
hashCode((int32_t(*)(HelloWorld)) &__Object::hashCode),
equals((bool(*)(HelloWorld,Object)) &__Object::equals),
getClass((Class(*)(HelloWorld)) &__Object::getClass),
toString((String(*)(HelloWorld)) &__Object::toString),
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
static int32_t hashCode(HelloUniverse);
static bool equals(HelloUniverse,Object);
static Class getClass(HelloUniverse);
static String toString(HelloUniverse);
static Class __class();
int32_t world;
static int32_t createWorld(HelloUniverse);
static int32_t returnX(String);
};

struct __HelloUniverse_VT {
Class __isa;
int32_t (*hashCode)(HelloUniverse);
bool (*equals)(HelloUniverse,Object);
Class (*getClass)(HelloUniverse);
String (*toString)(HelloUniverse);
int32_t (*createWorld)(HelloUniverse);
int32_t (*returnX)(String);
__HelloUniverse_VT()
: __isa(__HelloUniverse::__class()),
hashCode((int32_t(*)(HelloUniverse)) &__Object::hashCode),
equals((bool(*)(HelloUniverse,Object)) &__Object::equals),
getClass((Class(*)(HelloUniverse)) &__Object::getClass),
toString((String(*)(HelloUniverse)) &__HelloUniverse::toString),
createWorld((int32_t(*)(HelloUniverse)) &__HelloWorld::createWorld),
returnX((int32_t(*)(String)) &__HelloUniverse::returnX){}
};

}

