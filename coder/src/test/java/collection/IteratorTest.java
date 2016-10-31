package collection;

import java.util.HashMap;
import java.util.Iterator;

public class IteratorTest {

	public static void main(String[] args) {
		IteratorTest test = new IteratorTest();
		test.Test1();
	}
	
	public void Test2(){
		HashMap<String,String> map=null; //= new HashMap<String,String>();
		Iterator<String> it = map.values().iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	public void Test1(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("1", "liu");
		map.put("2", "yi");
		map.put("3", "zhi");
		//HashMap<String,String> map2 = (HashMap<String, String>) map.clone();
		
		Test3(map);
		
		Iterator<String> it2 = map.values().iterator();
		while(it2.hasNext()){
			String key = it2.next();
			System.out.println(key);
		}
	}
	public void Test3(HashMap<String,String> map){
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key);
			if(key.equals("1"))
				it.remove();
		}
	}

}
