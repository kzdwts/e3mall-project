package cn.e3mall.content.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 测试redis
 * 
 * @author kangyong
 * @date 2018年8月19日 上午11:42:37
 * @version 1.0
 */
public class JedisTest {

	/**
	 * 测试jedis单机版
	 */
	@Test
	public void jedisTest() {
		// 创建一个连接：参数：host、port
		Jedis jedis = new Jedis("192.168.10.131", 6379);
		// 直接操作
		jedis.set("mykey1", "my first redis test");
		String string = jedis.get("mykey1");
		System.out.println(string);

		// 关闭连接
		jedis.close();
	}

	/**
	 * jedis连接池
	 */
	@Test
	public void jedisPoolTest() {
		// 创建连接池对象
		JedisPool pool = new JedisPool("192.168.10.131", 6379);
		// 从连接池取出一个连接
		Jedis jedis = pool.getResource();
		// 操作
		String string = jedis.get("mykey1");
		System.out.println(string);

		// 关闭连接池
		jedis.close();
		pool.close();
	}

	/**
	 * 测试redis-cluster
	 */
	@Test
	public void testJedisCluster() {
		// 创建jedisCluster对象。有一个参数nodes是一个set类型。set中包含若干个HostAndPort对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.10.131", 7001));
		nodes.add(new HostAndPort("192.168.10.131", 7002));
		nodes.add(new HostAndPort("192.168.10.131", 7003));
		nodes.add(new HostAndPort("192.168.10.131", 7004));
		nodes.add(new HostAndPort("192.168.10.131", 7005));
		nodes.add(new HostAndPort("192.168.10.131", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		// 直接只用JedisCluster操作数据
		cluster.set("mykey2", "wanglili");
		String string = cluster.get("mykey2");
		System.out.println(string);
		// 关闭连接
		cluster.close();
	}

}
