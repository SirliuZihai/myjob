package com.zihai.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.alibaba.fastjson.JSON;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
	private MongoDatabase database;
	private MongoClient mongoClient;
	MongoUtil(){
		mongoClient = new MongoClient( "192.168.38.128" , 27017 );
		database = mongoClient.getDatabase("susan");
	}
	private MongoCollection<Document> getCollection(String name){
		return database.getCollection(name);
	}
	
	public List Query(Document doc , Class<? extends Object> clazz,String collectionName){
		List list = new ArrayList();
		Block<Document> block = new Block<Document>() {
		       @Override
		       public void apply(final Document document) {
		    	   list.add(JSON.parseObject(document.toJson(), clazz));
		       }
		};
		getCollection(collectionName).find(doc).forEach(block);
		return list;
		
	}
	/**析构函数*/
	protected void finalize(){
		mongoClient.close();
	}
}
