/**
 * 2014 Nov 8, 2014
 */
package com.hqj.learn.java.designpatterns;

/**
 * @author hqj
 * Version : 1.0
 * Description :用于演示Bridge Pattern。
 * 该模式用于分解抽象功能接口的实现与实际类的实现。 而两者之间的联系通过抽象的Bridge类来实现。
 * 比如可以通过传递实现功能接口的不同的类，来实现实际类的不同的行为表现。
 */
public class BridgePatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BridgePatternDemo bpd = new BridgePatternDemo();
		bpd.demonstrate();
	}
	
	/*
	 * 因为使用了内部类，所以将demo独立写到一个方法里面。
	 */
	public void demonstrate(){
		//Draw a green circle.
		DrawGreenCircle drawGreenCircle = new DrawGreenCircle();
		Shape greenCircle = new Circle(10,1,1,drawGreenCircle);
		greenCircle.draw();
		//Draw a red circle
		DrawRedCircle drawRedCircle = new DrawRedCircle();
		Shape redCircle = new Circle(15,3,3,drawRedCircle);
		redCircle.draw();
	}
	
	interface DrawApi{
		public void drawCircle(int radius, int x, int y);
	}

	class DrawRedCircle implements DrawApi{
		public void drawCircle(int radius, int x, int y){
			System.out.println("Draw red circle : [ radius = "+radius+"; x = "+x+"; y = "+y+" ]");
		}
	}

	class DrawGreenCircle implements DrawApi{
		public void drawCircle(int radius, int x, int y){
			System.out.println("Draw green circle : [ radius = "+radius+"; x = "+x+"; y = "+y+" ]");
		}
	}
	/*
	 * 该类就是起bridge作用的类。它通过成员变量与构造方法将功能接口DrawApi与
	 * 类的其他部分的实现隔离开来了。DrawApi与类的其他部分通过这个抽象的Shape类来连接。
	 */
	abstract class Shape {
		protected DrawApi drawApi;
		protected Shape(DrawApi drawApi){
			this.drawApi = drawApi;
		}
		abstract public void draw();
	}

	class Circle extends Shape{
		private int radius,x,y;
		public Circle(int radius, int x, int y, DrawApi drawApi){
			super(drawApi);
			this.radius=radius;
			this.x=x;
			this.y=y;
		}
		
		public void draw(){
			this.drawApi.drawCircle(radius, x, y);
		}
	}
}

