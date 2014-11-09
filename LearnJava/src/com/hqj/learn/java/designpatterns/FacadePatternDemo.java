/**
 * 
 */
package com.hqj.learn.java.designpatterns;

/**
 * @author Win7Homx64JP
 * Version : 1.0
 * Description : 结构模式Structural Pattern。该模式主要是在复杂系统与用户之间创建一个类。
 * 由该类来向用户隐藏复杂系统的复杂性，并向用户提供功能操作接口。
 */
public class FacadePatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FacadePatternDemo fpd = new FacadePatternDemo();
		fpd.demo();
	}
	
	public void demo(){
		ShapeMaker shapeMaker = new ShapeMaker(new Circle(),new Rectangle(),new Triangle());
		//Draw a circle
		shapeMaker.drawCircle();
		//Draw a rectangle
		shapeMaker.drawRectangle();
		//Draw a triangle
		shapeMaker.drawTriangle();
	}
	
	interface Shape{
		public void draw();
	}
	
	class Circle implements Shape{
		public void draw(){
			System.out.println("Draw circle.");
		}
	}
	class Rectangle implements Shape{
		public void draw(){
			System.out.println("Draw rectangle.");
		}
	}
	class Triangle implements Shape{
		public void draw(){
			System.out.println("Draw triangle.");
		}
	}
	class ShapeMaker{
		private Shape circle,rectangle,triangle;
		
		ShapeMaker(Shape circle, Shape rectangle, Shape triangle){
			this.circle = circle;
			this.rectangle = rectangle;
			this.triangle = triangle;
		}
		public void drawCircle(){
			this.circle.draw();
		}
		public void drawRectangle(){
			this.rectangle.draw();
		}
		public void drawTriangle(){
			this.triangle.draw();
		}
	}
}
