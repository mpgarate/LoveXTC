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
__A();
static __A_VT __vtable;
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
equals((bool(*)(A)) &__Object::equals){
}
};

struct __Test1;
struct __Test1_VT;
typedef __Test1* Test1;
struct __Test1 {
__Test1_VT* __vptr;
static __Test1_VT __vtable;
static String toString(Test1);
static int32_t hashCode(Test1);
static Class getClass(Test1);
static bool equals(Test1, Object);
static Class __class();
};

struct __Test1_VT {
Class __isa;
String (*toString)(Test1);
int32_t (*hashCode)(Test1);
Class (*getClass)(Test1);
bool (*equals)(Test1);
__Test1_VT()
: __isa(__Test1::__class()),
toString((String(*)(Test1)) &__Object::toString),
hashCode((int32_t(*)(Test1)) &__Object::hashCode),
getClass((Class(*)(Test1)) &__Object::getClass),
equals((bool(*)(Test1)) &__Object::equals){
}
};

