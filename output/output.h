#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;
struct __A;
struct __A_VT;
typedef __A* A;
struct __A {
__A_VT* __vptr;
static __A_VT __vtable;
__A();
static String toString(A);
static int32_t hashCode(A);
static Class getClass(A);
static bool equals(A, Object);
static Class __class();
};

struct __A_VT {
Class __isa;
String (*toString)(A);
int32_t (*hashCode)(A);
Class (*getClass)(A);
bool (*equals)(A);
__A_VT()
: __isa(__A::__class()),
toString((String(*)(A)) &__A::toString),
hashCode((int32_t(*)(A)) &__Object::hashCode),
getClass((Class(*)(A)) &__Object::getClass),
equals((bool(*)(A)) &__Object::equals){}
};

struct __Test2;
struct __Test2_VT;
typedef __Test2* Test2;
struct __Test2 {
__Test2_VT* __vptr;
static __Test2_VT __vtable;
__Test2();
static String toString(Test2);
static int32_t hashCode(Test2);
static Class getClass(Test2);
static bool equals(Test2, Object);
static Class __class();
};

struct __Test2_VT {
Class __isa;
String (*toString)(Test2);
int32_t (*hashCode)(Test2);
Class (*getClass)(Test2);
bool (*equals)(Test2);
__Test2_VT()
: __isa(__Test2::__class()),
toString((String(*)(Test2)) &__Object::toString),
hashCode((int32_t(*)(Test2)) &__Object::hashCode),
getClass((Class(*)(Test2)) &__Object::getClass),
equals((bool(*)(Test2)) &__Object::equals){}
};

