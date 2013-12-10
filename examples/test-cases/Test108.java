//ArithmeticException

public class Test108 {
	public static void main(String[ ] args) {
		int i=1;
		int j=1;
		
		//try
		//{
			i++; //becomes 2
			j--; //becomes 0
			
			if (i/j > 1)
			{
				i++;
			}
		//}

		/*catch(ArithmeticException e)
		{				
			System.out.println("arithmetic error.");
		}*/

		
	}
}
