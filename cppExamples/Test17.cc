#include "output.h"
#include <sstream>
#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;

__A::__A() : __vptr(&__vtable){}

A __A::init(A __this, int32_t x){
__Object::init(__this);
__this->self = __this;
return __this;
}

//A __A::self(A __this) {
//return __this->self;

//}
__A_VT __A::__vtable;

Class __A::__class() {
static Class k = new __Class(__rt::literal("A"), (Class) __rt::null());
return k;
}
A __A::self_impl(A __this) {
  return __this->self;
}

int main(void){
A a = __A::init(new __A(), 5);
__rt::checkNotNull(a);
A tmp = a->__vptr->self(a);
__rt::checkNotNull(tmp);
std::cout << tmp->__vptr->toString(tmp) << std::endl;

return 0;
}
__Test017_VT __Test017::__vtable;

Class __Test017::__class() {
static Class k = new __Class(__rt::literal("Test017"), (Class) __rt::null());
return k;
}
