/*
 * Object-Oriented Programming
 * Copyright (C) 2012 Robert Grimm
 * Modifications copyright (C) 2013 Thomas Wies
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

int main(void) {
  // Let's get started.
  std::cout << "--------------------------------------------------------------"
            << "----------------"
            << std::endl;

  // int[] a = new int[5];
  __rt::Array<int32_t>* a = new __rt::Array<int32_t>(5);

  // a[2]
  __rt::checkNotNull(a);
  std::cout << "a[2]  : " << (*a)[2] << std::endl;

  // a[2] = 5;
  __rt::checkNotNull(a);
  (*a)[2] = 5;

  // a[2]
  __rt::checkNotNull(a);
  __rt::checkIndex(a, 2);
  std::cout << "a[2]  : " << a->__data[2] << std::endl;

  // String[] ss = new String[5];
  __rt::Array<String>* ss = new __rt::Array<String>(5);

  // String s = "Hello";
  String s = __rt::literal("Hello");

  // ss[2] = "Hello";
  __rt::checkNotNull(ss);
  __rt::checkStore(ss, s);
  (*ss)[2] = s;

  std::cout << "ss[2] : " << (*ss)[2] << std::endl;


  // Done.
  std::cout << "--------------------------------------------------------------"
            << "----------------"
            << std::endl;
  return 0;
}
