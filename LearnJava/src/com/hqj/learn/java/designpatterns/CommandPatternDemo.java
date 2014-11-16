/**
 * 
 */
package com.hqj.learn.java.designpatterns;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hu
 * Version :
 * Description : 命令模式属于数据驱动模式，在分类上属于行为模式的一种。该模式下，请求被包装到一个command类中并且被传递给请求的发起者invoker。
 * invoker根据command的类型来找何时的command receiver. 然后将命令发给它，由它来处理。
 */
public class CommandPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommandPatternDemo cpd = new CommandPatternDemo();
		cpd.demo();
	}
	
	public void demo(){
		//请求类
		Stock amazonStock = new Stock("AMZ",100);
		Stock aliStock = new Stock("ALI",200);
		Stock mseStock = new Stock("MSE",150);
		//将请求类包装到命令类中
		BuyStock buyAmazon = new BuyStock(amazonStock);
		BuyStock buyAli = new BuyStock(aliStock);
		SellStock sellMSE = new SellStock(mseStock);
		
		//请求发起者。Invoker
		StockBroker stockBroker = new StockBroker();
		
		//接收命令。
		stockBroker.getOrder(buyAli);
		stockBroker.getOrder(sellMSE);
		stockBroker.getOrder(buyAmazon);
		
		//发起请求，由 command receiver处理请求
		stockBroker.placeOrder();
		
	}
	
	//命令接口
	interface Order{
		public void executeOrder();
	}
	//请求类。请求类会被包装到命令接口的类中。
	class Stock{
		
		private String stockName;
		private int ammount;
		
		Stock(String stockName,int ammount){
			this.stockName=stockName;
			this.ammount=ammount;
		}
		
		public void buyStock(){
			System.out.println("Bought "+this.ammount+" shares of "+this.stockName);
		}
		
		public void sellStock(){
			System.out.println("Sold "+this.ammount+" shares of "+this.stockName);
		}
	}
	//实际的命令处理类
	class BuyStock implements Order{
		private Stock stock;
		
		BuyStock(Stock stock){
			this.stock=stock;
		}
		
		public void executeOrder(){
			this.stock.buyStock();
		}
	}
	//实际的命令处理类
	class SellStock implements Order{
		private Stock stock;
		
		SellStock(Stock stock){
			this.stock=stock;
		}
		
		public void executeOrder(){
			this.stock.sellStock();
		}
	}
	
	//该类就是invoker。负责将命令转给合适的处理者。
	class StockBroker{
		
		private List<Order> orders = new ArrayList<Order>();
		
		public void getOrder(Order order){
			this.orders.add(order);
		}
		
		public void placeOrder(){
			for(Order order : orders){
				order.executeOrder();
			}
		}
	}
	
}
