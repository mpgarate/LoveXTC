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
class A           { public String toString() { return "A"; } }
class B extends A { public String toString() { return "B"; } }
class C extends B { public String toString() { return "C"; } }

class ParentOverloaded {

  public void m(C c)        { System.out.println("ParentOverloaded.m(C)       : " + c); } 
  public void m(A a, C c, A a1) { System.out.println("ParentOverloaded.m(A,C,A)     : "+ a +", "+ c + ", " +a1);}

}
public class Overloaded  extends ParentOverloaded{

  public void m()           { System.out.println("Overloaded.m()        : ---"); }
  public void m(byte b)     { System.out.println("Overloaded.m(byte)    : " + b); }
  public void m(short s)    { System.out.println("Overloaded.(short)   : " + s); }
  public void m(int i)      { System.out.println("Overloaded.m(int)     : " + i); }
  public void m(long l)     { System.out.println("Overloaded.m(long)    : " + l); }
  public void m(Object o)   { System.out.println("Overloaded.m(Object)  : " + o); }
  public void m(String s)   { System.out.println("Overloaded.m(String)  : " + s); }
  //public void m(A a)        { System.out.println("Overloaded.m(A)       : " + a); }
  public void m(B b)        { System.out.println("Overloaded.m(B)       : " + b); }
  public void m(A a1, A a2) { System.out.println("Overloaded.m(A,A)     : "+ a1 +", "+ a2);}
  public void m(A a1, B b2) { System.out.println("Overloaded.m(A,B)     : "+ a1 +", "+ b2);}
  public void m(B b1, A a2) { System.out.println("Overloaded.m(B,A)     : "+ b1 +", "+ a2);}
  public void m(C c1, C c2) { System.out.println("Overloaded.m(C,C)     : "+ c1 +", "+ c2);}
  public void m(A a, B b, A a1) { System.out.println("Overloaded.m(A,B,A)     : "+ a +", "+ b + ", " +a1);}
  public void m(B b, C c, C c1) { System.out.println("Overloaded.m(B,C,C)     : "+ b +", "+ c + ", " +c1);}
  public void m(B b, B b1, B b2) { System.out.println("Overloaded.m(B,B,B)     : "+ b +", "+ b1 + ", " +b2);}
  public void m(A a, A a1, A a2) { System.out.println("Overloaded.m(A,A,A)     : "+ a +", "+ a1 + ", " +a2);}
  public void m(Object o, A a, A a2) { System.out.println("Overloaded.m(Object,A,A)     : "+ o +", "+ a + ", " +a2);}
  public void m(A a, A a1, B b2) { System.out.println("Overloaded.m(A,A,B)     : "+ a +", "+ a1 + ", " +b2);}

  public static void main(String[] args) {
    Overloaded o = new Overloaded();
    byte n1 = 1, n2 = 2;
    short s = 5;
    long l1 = 123456789;
    A a = new A();
    B b = new B();
    C c = new C();

    o.m();
    o.m(n1);
    o.m(n1 + n2);
    o.m(new Object());
    //o.m(new Exception());
    o.m("String");
    o.m(a);
    o.m(b);
    o.m(a);
    o.m(a, a);
    o.m((A)b, b);
    o.m(c, c);
    o.m(c);
    o.m(c,c,c);
    o.m(a,b,(A)c);
  }

}