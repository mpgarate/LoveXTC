#include "Overloaded.h"
#include <sstream>


#include <iostream>
#include "java_lang.h"

using namespace java::lang;
using namespace def;
using namespace std;

namespace def {

	__A::__A() : __vptr(&__vtable) {}

	String __A::toString(A __this) {
				return new __String("A");
    }
    __A_VT __A::__vtable;


	Class __A::__class() {
      static Class k =
        new __Class(__rt::literal("def.A"), (Class) __rt::null());
      return k;
  }

}
namespace def {

	__B::__B() : __vptr(&__vtable) {}

	String __B::toString(B __this) {
				return new __String("B");
    }
    __B_VT __B::__vtable;


	Class __B::__class() {
      static Class k =
        new __Class(__rt::literal("def.B"), (Class) __rt::null());
      return k;
  }

}
namespace def {

	__C::__C() : __vptr(&__vtable) {}

	String __C::toString(C __this) {
				return new __String("C");
    }
    __C_VT __C::__vtable;


	Class __C::__class() {
      static Class k =
        new __Class(__rt::literal("def.C"), (Class) __rt::null());
      return k;
  }

}
namespace def {

	__Overloaded::__Overloaded() : __vptr(&__vtable) {}

	void __Overloaded::m(Overloaded __this) {
				cout << "m()        : ---" <<endl;;
    }
    void __Overloaded::m_byte(byte c) {
				cout << "m(byte)    : " << (int)c <<endl;;
    }
    void __Overloaded::m_short(short s) {
				cout << "m(short)   : " << s <<endl;;
    }
    void __Overloaded::m_int32_t(int32_t i) {
				cout << "m(int32_t) : " << i <<endl;;
    }
    void __Overloaded::m_long(long l) {
				cout << "m(long)    : " << l <<endl;;
    }
    void __Overloaded::m_Object(Object o) {
				cout << "m(Object)  : " << o->__vptr->toString(o)->data <<endl;;
    }
    void __Overloaded::m_String(String s) {
				cout << "m(String)  : " << s->__vptr->toString(s)->data <<endl;;
    }
    void __Overloaded::m_A(A a) {
				cout << "m(A)       : " << a->__vptr->toString(a)->data <<endl;;
    }
    void __Overloaded::m_B(B b) {
				cout << "m(B)       : " << b->__vptr->toString(b)->data <<endl;;
    }
    void __Overloaded::m_A_A(A a1, A a2) {
				cout << "m(A,A)     : " << a1->__vptr->toString(a1)->data << ", " << 
						a2->__vptr->toString(a2)->data <<endl;;
    }
    void __Overloaded::m_A_B(A a1, B b2) {
				cout << "m(A,B)     : " << a1->__vptr->toString(a1)->data << ", " << 
						b2->__vptr->toString(b2)->data <<endl;;
    }
    void __Overloaded::m_B_A(B b1, A a2) {
				cout << "m(B,A)     : " << b1->__vptr->toString(b1)->data << ", " << 
						a2->__vptr->toString(a2)->data <<endl;;
    }
    void __Overloaded::m_C_C(C c1, C c2) {
				cout << "m(C,C)     : " << c1->__vptr->toString(c1)->data << ", " << 
						c2->__vptr->toString(c2)->data <<endl;;
    }


    __Overloaded_VT __Overloaded::__vtable;


	Class __Overloaded::__class() {
      static Class k =
        new __Class(__rt::literal("def.Overloaded"), (Class) __rt::null());
      return k;
  }

}

int main(void){

	Overloaded o = new __Overloaded();
	byte n1 = 1, n2 = 2;
	short s = 5;
	long l1 = 123456789;
	A a = new __A();
	B b = new __B();
	C c = new __C();

	o->__vptr->m(o);
	o->__vptr->m_byte(n1);
	o->__vptr->m_int32_t(n1+n2);
	o->__vptr->m_short(s);
	o->__vptr->m_long(l1);
	o->__vptr->m_Object(new __Object());
	o->__vptr->m_String(new __String("String"));
	o->__vptr->m_A(a);
	o->__vptr->m_B(b);
	o->__vptr->m_B((B)c);
	o->__vptr->m_A_A(a,a);
	o->__vptr->m_A_B((A)b,b);
	o->__vptr->m_C_C(c,c);

	return 0;
}