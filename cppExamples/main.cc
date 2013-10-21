/*
 * Object-Oriented Programming
 * Copyright (C) 2012 Robert Grimm
 * Modifications Copyright (C) 2013 Thomas Wies
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */

#include <iostream>

#include "java_lang.h"

using namespace java::lang;
using namespace std;

int main(void) {
  // Let's get started.
  cout << "--------------------------------------------------------------"
       << "----------------"
       << endl;

  // Object o = new Object();
  Object o = new __Object();

  cout << "o.toString() : "
       << o->__vptr->toString(o)->data // o.toString()
       << endl;

  // Class oClass = o.getClass();
  Class oClass = o->__vptr->getClass(o);

  cout << "oClass.getName()  : "
       << oClass->__vptr->getName(oClass)->data // oClass.getName()
       << endl
       << "oClass.toString() : "
       << oClass->__vptr->toString(oClass)->data // oClass.toString()
       << endl;

  // Class oClassClass = oClass.getClass();
  Class oClassClass = oClass->__vptr->getClass(oClass);

  cout << "oClassClass.getName()  : "
       << oClassClass->__vptr->getName(oClassClass)->data // oClassClass.getName()
       << endl
       << "oClassClass.toString() : "
       << oClassClass->__vptr->toString(oClassClass)->data // oClassClass.toString()
       << endl;

  // if (oClass.equals(oClassClass)) { ... } else { ... }
  if (oClass->__vptr->equals(oClass, (Object) oClassClass)) {
    cout << "oClass.equals(oClassClass)" << endl;
  } else {
    cout << "! oClass.equals(oClassClass)" << endl;
  }

  // if (oClass.equals(l.getSuperclass())) { ... } else { ... }
  if (oClass->__vptr->equals(oClass, (Object) oClassClass->__vptr->getSuperclass(oClassClass))) {
    cout << "oClass.equals(oClassClass.getSuperclass())" << endl;
  } else {
    cout << "! oClass.equals(oClassClass.getSuperclass())" << endl;
  }

  // if (oClass.isInstance(o)) { ... } else { ... }
  if (oClass->__vptr->isInstance(oClass, o)) {
    cout << "o instanceof oClass" << endl;
  } else {
    cout << "! (o instanceof oClass)" << endl;
  }

  // if (oClassClass.isInstance(o)) { ... } else { ... }
  if (oClassClass->__vptr->isInstance(oClassClass, o)) {
    cout << "o instanceof oClassClass" << endl;
  } else {
    cout << "! (o instanceof oClassClass)" << endl;
  }

  // HACK: Calling java.lang.Object.toString on oClass via o's vptr
  cout << o->__vptr->toString((Object) oClass)->data << endl;

  // Done.
  cout << "--------------------------------------------------------------"
       << "----------------"
       << endl;
  return 0;
}
