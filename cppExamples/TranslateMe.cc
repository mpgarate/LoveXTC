#include <iostream>
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;

int main(void){

	const HelloWorld hw = new HelloWorld();
	cout << hw->__vptr->toString()->data <<endl;

	const HelloUniverse hu = new HelloUniverse();
	cout << hu->__vptr->toString()->data <<endl;
	cout << hu->__vptr->createWorld()->data <<endl;

	return 0;
}
