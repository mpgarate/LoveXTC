#include "Derived.h"
#include <sstream>


#include <iostream>
#include "java_lang.h"

using namespace java::lang;
using namespace def;
using namespace std;



namespace def {

	__Base::__Base() : __vptr(&__vtable) {}

	Base __Base::init(Base __this){
		__Object::init(__this);
		return __this;
	}

	void __Base::m_Object(Object o) {
				cout << "Base.m(Object)" <<endl;;
    }
    void __Base::m_String(String s) {
				cout << "Base.m(String)" <<endl;;
    }
    void __Base::m_class(Class c) {
				cout << "Base.m(Class)" <<endl;;
    }
    __Base_VT __Base::__vtable;


	Class __Base::__class() {
      static Class k =
        new __Class(__rt::literal("def.Base"), (Class) __rt::null());
      return k;
  }

}
namespace def {

	__Derived::__Derived() : __vptr(&__vtable) {}

	Derived __Derived::init(Derived __this){
		__Base::init(__this);
		return __this;
	}

	void __Derived::m_Object(Object o) {
				cout << "Derived.m(Object)" <<endl;;
    }
    void __Derived::m_String(String s) {
				cout << "Derived.m(String)" <<endl;;
    }
    


    __Derived_VT __Derived::__vtable;


	Class __Derived::__class() {
      static Class k =
        new __Class(__rt::literal("def.Derived"), (Class) __rt::null());
      return k;
  }

}

int main(void){


	Base b = __Derived::init(new __Derived());
	__rt::checkNotNull(b);
	
	b->__vptr->m_Object(new __Object());
	b->m_String(new __String("Hello"));
	b->__vptr->m_class(b->__vptr->getClass(b));


	return 0;
}