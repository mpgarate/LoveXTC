#include "output.h"
#include <sstream>
subExamples __TranslateMe::main(String args) {
HelloWorld hw = new __HelloWorld();
Systemhw;
HelloUniverse hu = new __HelloUniverse();
Systemhu;
Systemhu;
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
__HelloUniverse::__HelloUniverse() : __vptr(&__vtable), {}

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
