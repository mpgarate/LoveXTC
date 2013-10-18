import subExamples.HelloWorld;
import subExamples.HelloUniverse;
public class TranslateMe {
	public static void main(String[] args){
		HelloWorld hw = new HelloWorld();
		System.out.println(hw.toString());

		HelloUniverse hu = new HelloUniverse();
		System.out.println(hu.toString());

		System.out.println(hu.createWorld());
	}
}
