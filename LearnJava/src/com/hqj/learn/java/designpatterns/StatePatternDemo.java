/**
 * 
 */
package com.hqj.learn.java.designpatterns;

/**
 * @author hu qijin
 * Version : 1.0
 * Description : 该模式为行为模式。该模式中，对象的行为根据它的状态的改变而改变。
 * 我们创建两个对象， 一个State对象，代表各个状态。
 * 一个context对象，该对象的行为根据state的不同而改变。
 * 创建State接口与Context类。
 */
public class StatePatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StatePatternDemo spd = new StatePatternDemo();
		spd.demo();
	}
	
	public void demo(){
		Context context = new Context();
		StartState startState = new StartState();
		StopState stopState = new StopState();
		
		startState.doAction(context);
		System.out.println(context.getState().toString());
		stopState.doAction(context);
		System.out.println(context.getState().toString());
	}
	
	interface State{
		public void doAction(Context context);
	}
	
	class StartState implements State{
		public void doAction(Context context){
			System.out.println("Player is in Start State");
			context.setState(this);
		}
		
		public String toString(){
			return "Start State";
		}
	}
	
	class StopState implements State{
		public void doAction(Context context){
			System.out.println("Player is in Stop State");
			context.setState(this);
		}
		
		public String toString(){
			return "Stop State";
		}
	}
	
	class Context{
		private State state;

		/**
		 * 
		 */
		public Context() {
			super();
			this.state=null;
		}

		/**
		 * @return the state
		 */
		public State getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(State state) {
			this.state = state;
		}
	}
}