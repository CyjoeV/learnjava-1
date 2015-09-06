package com.packtpub.rabbitmqessentials.ccm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Exchange.BindOk;
import com.rabbitmq.client.AMQP.Exchange.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

@Named
public class UserMsgManager {
	static final String USER_INBOXES_EXCHANGE = "user-inboxes"; 
	static final String USER_TOPICS_EXCHANGE = "user-topics";
	static final String MESSAGE_CONTENT_TYPE = "application/vnd.ccm.pmsg.v1+json";
	static final String MESSAGE_ENCODING = "UTF-8";

	@Inject
	RabbitMqManager mqManager;
	
	//Declare exchange on application start
	public void onApplicationStart(){
		declareDirectExchange();	//Direct exchange
		//declareTopicExchange();		//Topic exchange
	}
	
	private String getUserInboxQueue(final long userId){
		return "user-inbox."+userId;
	}

	private DeclareOk declareDirectExchange() {
		return mqManager.call(new ChannelCallable<DeclareOk>(){
			public String getDescription(){
				return "Declaring direct exchange : "+USER_INBOXES_EXCHANGE;
			}
			
			public DeclareOk call(Channel channel) throws IOException {
				String exchange = USER_INBOXES_EXCHANGE;
				String type = "direct";
				//survive a server restart
				boolean durable = true;
				//keep it even if nobody is not using it
				boolean autoDelete = false;
				//no special arguments
				Map<String,Object> arguments = null;
				return channel.exchangeDeclare(exchange, type, durable, autoDelete, arguments);
			}
		});
	}
	
	private DeclareOk declareTopicExchange() {
		return mqManager.call(new ChannelCallable<DeclareOk>(){
			public String getDescription(){
				return "Declaring topic exchange : " +USER_TOPICS_EXCHANGE;
			}
			
			public DeclareOk call(Channel channel) throws IOException {
				String exchange = USER_TOPICS_EXCHANGE;
				String type = "topic";
				//survive a server restart
				boolean durable = true;
				//keep it even if nobody is not using it
				boolean autoDelete = false;
				//no special arguments
				Map<String,Object> arguments = null;
				return channel.exchangeDeclare(exchange, type, durable, autoDelete, arguments);
			}
		});
	}
	
	//Create user's message queue on user login
	public void onUserLogin(final long userId){
		final String queue = getUserInboxQueue(userId);
		
		mqManager.call(new ChannelCallable<BindOk>(){
			
			public String getDescription(){
				return "Declaring user queue : " + queue + " and bind it to : "+USER_INBOXES_EXCHANGE;
			}
			
			public BindOk call(Channel channel) throws IOException {
				return declareUserMsgQueue(queue, channel, USER_INBOXES_EXCHANGE);
			}
		});
	}
	
	private BindOk declareUserMsgQueue(String queue, Channel channel, String exchange) throws IOException {
		//Survive a server restart
		boolean durable = true;
		//keep the queue even if nobody uses it
		boolean autoDelete = false;
		//can be consumed by another connection
		boolean exclusive = false;
		//no special arguments
		Map<String, Object> arguments = null;
		
		channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
		
		//bind the addressee's queue to the exchange
		String routingKey = queue;
		channel.queueBind(queue, exchange, routingKey);
		return null;
	}
	
	public String sendUserMsg(final long tragetUserId, final String jsonMsg, final String exchange){
		
		return mqManager.call(new ChannelCallable<String>(){
			public String getDescription(){
				return "Sending message to user : "+tragetUserId;
			}
			
			public String call(final Channel channel) throws IOException {
				String queue = getUserInboxQueue(tragetUserId);
				//it taret user queue may not exist so declare it.
				declareUserMsgQueue(queue, channel, exchange);
				
				String messageId = UUID.randomUUID().toString();
				
				BasicProperties props = new BasicProperties.Builder()
										.contentType(MESSAGE_CONTENT_TYPE)
										.contentEncoding(MESSAGE_ENCODING)
										.messageId(messageId)
										.deliveryMode(2)
										.build();
				String routingKey = queue;
				
				//publish the msg to the direct exchange
				channel.basicPublish(exchange, routingKey, props, jsonMsg.getBytes(MESSAGE_ENCODING));
				return messageId;
			}
		});
	}
	
	public String sendTopicMsg(final String exchange, final String topic, final String jsonMsg){
		
		return mqManager.call(new ChannelCallable<String>(){
			public String getDescription(){
				return "Sending topic message : "+topic;
			}
			
			public String call(final Channel channel) throws IOException {
				
				String messageId = UUID.randomUUID().toString();
				
				BasicProperties props = new BasicProperties.Builder()
										.contentType(MESSAGE_CONTENT_TYPE)
										.contentEncoding(MESSAGE_ENCODING)
										.messageId(messageId)
										.deliveryMode(2)
										.build();
				
				//publish the msg to the direct exchange
				channel.basicPublish(exchange, topic, props, jsonMsg.getBytes(MESSAGE_ENCODING));
				return messageId;
			}
		});
	}
	
	public List<String> fetchMsgs(final long userId){
		return mqManager.call(new ChannelCallable<List<String>>(){
			public String getDescription(){
				return "Fetching messages for user : "+userId;
			}
			
			public List<String> call(Channel channel) throws IOException {
				List<String> messages = new ArrayList<String>();
				
				String queue = getUserInboxQueue(userId);
				boolean autoAck = true;
				
				GetResponse getResponse;
				while((getResponse = channel.basicGet(queue, autoAck)) != null){
					final String contentEncoding = getResponse.getProps().getContentEncoding();
					messages.add(new String(getResponse.getBody(), contentEncoding));
				}
				return messages;
			}
		});
	}
	
	public void onUserInterestTopicChange(final String exchange, final long userId, final Set<String> subscribes, final Set<String> unsubscribes){
		final String queue = this.getUserInboxQueue(userId);
		
		mqManager.call(new ChannelCallable<Void>(){
			public String getDescription(){
				return "Binding user queue "+queue+" to Exchange " + exchange + " with " + subscribes + ", unbinding : " + unsubscribes;
			}
			
			public Void call(Channel channel)  throws IOException {
				for(String subscribe : subscribes){
					channel.queueBind(queue, exchange, subscribe);
				}
				for(String unsubscribe : unsubscribes){
					channel.queueUnbind(queue, exchange, unsubscribe);
				}
				return null;
			}
		});
	}
	
}
