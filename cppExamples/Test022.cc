#include "output.h"
#include <sstream>
#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;

void __Test022::main(__rt::Ptr<__rt::Array<String> > args){
{

for(int32_t i = 0;i<args->length;i++)
{
cout << args->__data[i] <<endl;
}

}
}

__Test022::__Test022() : __vptr(&__vtable){}

Test022 __Test022::init(Test022 __this){
__Object::init(__this);
return __this;
}

__Test022_VT __Test022::__vtable;

Class __Test022::__class() {
static Class k = new __Class(__rt::literal("Test022"), (Class) __rt::null());
return k;
}

int main(int argc, char* argv[]) {
  __rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc - 1);

  for (int32_t i = 1; i < argc; i++) {
    (*args)[i] = __rt::literal(argv[i]);
  }
  
  __Test022::main(args);
  
  return 0;
}
