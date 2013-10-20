#include "output.h"
#include <sstream>

namespace subExamples {

	Helloworld::HelloWorld() : __vptr(&__vtable), world = 0 {}

	int32_t Helloworld::createWorld() {
		world = 1;
		return world;
	}

	int32_t Helloworld::returnX(String a) {
		int32_t x = 5;
		return x;
	}

	HelloUniverse::HelloUniverse() : __vptr(&__vtable) {}

	int32_t HelloUniverse::returnX(String b) {
		int32_t x = 10;
		return x;
	}

	String HelloUniverse::toString() {
		return "x";
	}




}