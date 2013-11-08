#include "output.h"
#include <sstream>
#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;

String __A::toString(A __this) {
return new __String("A");

}
__A::__A() : __vptr(&__vtable){}
__A_VT __A::__vtable;

Class __A::__class() {
static Class k =
new __Class(__rt::literal("null.A"), (Class) __rt::null());
return k;
}
int main(void){
A a = new __A();
Object o = a;
cout << o->__vptr->toString(o)->data <<endl;

return 0;
}
__Test2::__Test2() : __vptr(&__vtable){}
__Test2_VT __Test2::__vtable;

Class __Test2::__class() {
static Class k =
new __Class(__rt::literal("null.A"), (Class) __rt::null());
return k;
}
