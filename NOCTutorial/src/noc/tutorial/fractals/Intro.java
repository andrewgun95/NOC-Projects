package noc.tutorial.fractals;

public class Intro {

	static int factorial(int n) {
		int result = 1;
		for (int i = 0; i < n; i++) {
			result = result * (i + 1);

		}
		return result;
	}
	
	/**
	 * with recursion
	 * 
	 * @param n
	 * @return
	 */
	static int rec_factorial(int n){
		if(n == 1){
			return 1;
		}else
			return n * rec_factorial(n - 1);
	}

	public static void main(String[] args) {
		System.out.println("Factorial 4 is "+factorial(4));
		System.out.println("Recursive Factorial 4 is "+rec_factorial(4));
	}

}
