package classTest;

public class Children extends Parent {
	private Integer age;
	private People firend;
	public void change(String name){
		this.name=name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public People getFirend() {
		return firend;
	}
	public void setFirend(People firend) {
		this.firend = firend;
	}
}
