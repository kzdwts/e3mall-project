package cn.e3mall.activemq;

import java.awt.font.TextMeasurer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

/**
 * 测试
 * 
 * @author kangyong
 * @date 2018年8月28日 上午11:13:40
 * @version 1.0
 */
public class ActiveMqTest {

	/**
	 * 点到点形式发送消息
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueueProducer() throws Exception {
		// 1、 创建一个连接工厂对象，需要指定服务的ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.10.131:61616");
		// 2、 使用工厂对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		// 3、 开启连接，调用Connection对象的start方法
		connection.start();
		// 4、 创建一个Session对象
		// 第一个参数：是否开启事物，一般不开启事物。如果开启事物，第二个参数无意义
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、 使用session对象创建一个Destination对象。
		Queue queue = session.createQueue("test-queue");
		// 6、使用session对象创建一个Producer对象
		MessageProducer producer = session.createProducer(queue);
		// 7、创建一个Message对象，可以使用TextMessage
		/*
		 * TextMessage textMessage = new ActiveMQTextMessage();
		 * textMessage.setText("hello Activemq");
		 */
		TextMessage textMessage = session.createTextMessage("Hello World!");
		// 8、发送消息
		producer.send(textMessage);
		// 9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * 测试接收消息
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueueConsumer() throws Exception {
//		创建一个ConnectionFactory对象连接MQ服务器
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.10.131:61616");
//		创建一个连接对象
		Connection connection = connectionFactory.createConnection();
//		开启连接
		connection.start();
//		使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		创建一个Destination对象。queue对象
		Queue queue = session.createQueue("spring-queue");
//		使用Session对象创建一个消费者对象
		MessageConsumer consumer = session.createConsumer(queue);
//		接收消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
//		打印结果
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		// 等待
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	/**
	 * 测试广播队列
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTopicProducer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.10.131:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageProducer producer = session.createProducer(topic);
		TextMessage message = session.createTextMessage("text topic hello");
		producer.send(message);
		// 释放资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * 测试topic消费者
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTopiceConsumer() throws Exception {
//		创建一个ConnectionFactory对象连接MQ服务器
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.10.131:61616");
//		创建一个连接对象
		Connection connection = connectionFactory.createConnection();
//		开启连接
		connection.start();
//		使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		创建一个Destination对象。queue对象
		Topic topic = session.createTopic("test-topic");
//		使用Session对象创建一个消费者对象
		MessageConsumer consumer = session.createConsumer(topic);
//		接收消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
//		打印结果
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("消费者3已经启动。。。");
		// System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

}
