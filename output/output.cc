#include "output.h"
#include <sstream>
#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;

using namespace subExamples;

int main(void){
HelloWorld hw = new __HelloWorld();
cout << hw->__vptr->toString(hw)->data <<endl;
HelloUniverse hu = new __HelloUniverse();
cout << hu->__vptr->toString(hu)->data <<endl;
cout << hu->__vptr->createWorld(hu) <<endl;

return 0;
}
namespace subExamples {
__HelloWorld::__HelloWorld() : __vptr(&__vtable), world(0) {}

int32_t __HelloWorld::createWorld(HelloWorld __this) {
__this->world = 1;
return __this->world;

}
int32_t __HelloWorld::returnX(String a) {
int32_t x = 5;
return x;

}
__HelloWorld_VT __HelloWorld::__vtable;

Class __HelloWorld::__class() {
static Class k =
new __Class(__rt::literal("subExamples.HelloWorld"), (Class) __rt::null());
return k;
}
}
namespace subExamples {
__HelloUniverse::__HelloUniverse() : __vptr(&__vtable){}

int32_t __HelloUniverse::returnX(String b) {
int32_t x = 10;
return x;

}
String __HelloUniverse::toString(HelloUniverse __this) {
return new __String("x");

}
__HelloUniverse_VT __HelloUniverse::__vtable;

Class __HelloUniverse::__class() {
static Class k =
new __Class(__rt::literal("subExamples.HelloUniverse"), (Class) __rt::null());
return k;
}
}
