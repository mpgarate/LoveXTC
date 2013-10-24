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
