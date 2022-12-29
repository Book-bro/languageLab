package kr.or.ddit.object;

public class Parent {
	private String code="default";
	
	public void method() {
		System.out.println("parent method execute");
	}
	
	public void template() {
		this.method(); //생성한놈의 메소드
	}
	
	//두개의 객체의 주소가아닌 상수값으로 비교
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parent other = (Parent) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	
}
