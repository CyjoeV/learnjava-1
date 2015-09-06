package qijin.learn_rabbitmq;

import com.packtpub.rabbitmqessentials.ccm.RabbitMqManager;
import com.packtpub.rabbitmqessentials.ccm.UserMsgManager;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQDemo {
	static final String USER_INBOXES_EXCHANGE = "user-inboxes"; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final ConnectionFactory cf = new ConnectionFactory();
		cf.setUsername("admin");
		cf.setPassword("admin");
		cf.setVirtualHost("rabbitmq_demo");
		cf.setHost("192.168.148.130");
		cf.setPort(15672);
		
		final RabbitMqManager rabbitMqManager = new RabbitMqManager(cf);
		
		UserMsgManager umm = new UserMsgManager();
		umm.onApplicationStart();
		umm.onUserLogin(123);
//		umm.onUserLogin(456);
		umm.sendUserMsg(456, "hello 456", USER_INBOXES_EXCHANGE);
		
	}

}
