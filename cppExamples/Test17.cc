#include <iostream>
#include "A.h"
#include "Test17.h"

using namespace java::lang;

__Test17::__Test17() : __vptr(&__vtable) { }

Test17 __Test17::init(Test17 __this) {
  // make implicit call to constructor of Test17's superclass explicit
  __Object::init(__this);
  return __this;
}

__Test17_VT __Test17::__vtable;


Class __Test17::__class() {
  static Class k =
    new __Class(__rt::literal("Test17"), java::lang::__Object::__class());
  return k;
}

void __Test17::main(__rt::Ptr<__rt::Array<String> > args) {
  A a = __A::init(new __A());
  __rt::checkNotNull(a);
  A tmp = a->__vptr->self(a);
  __rt::checkNotNull(tmp);
  std::cout << tmp->__vptr->toString(tmp) << std::endl;
}
