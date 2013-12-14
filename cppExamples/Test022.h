#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;
typedef unsigned char byte;
struct __Test022;
struct __Test022_VT;
typedef __rt::Ptr<__Test022> Test022;
struct __Test022 {
__Test022_VT* __vptr;
static __Test022_VT __vtable;
__Test022();

static void main(__rt::Ptr<__rt::Array<String> > args);

static Test022 init(Test022);
static int32_t hashCode(Test022);
static bool equals(Test022,Object);
static Class getClass(Test022);
static String toString(Test022);
static Class __class();
};

struct __Test022_VT {
Class __isa;
void (*__delete)(__Test022*);
int (*hashCode)(Test022);
bool (*equals)(Test022,Object);
Class (*getClass)(Test022);
String (*toString)(Test022);
__Test022_VT()
: __isa(__Test022::__class()),
__delete(&__rt::__delete<__Test022>),
hashCode((int32_t(*)(Test022)) &__Object::hashCode),
equals((bool(*)(Test022,Object)) &__Object::equals),
getClass((Class(*)(Test022)) &__Object::getClass),
toString((String(*)(Test022)) &__Object::toString){}
};

