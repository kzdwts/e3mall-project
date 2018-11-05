package cn.e3mall.content.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

/**
 * 读取配置文件测试jedis
 * 
 * @author kangyong
 * @date 2018年8月19日 下午12:27:55
 * @version 1.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//	"classpath:spring/applicationContext-redis.xml"
//})
public class JedisClientTest {

//	@Autowired
//	private JedisClient jedisClient;

	@Test
	public void testJedisClient() {
		// 初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-redis.xml");
		// 从容器中获取JedisClient对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("mykey1", "test123 kangyong");
		String string = jedisClient.get("mykey1");
		System.out.println(string);
	}

}
