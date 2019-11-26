package org.zhaoyu.test.redis;


import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisTest {
	public static void main(String[] args) {
		Jedis jedis=new Jedis("192.168.31.202");
		System.out.println("Server is running:"+jedis.ping());
		
		System.out.println("-----华丽做分隔线------");
		testString(jedis);
		System.out.println("-----华丽做分隔线------");
		testList(jedis);
		System.out.println("-----华丽做分隔线------");
		testKey(jedis);
	}
	
	private static void testString(Jedis jedis){
		jedis.set("w3ckey","Redis tutorial");
		System.out.println("Store String in redis:"+jedis.get("w3ckey"));
	}
	
	public static void testList(Jedis jedis){
		jedis.lpush("well-known DB", "redis");
		jedis.lpush("well-known DB", "mongodb");
		jedis.lpush("well-known DB", "mysql");
		//获得并输出数据
		List<String> list=jedis.lrange("well-known DB", 0, 5);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("stored string in redis list:"+list.get(i));
		}
	}
	
	
	public static void testKey(Jedis jedis){
		Set<String> set=(Set<String>) jedis.keys("*");
		for (String string : set) {
			System.out.println("List of stored keys:"+string);
		}
	}
}
