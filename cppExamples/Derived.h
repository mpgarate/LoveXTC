#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;



namespace def {

	struct __Base;
	struct __Base_VT;

	typedef __rt::Ptr<__Base> Base;

	struct __Base {
		__Base_VT* __vptr;

		__Base();

		static int32_t hashCode(Base);
  		static bool equals(Base, Object);
  		static Class getClass(Base);
  		static String toString(Base);
  		static Class __class();
      static void m_Object(Object);
      static void m_String(String);
      static void m_class(Class);
      static Base init(Base);

  	static __Base_VT __vtable;
	};

	struct __Base_VT {
      Class __isa;
      void (*__delete)(__Base*);
      int32_t (*hashCode)(Base);
      bool (*equals)(Base, Object);
      Class (*getClass)(Base);
      String (*toString)(Base);
      void (*m_Object)(Object);

      void (*m_class)(Class);

      __Base_VT()
      : __isa(__Base::__class()),
        __delete(&__rt::__delete<__Base>),
        hashCode((int32_t(*)(Base)) &__Object::hashCode),
        equals((bool(*)(Base,Object)) &__Object::equals),
        getClass((Class(*)(Base)) &__Object::getClass),
        toString((String(*)(Base))&__Object::toString),
        m_Object(&__Base::m_Object),
        m_class(&__Base::m_class){
      }
    };
}
namespace def {

	struct __Derived;
	struct __Derived_VT;

	typedef __rt::Ptr<__Derived> Derived;

	struct __Derived {
		__Derived_VT* __vptr;

		__Derived();

		static int32_t hashCode(Derived);
  		static bool equals(Derived, Object);
  		static Class getClass(Derived);
  		static String toString(Derived);
      static Class __class();
  		static void m_Object(Object);
      static void m_String(String);
      static void m_class(Class);
      static Derived init(Derived);

  	static __Derived_VT __vtable;
	};

	struct __Derived_VT {
      Class __isa;
      void (*__delete)(__Derived*);
      int32_t (*hashCode)(Derived);
      bool (*equals)(Derived, Object);
      Class (*getClass)(Derived);
      String (*toString)(Derived);
  	  void (*m_Object)(Object);
  	  void (*m_class)(Class);


      __Derived_VT()
      : __isa(__Derived::__class()),
        __delete(&__rt::__delete<__Derived>),
        hashCode((int32_t(*)(Derived)) &__Object::hashCode),
        equals((bool(*)(Derived,Object)) &__Object::equals),
        getClass((Class(*)(Derived)) &__Object::getClass),
        toString((String(*)(Derived)) &__Object::toString),
        m_Object(&__Derived::m_Object),
        m_class(&__Base::m_class){
      }
    };
}