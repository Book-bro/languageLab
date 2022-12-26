package kr.or.ddit.designpattern.templatemethodpattern;

import java.util.Arrays;

public class Playground {
	public static void main(String[] args) {
		TemplateClass[] array = new TemplateClass[] {new DerivedClass1(), new DerivedClass2()};
		Arrays.stream(array)
				.forEach(TemplateClass::template);
	}
}
