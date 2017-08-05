package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.zihai.entity.User;

public class IteratorTest {

	public static void main(String[] args) {
		IteratorTest test = new IteratorTest();
		test.Test4();
	}
	
	public void Test2(){
		HashMap<String,String> map=null; //= new HashMap<String,String>();
		Iterator<String> it = map.values().iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

	public void MapObjectTest(){
		HashMap<String,Object> map = new HashMap<String,Object>();
		User user = new User();
		user.setUsername("liu");
		map.put("one", user);
		User user1 = (User) map.get("one");
		user1.setUsername("zhi");
		System.out.println(user.getUsername());
	}
	
	public void Test1(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("1", "liu");
		map.put("2", "yi");
		map.put("3", "zhi");
	//	map.put("4",new User());
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
	public void Test4(){
		List<String> list = new ArrayList<String>();
		list.add("155022");
		list.add("155017");
		list.add("155005");
		list.add("155025");list.add("155024");list.add("155023");
		Collections.sort(list);
		String[] arr =  list.toArray(new String[list.size()]);
		for(String str:arr){
			System.out.println(str);
		}
		
	}

}
