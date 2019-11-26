package org.zhaoyu.test.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 目前Redis实现集群的方法主要是采用一致性哈稀分片（Shard），将不同的key分配到不同的redis server上，达到横向扩展的目的。
 * @author Administrator
 *
 */
public class RedisClient {
	// 非切片额客户端
	private Jedis jedis;
	// 非切片额连接池
	private JedisPool jedisPool;
	// 切片额客户端
	private ShardedJedis shardedJedis;
	// 切片额客户端连接池
	private ShardedJedisPool shardedJedisPool;
	
	
	public RedisClient(){
		initPool();
		initShardedPool();
		jedis=jedisPool.getResource();
		shardedJedis=shardedJedisPool.getResource();
	}
	
	public void initPool(){
		JedisPoolConfig config=new JedisPoolConfig();
		//池基本配置
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10001);
		config.setTestOnBorrow(false);
		jedisPool=new JedisPool(config,"127.0.0.1",6379);
	}
	
	public void initShardedPool(){
		JedisPoolConfig config=new JedisPoolConfig();
		
		//池基本配置
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10001);
		config.setTestOnBorrow(false);
		//slave 连接
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("127.0.0.1",6379,"master"));
		
		shardedJedisPool=new ShardedJedisPool(config, shards);
	}
	
	public Jedis getJedis(){
		return jedis;
	}
	public ShardedJedis getShardedJedis(){
		return shardedJedis;
	}
}