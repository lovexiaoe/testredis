package org.zhaoyu.test.redis.dealobject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.zhaoyu.test.redis.RedisClient;

import redis.clients.jedis.Jedis;

public class ObjectOperateTest {
	public static void main(String[] args) {
		//map
		RedisClient client=new RedisClient();
		Jedis jedis=client.getJedis();
		
		MapOperate(jedis);
		objectOperate(jedis);
		
		
	}
	
	public static void MapOperate(Jedis jedis){
		Map<String, String> user=new HashMap<String,String>();
		user.put("name", "shabi");
		user.put("passwd", "123456");
		
		jedis.hmset("user", user);
		//map中key的个数
		System.out.println(String.format("len:%d", jedis.hlen("user")));
		//map中所有key值
		System.out.println(String.format("keys:%s", jedis.hkeys("user")));
		//map中所有value值
		System.out.println(String.format("values:%s", jedis.hvals("user")));
		
		/*len:2
		keys:[passwd, name]
		values:[123456, shabi]*/
	}
	
	public static void objectOperate(Jedis jedis){
		//存储对象
		Person person1=new Person();
		person1.setTid(1);
		person1.setName("张三");
		person1.setPassword("123");
		try {
			jedis.set(("user:"+person1.getTid()).getBytes(), SerializeUtil.serialize(person1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//读取对象
		byte[] person=jedis.get(("user:"+person1.getTid()).getBytes());
		Person person2=null;
		try {
			 person2=(Person) SerializeUtil.unserialize(person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(person2.getTid());
		System.out.println(person2.getName());
		System.out.println(person2.getPassword());
	}
}
