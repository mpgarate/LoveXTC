class A {
  static int m(int i) { System.out.println("A.m(int)"); return i; }
  static void m(A a) { System.out.println("A.m(A)"); }
  static void m(double d) { System.out.println("A.m(double)"); }
  static void m(Object o) { System.out.println("A.m(Object)"); }
  static void m(Object o1, Object o2) { System.out.println("A.m(Object, Object)"); }
  static void m(A a1, Object o2) { System.out.println("A.m(A, Object)"); }
}

public class Test033 {
  public static void main(String[] args) {
    A a = new A();
    byte b = 1;
    a.m(b);
    a.m(a);
    a.m(1.0);
    a.m((Object) a);
    a.m(new A(), a);
    a.m(new Object(), a);
  }
}
