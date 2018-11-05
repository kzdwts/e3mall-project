package cn.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 测试Activemq整合spring
 * 
 * @author kangyong
 * @date 2018年8月30日 上午11:06:31
 * @version 1.0
 */
public class ActiveMqSpring {

	@Test
	public void sendMessage() throws Exception {
		// 初始化容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		// 从容器中获得jmsTemplate
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 从容器中获得Destination
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		// 发送消息
		jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("send activemq message 啦啦啦，我是卖报的小行家");
			}
		});
	}
}
