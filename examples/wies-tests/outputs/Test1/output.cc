#include "output.h"
#include <sstream>
#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;

//__HelloWorld::__HelloWorld() : __vptr(&__vtable), world(0) {}
__A::__A() : __vptr(&__vtable) {}

String __A::toString(A __this) {
return new __String("A");

}
__A_VT __A::__vtable;

Class __A::__class() {
static Class k =
new __Class(__rt::literal("null.A"), (Class) __rt::null());
return k;
}
int main(void){
A a = new __A();
cout << a->__vptr->toString(a)->data <<endl;

return 0;
}
__Test1_VT __Test1::__vtable;

Class __Test1::__class() {
static Class k =
new __Class(__rt::literal("null.A"), (Class) __rt::null());
return k;
}