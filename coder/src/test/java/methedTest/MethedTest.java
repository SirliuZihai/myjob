package methedTest;

import java.util.Arrays;

public class MethedTest {
	
	public static void print(int i,String...args){
		if(args.length!=0){
			if(args.length ==2){
				System.out.println(args[1]);
			}else{
				System.out.println(args[0]);
			}
		}else{
			System.out.println(i);
		}
	}
	public static String getValue(int key){
		try{
			if(key==1)throw new Exception("报错了");
			return String.valueOf(key);
		}catch(Exception e){
			System.out.println("报错为："+e.getMessage());
		}finally{
			System.out.println("do finally");
		}
		return "得到返回值后的值";
	}
	public static void main(String[] args) {
		print(22);
	}

}
