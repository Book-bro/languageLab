package kr.or.ddit.designpattern.adapterpattern;

public class Adapter implements Target {

	private Adaptee adaptee;
	
//	wrapper instance
	public Adapter(Adaptee adaptee) {
		super();
		this.adaptee = adaptee;
	}

	@Override
	public void request() {
		adaptee.specificRequest();
	}

}
