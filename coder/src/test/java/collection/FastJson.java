package collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zihai.common.TreeNode;
import com.zihai.entity.Area;
import com.zihai.service.RedisService;
import com.zihai.service.iml.RedisServiceiml;
import com.zihai.util.RedisUtil;

public class FastJson {

	public static void main(String[] args) {
		// RedisUtil<String,HashMap<String,Area>> redisService =new RedisUtil<String,HashMap<String,Area>>();
		// TreeNode node = redisService.get("AreaTree");
		 //System.out.println(node.getChildren().get(0).getText());
		HashMap<String,Area> map = new HashMap<String,Area>();
		//List<Area> list = new ArrayList<Area>();
		Area area = new Area();
		area.setId("893712");
		area.setAreaname("大坂");
		map.put(area.getId(), area);
		//redisService.add("test", map);
		//list.add(area);
		byte[] b = JSON.toJSONBytes(map);
		//System.out.println(b);
		//JSON.parse(b).
		//HashMap<String,Area> map2 = JSON.parseObject(b, HashMap.class);
		//String str = map2.get("893712").getAreaname();
		//Area map2 = JSON.parseObject(JSON.toJSONString(JSON.parse(b)),new TypeReference<Area>(){});
		//System.out.println(redisService.get("test").get("893712").getAreaname());
		//HashMap<String,Area> map2 = JSON.parseObject(JSON.toJSONString(JSON.parse(b)),new TypeReference<HashMap<String,Area>>(){});
	//	System.out.println(map2.get("893712").getAreaname());
	}

}
