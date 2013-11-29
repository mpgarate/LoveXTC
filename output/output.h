#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;
typedef unsigned char byte;
struct __A;
struct __A_VT;
typedef __A* A;
struct __A {
__A_VT* __vptr;
static __A_VT __vtable;
__A();
static int32_t hashCode(A);
static bool equals(A,Object);
static Class getClass(A);
static String toString(A);
static Class __class();
};

struct __A_VT {
Class __isa;
int32_t (*hashCode)(A);
bool (*equals)(A,Object);
Class (*getClass)(A);
String (*toString)(A);
__A_VT()
: __isa(__A::__class()),
hashCode((int32_t(*)(A)) &__Object::hashCode),
equals((bool(*)(A,Object)) &__Object::equals),
getClass((Class(*)(A)) &__Object::getClass),
toString((String(*)(A)) &__A::toString){}
};

struct __Test1;
struct __Test1_VT;
typedef __Test1* Test1;
struct __Test1 {
__Test1_VT* __vptr;
static __Test1_VT __vtable;
__Test1();
static int32_t hashCode(Test1);
static bool equals(Test1,Object);
static Class getClass(Test1);
static String toString(Test1);
static Class __class();
};

struct __Test1_VT {
Class __isa;
int32_t (*hashCode)(Test1);
bool (*equals)(Test1,Object);
Class (*getClass)(Test1);
String (*toString)(Test1);
__Test1_VT()
: __isa(__Test1::__class()),
hashCode((int32_t(*)(Test1)) &__Object::hashCode),
equals((bool(*)(Test1,Object)) &__Object::equals),
getClass((Class(*)(Test1)) &__Object::getClass),
toString((String(*)(Test1)) &__Object::toString){}
};

