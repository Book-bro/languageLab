package kr.or.ddit.object;

public class StringPlayground {
	public static void main(String[] args) {
		String str1 = "SAMPLE";
		str1 = str1+"append";
		String str2 = "SAMPLE";
		String str3 = new String("SAMPLE");
		String str4 = new String(str1);

		StringBuffer original = new StringBuffer("SAMPLE");
		original.append("append");
		
		System.out.printf("str1 == str2 : %b \n", str1 == str2);
		System.out.printf("str2 == str3 : %b \n", str2 == str3);
		System.out.printf("str3 == str4 : %b \n", str3 == str4);
		System.out.printf("str4 == str1 : %b \n", str4 == str1);
		System.out.printf("str4 == str1 : %b \n", str4.intern() == str1);
		System.out.printf("str4 == str2 : %b \n", str4.intern() == str2);
		System.out.printf("str4 == str3 : %b \n", str4.intern() == str3.intern());
	}
}
