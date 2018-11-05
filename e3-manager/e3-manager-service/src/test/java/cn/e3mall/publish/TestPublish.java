package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试初始化容器
 * 
 * @author kangyong
 * @date 2018年8月18日 下午4:30:19
 * @version 1.0
 */
public class TestPublish {

	/**
	 * 发布服务
	 * 
	 * @throws Exception
	 */
	@Test
	public void publishService() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		/*
		 * while (true) { Thread.sleep(1000); }
		 */
		System.out.println("服务已经启动。。。");
		System.in.read();
		System.out.println("服务已经结束");
	}

}
