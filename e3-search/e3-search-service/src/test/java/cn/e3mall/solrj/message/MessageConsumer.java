package cn.e3mall.solrj.message;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动容器，监听Activemq消息
 * 
 * @author kangyong
 * @date 2018年8月30日 上午11:35:48
 * @version 1.0
 */
public class MessageConsumer {

	/**
	 * 启动容器
	 */
	@Test
	public void testMsgConsumer() throws Exception {
		// 初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		// 等待
		System.in.read();
	}

}
