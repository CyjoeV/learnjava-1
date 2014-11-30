package com.hqj.learn.java.designpatterns;

import java.util.Date;

/**
 * @author hu qijin
 * Version : 1.0
 * Description : 行为模式，用于减少多个对象之间的通信复杂度(communication complexity).该模式提供一个中间人处理所有类之间的通信
 * 并且通过loose coupling 使维护简化。
 *
 */
public class MediatorPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatRoomUser userJack = new ChatRoomUser("Jack");
		ChatRoomUser userLucy = new ChatRoomUser("Lucy");
		userJack.sendMessage("This is Hello from Jack!");
		userLucy.sendMessage("This is Good to see you from Lucy");
	}
	
}

class ChatRoom{
	public static void showMessage(ChatRoomUser user, String message){
		System.out.println(new Date().toString()+" [ "+user.getName()+" ] : "+message);
	}
}

class ChatRoomUser{
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	ChatRoomUser(String name){
		this.name=name;
	}
	
	public void sendMessage(String message){
		ChatRoom.showMessage(this, message);
	}
}
