package classTest;

public class Test {

	public static void main(String[] args) {
		Children child = new Children();
		Children child2 = new Children();
		System.out.println(Children.name);
		child.change("zihai");
		child2.change("zihai2");
		System.out.println("child's name:"+child.name);
		System.out.println("child2's name:"+child2.name);
		System.out.println("Class's name:"+Children.name);
		System.out.println(Parent.name);
	}

}
