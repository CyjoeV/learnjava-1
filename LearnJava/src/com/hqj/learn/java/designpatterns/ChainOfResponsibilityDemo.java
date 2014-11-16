/**
 * 
 */
package com.hqj.learn.java.designpatterns;

/**
 * @author hu
 * Version :1.0
 * Description : 该模式是行为模式。它为一个请求创建一个接收者链条。接收者通过请求的类型处理请求。根据不同的条件从一个接收者发给下一个接收者。
 * 该模式将请求的发起者与请求的接收者结构。特别适合用于根据log的级别来选择log记录对象的情况。
 * 下面的程序假设：1，级别为ERROR的log消息所有的log（console log, file log, tape log）中介质中都必须记载。
 * debug log只需要文件记载(file log)即可。而tape log就必须记录所有的log.
 */
public class ChainOfResponsibilityDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChainOfResponsibilityDemo crd = new ChainOfResponsibilityDemo();
		crd.demo();
	}

	public AbstractLogger getLogger(){
		AbstractLogger consoleLogger = new ConsoleLog(AbstractLogger.ERROR);
		AbstractLogger fileLogger = new FileLog(AbstractLogger.DEBUG);
		AbstractLogger tapeLogger = new TapeLog(AbstractLogger.INFO);
		//建立接收者链条。
		consoleLogger.setNextLogger(fileLogger);
		fileLogger.setNextLogger(tapeLogger);
		
		return consoleLogger;
	}
	
	public void demo(){
		AbstractLogger logger = this.getLogger();
		logger.log(AbstractLogger.ERROR, "This is an error message");
		logger.log(AbstractLogger.DEBUG, "This is an debug message");
		logger.log(AbstractLogger.INFO, "This is an info message");
	}
	
	class ConsoleLog extends AbstractLogger{
		
		ConsoleLog(int level){
			this.level=level;
		}
		
		public void writeMessage(String message){
			System.out.println("Consle log : "+message);
		}
	}
	
	class FileLog extends AbstractLogger{
		FileLog(int level){
			this.level=level;
		}
		public void writeMessage(String message){
			System.out.println("File log : "+message);
		}
	}
	
	class TapeLog extends AbstractLogger{
		TapeLog(int level){
			this.level=level;
		}
		public void writeMessage(String message){
			System.out.println("Tape log : "+message);
		}
	}
}

abstract class AbstractLogger{
	public static int ERROR=1;
	public static int DEBUG=2;
	public static int INFO=3;
	
	protected int level;
	protected AbstractLogger nextLogger;
	
	public void setNextLogger(AbstractLogger nextLogger){
		this.nextLogger=nextLogger;
	}
	
	public void log(int level, String message){
		//如果当前的消息级别属于
		if(this.level<=level){
			this.writeMessage(message);
		}
		if(this.nextLogger!=null){
			//如果有下级接收者的话，就传给它处理。
			this.nextLogger.log(level, message);
		}
	}
	
	abstract void writeMessage(String message);
}
