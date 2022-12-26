package kr.or.ddit.designpattern.templatemethodpattern;

public class DerivedClass2 extends TemplateClass {

	@Override
	protected void stepTwo() {
		System.out.println("B 방식으로 구현된 2단계");
	}

}
