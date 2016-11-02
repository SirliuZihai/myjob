package methedTest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisTest {
	
	public static void main(String args[]){
		new RedisTest().Test2();
	}
	
	public void Test(){
		//连接本地的 Redis 服务
	      Jedis jedis = new Jedis("localhost");
	      System.out.println("Connection to server sucessfully");
	     /* jedis.lpush("list", "qw");
	      jedis.lpush("list", "er");
	      jedis.lpush("list", "er");*/
	      jedis.del("list");
	      
	      //查看服务是否运行
	      System.out.println("Server is running: "+jedis.ping());
	}
	public void Test2(){
		Jedis jedis = new Jedis("localhost");
		// 获取存储的数据并输出
	     List<String> list = jedis.lrange("list", 0 ,5);
	     for(int i=0; i<list.size(); i++) {
	       System.out.println("Stored string in redis:: "+list.get(i));
	     }
	}
}

