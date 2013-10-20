#include "java_lang.h"
#include "output.h"

void main(){

	const HelloWorld hw = new HelloWorld();
	std::cout << hw->__vptr->toString()->data <<stdendl;

	const HelloUniverse hu = new HelloUniverse();
	std::cout << hu->__vptr->toString()->data <<stdendl;
	std::cout << hu->__vptr->createWorld()->data <<stdendl;

	return 0;
}
