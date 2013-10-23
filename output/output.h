#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;
namespace subExamples {
struct __TranslateMe;
struct __TranslateMe_VT;
typedef __TranslateMe* TranslateMe;
struct __TranslateMe {
__TranslateMe_VT* __vptr;
static __TranslateMe_VT __vtable;
static String toString();
static int32_t hashcode();
static Class getClass();
static bool equals();
static Class __class();
static main();
};

struct __TranslateMe_VT {
Class __isa;
String (*toString)(TranslateMe);
int32_t (*hashcode)(TranslateMe);
Class (*getClass)(TranslateMe);
bool (*equals)(TranslateMe);
null (*main)(TranslateMe);
Class __isa();
String toString();
int32_t hashcode();
Class getClass();
bool equals();
main();
};

}

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
Class __isa();
String toString();
int32_t hashcode();
Class getClass();
bool equals();
int32_t createWorld();
int32_t returnX();
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
Class __isa();
int32_t hashcode();
Class getClass();
bool equals();
int32_t returnX();
String toString();
};

}

