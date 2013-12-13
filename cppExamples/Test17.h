#pragma once

#include "java_lang.h"

using namespace java::lang;

struct __Test17;
struct __Test17_VT;
typedef __rt::Ptr<__Test17> Test17;

struct __Test17 {
  __Test17_VT* __vptr;
  
  // The constructor.
  __Test17();

  // The methods implemented by Test17.
  static void main(__rt::Ptr<__rt::Array<String> > args);
  static Test17 init(Test17);

  // The function returning the class object representing
  // Test17.
  static Class __class();

  // The vtable for Test17.
  static __Test17_VT __vtable;
};

// The vtable layout for Test17.
struct __Test17_VT {
  Class __isa;
  void (*__delete)(__Test17*);
  int32_t (*hashCode)(Test17);
  bool (*equals)(Test17, Object);
  Class (*getClass)(Test17);
  String (*toString)(Test17);

  __Test17_VT()
  : __isa(__Test17::__class()),
    __delete(&__rt::__delete<__Test17>),
    hashCode((int32_t(*)(Test17))&__Object::hashCode),
    equals((bool(*)(Test17, Object))&__Object::equals),
    getClass((Class(*)(Test17))&__Object::getClass),
    toString((String(*)(Test17))&__Object::toString) {
  }
};
