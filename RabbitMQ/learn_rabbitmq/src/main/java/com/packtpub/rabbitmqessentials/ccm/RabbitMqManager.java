package com.packtpub.rabbitmqessentials.ccm;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

@Named
public class RabbitMqManager implements ShutdownListener {
	private final Logger LOGGER = Logger.getLogger(getClass().getName());
	
	private final ConnectionFactory connectionFactory;
	private final ScheduledExecutorService scheduledExecutorService;
	private volatile Connection connection;
	
	
	
	public RabbitMqManager(final ConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		connection = null;
	}

	public void start(){
		try{
			connection = connectionFactory.newConnection();
			connection.addShutdownListener(this);
			LOGGER.info("Connected to : "+connectionFactory.getHost()+":"+connectionFactory.getPort());
		}catch(final Exception e){
			LOGGER.log(Level.SEVERE, "Failed to connect to "+connectionFactory.getHost()+":"+connectionFactory.getPort());
			asyncWaitAndReconnect();
		}
	}
	
	private void asyncWaitAndReconnect(){
		this.scheduledExecutorService.schedule(new Runnable(){
			public void run(){
				start();
			}
		}, 15, TimeUnit.SECONDS);
	}

	public void shutdownCompleted(final ShutdownSignalException cause) {
		// TODO Auto-generated method stub
		//Reconnect only on unexpected errors
		if(!cause.isInitiatedByApplication()){
			LOGGER.log(Level.SEVERE,"Lost connection to "+connectionFactory.getHost()+":"+connectionFactory.getPort());
			connection = null;
			asyncWaitAndReconnect();
		}
	}
	
	//This method is used to cleanly terminate RabbitMqManager
	public void stop(){
		this.scheduledExecutorService.shutdownNow();
		if(connection == null){
			return;
		}
		try{
			connection.close();
		}catch(final Exception e){
			LOGGER.log(Level.SEVERE,"Failed to close connection!",e);
		}finally{
			connection = null;
		}
	}
	
	//Create channels. Real works happen in channels. But conneciton is the basis of anything with RabbitMQ.
	public Channel createChannel(){
		try{
			return connection==null?null:connection.createChannel();
		}catch(final Exception e){
			LOGGER.log(Level.SEVERE, "Failed to create channel", e);
			return null;
		}
	}
	
	public void closeChannel(final Channel channel){
		//isOpen is not fully trustable!
		if(channel == null || !channel.isOpen()){
			return;
		}
		try{
			channel.close();
		}catch(final Exception e){
			LOGGER.log(Level.SEVERE, "Failed to close channel", e);
		}
	}
	
	public <T> T call(final ChannelCallable<T> channelCallable){
		Channel channel = createChannel();
		if(channel != null){
			try{
				return channelCallable.call(channel);
			}catch(final Exception e){
				LOGGER.log(Level.SEVERE,"Failed to run "+channelCallable.getDescription(),e);
			}finally{
				closeChannel(channel);
			}
		}
		return null;
	}
}
