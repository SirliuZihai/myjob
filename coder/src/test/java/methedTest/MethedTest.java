package methedTest;

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

	public static void main(String[] args) {
	//	print(3,"dd","aa");
		String aa=null;
		System.out.println(aa);

	}

}
