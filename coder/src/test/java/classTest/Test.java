package classTest;

public class Test {

	public static void main(String[] args) {
		String s = "liu";
		int i = 1;
		int[] arr ={1,2};
		doing(s);
		System.out.println(s);
	}
	public static void doing(String s){
		s.toCharArray()[0] = 'y';
	}
	public static void doing(int i){
		i=i+1;
	}
	public static void doing(int[] i){
		i[0] = 9;
	}
	public void Test(){
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

