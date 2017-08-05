package classTest;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.WrapDynaBean;

public class Test {

	public static void main(String[] args) {
		/*String s = "liu";
		int i = 1;
		int[] arr ={1,2};
		doing(i);
		System.out.println(i);*/
		new Test().Test2();
		
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
	/**
	 * 子类可改变父类的变量测试
	 * */
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
	
	/**
	 * 外部直接改变类内部对象值测试
	 * 注意：对象的私有对象传出去注意需要对象复制
	 * */
	public void Test2(){
		Children child = new Children();
		child.setAge(23);
		Integer i = child.getAge();
		i = 24;
		System.out.println(child.getAge()); //不可以改Integer变值   输出23
		People p = new People();
		p.setAge(26);
		child.setFirend(p);
		p = child.getFirend();
		p.setAge(27);
		System.out.println(child.getFirend().getAge());//会改变值 输出27 
	}
}

