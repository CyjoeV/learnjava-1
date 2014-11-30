/**
 * 
 */
package com.hqj.learn.java.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hu qijin
 * Version : 1.0
 * Description : 属于行为模式， 该模式用于方便地将一个对象恢复到之前的状态。 
 * Originator：原始对象。就是需要将它的状态恢复。
 * Memento : 保存对象的状态。
 * CareTaker : 保存对象的当前状态，或者提供对象以前的状态。
 */
public class MementoPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MementoPatternDemo mementoPatternDemo = new MementoPatternDemo();
		mementoPatternDemo.demo();
	}
	
	public void demo(){
		Originator originator = new Originator();
		CareTaker careTaker = new CareTaker();
		
		originator.setState("state #1");
		originator.setState("state #2");
		careTaker.add(originator.saveStateToMemento());
		originator.setState("state #3");
		careTaker.add(originator.saveStateToMemento());
		originator.setState("state #4");
		System.out.println("Current state : "+originator.getState());
		originator.getStateFromMemento(careTaker.get(0));//将对象恢复到以前的状态
		System.out.println("The first saved state : "+originator.getState());
		originator.getStateFromMemento(careTaker.get(1));
		System.out.println("The second saved state :　"+originator.getState());
	}
	
	
	class Memento{
		String state;

		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}
	}
	
	class Originator{
		String state;
		
		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}
		
		public Memento saveStateToMemento(){
			Memento memento = new Memento();
			memento.setState(this.state);
			return memento;
		}
		
		public void getStateFromMemento(Memento memento){
			this.state = memento.getState();
		}
	}
	
	class CareTaker{
		
		List<Memento> mementoList = new ArrayList<Memento>();
		
		public void add(Memento memento){
			this.mementoList.add(memento);
		}
		
		public Memento get(int index){
			return (Memento)mementoList.get(index);
		}
	}
	
}
