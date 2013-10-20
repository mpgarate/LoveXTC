#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace subExamples;
using namespace std;

int main(void){

	const HelloWorld hw = new __HelloWorld();
	cout << hw->__vptr->toString(hw)->data <<endl;

	const HelloUniverse hu = new __HelloUniverse();
	cout << hu->__vptr->toString(hu)->data <<endl;
	cout << hu->__vptr->createWorld()->data <<endl;

	return 0;
}
