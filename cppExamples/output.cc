#include "output.h"
#include <sstream>

namespace subExamples {

	__HelloWorld::__HelloWorld() : __vptr(&__vtable) {}

	int32_t __HelloWorld::createWorld() {
		world = 1;
		return world;
	}

	int32_t __HelloWorld::returnX(String a) {
		int32_t x = 5;
		return x;
	}

	__HelloUniverse::__HelloUniverse() : __vptr(&__vtable) {}

	int32_t __HelloUniverse::returnX(String b) {
		int32_t x = 10;
		return x;
	}

	String __HelloUniverse::toString() {
		return "x";
	}




}