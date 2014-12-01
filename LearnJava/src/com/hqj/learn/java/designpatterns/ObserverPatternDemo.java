/**
 * 
 */
package com.hqj.learn.java.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hu qijin
 * Version : 1.0
 * Description : 该模式属于行为吗模式。
 * 该模式用于对象之间存在一对多的关系，当一个对象被改变，其他的对象需要被自动通知到的情况。
 * Observer模式使用三种类的对象，Subject, Observer, Client.
 * Subject class having methods to attach observers to or detach observers from clients.  
 */
public class ObserverPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObserverPatternDemo opd = new ObserverPatternDemo();
		opd.demo();
	}
	
	public void demo(){
		Subject subject = new Subject(5);
		
		BinaryObserver binaryObserver = new BinaryObserver(subject);
		OctalObserver octalObserver = new OctalObserver(subject);
		HexObserver hexObserver = new HexObserver(subject);
		
		System.out.println("First state change.");
		subject.setState(10);
		System.out.println("Second state change.");
		subject.setState(15);
	}
	
	class Subject{
		
		private int state;
		private List<Observer> observers = new ArrayList<Observer>();

		/**
		 * @param state
		 */
		public Subject(int state) {
			super();
			this.state = state;
		}
		/**
		 * @return the state
		 */
		public int getState() {
			return state;
		}
		/**
		 * @param state the state to set
		 */
		public void setState(int state) {
			this.state = state;
			this.notifyAllObservers();
		}
		
		public void attach(Observer observer){
			this.observers.add(observer);
		}
		
		public void notifyAllObservers(){
			for(Observer observer : observers){
				observer.update();
			}
		}
	}
	
	abstract class Observer{
		protected Subject subject;
		abstract public void update();
	}
	
	class BinaryObserver extends Observer{
		
		BinaryObserver(Subject subject){
			this.subject=subject;
			this.subject.attach(this);
		}
		
		public void update(){
			System.out.println("Binary String : "+Integer.toBinaryString(this.subject.getState()));
		}
	}
	
	class OctalObserver extends Observer{
		
		OctalObserver(Subject subject){
			this.subject=subject;
			this.subject.attach(this);
		}
		
		public void update(){
			System.out.println("Octal String : "+Integer.toOctalString(this.subject.getState()));
		}
	}
	
	class HexObserver extends Observer{
		
		HexObserver(Subject subject){
			this.subject=subject;
			this.subject.attach(this);
		}
		
		public void update(){
			System.out.println("Hex String : "+Integer.toHexString(this.subject.getState()));
		}
	}
}
