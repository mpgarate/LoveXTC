#pragma once
#include <stdint.h>
#include <string>
#include "java_lang.h"

using namespace java::lang;
using namespace std;

typedef unsigned char byte;



	struct __A;
	struct __A_VT;

	typedef __A* A;

	struct __A {
		__A_VT* __vptr;

		__A();

		static int32_t hashCode(A);
  		static bool equals(A, Object);
  		static Class getClass(A);
  		static String toString(A);
  		static Class __class();

  	static __A_VT __vtable;
	};

	struct __A_VT {
      Class __isa;
      int32_t (*hashCode)(A);
      bool (*equals)(A, Object);
      Class (*getClass)(A);
      String (*toString)(A);

      __A_VT()
      : __isa(__A::__class()),
        hashCode((int32_t(*)(A)) &__Object::hashCode),
        equals((bool(*)(A,Object)) &__Object::equals),
        getClass((Class(*)(A)) &__Object::getClass),
        toString(&__A::toString){
      }
    };



	struct __B;
	struct __B_VT;

	typedef __B* B;

	struct __B {
		__B_VT* __vptr;

		__B();

		static int32_t hashCode(B);
  		static bool equals(B, Object);
  		static Class getClass(B);
  		static String toString(B);
  		static Class __class();

  	static __B_VT __vtable;
	};

	struct __B_VT {
      Class __isa;
      int32_t (*hashCode)(B);
      bool (*equals)(B, Object);
      Class (*getClass)(B);
      String (*toString)(B);

      __B_VT()
      : __isa(__B::__class()),
        hashCode((int32_t(*)(B)) &__Object::hashCode),
        equals((bool(*)(B,Object)) &__Object::equals),
        getClass((Class(*)(B)) &__Object::getClass),
        toString(&__B::toString){
      }
    };



	struct __C;
	struct __C_VT;

	typedef __C* C;

	struct __C {
		__C_VT* __vptr;

		__C();

		static int32_t hashCode(C);
  		static bool equals(C, Object);
  		static Class getClass(C);
  		static String toString(C);
  		static Class __class();

  	static __C_VT __vtable;
	};

	struct __C_VT {
      Class __isa;
      int32_t (*hashCode)(C);
      bool (*equals)(C, Object);
      Class (*getClass)(C);
      String (*toString)(C);

      __C_VT()
      : __isa(__C::__class()),
        hashCode((int32_t(*)(C)) &__Object::hashCode),
        equals((bool(*)(C,Object)) &__Object::equals),
        getClass((Class(*)(C)) &__Object::getClass),
        toString(&__C::toString){
      }
    };



	struct __Overloaded;
	struct __Overloaded_VT;

	typedef __Overloaded* Overloaded;

	struct __Overloaded {
		__Overloaded_VT* __vptr;

		__Overloaded();

		static int32_t hashCode(Overloaded);
  		static bool equals(Overloaded, Object);
  		static Class getClass(Overloaded);
  		static String toString(Overloaded);
  		static void m(Overloaded);
  		static void m_byte(byte);
  		static void m_short(short);
  		static void m_int32_t(int32_t);
  		static void m_long(long);
  		static void m_Object(Object);
  		static void m_String(String);
  		static void m_A(A);
  		static void m_B(B);
  		static void m_A_A(A, A);
  		static void m_A_B(A, B);
  		static void m_B_A(B, A);
  		static void m_C_C(C, C);
  		static Class __class();

  	static __Overloaded_VT __vtable;
	};

	struct __Overloaded_VT {
      Class __isa;
      int32_t (*hashCode)(Overloaded);
      bool (*equals)(Overloaded, Object);
      Class (*getClass)(Overloaded);
      String (*toString)(Overloaded);
      void (*m)(Overloaded);
      void (*m_byte)(byte);
      void (*m_short)(short);
      void (*m_int32_t)(int32_t);
  	  void (*m_long)(long);
  	  void (*m_Object)(Object);
  	  void (*m_String)(String);
  	  void (*m_A)(A);
      void (*m_B)(B);
  	  void (*m_A_A)(A, A);
  	  void (*m_A_B)(A, B);
  	  void (*m_B_A)(B, A);
  	  void (*m_C_C)(C, C);


      __Overloaded_VT()
      : __isa(__Overloaded::__class()),
        hashCode((int32_t(*)(Overloaded)) &__Object::hashCode),
        equals((bool(*)(Overloaded,Object)) &__Object::equals),
        getClass((Class(*)(Overloaded)) &__Object::getClass),
        toString((String(*)(Overloaded)) &__Object::toString),
        m(&__Overloaded::m),
        m_byte(&__Overloaded::m_byte),
        m_short(&__Overloaded::m_short),
        m_int32_t(&__Overloaded::m_int32_t),
        m_long(&__Overloaded::m_long),
        m_Object(&__Overloaded::m_Object),
        m_String(&__Overloaded::m_String),
        m_A(&__Overloaded::m_A),
        m_B(&__Overloaded::m_B),
        m_A_A(&__Overloaded::m_A_A),
        m_A_B(&__Overloaded::m_A_B),
        m_B_A(&__Overloaded::m_B_A),
        m_C_C(&__Overloaded::m_C_C){
      }
    };
