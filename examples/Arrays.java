public class Arrays{
  public static void main(String[] args){

    int[] a = new int[5];
    // __rt::Array<int32_t>* a = new __rt::Array<int32_t>(5);
    
    System.out.println(a[2]); //0

    a[2] = 5;

    System.out.println(a[2]); //5

    String[] ss = new String[5];

    String s = "Hello";

    ss[2] = "Hello";

    System.out.println(ss[2]); //Hello
  }
}
