/**
 * 2014 Nov 9, 2014
 */
package com.hqj.learn.java.designpatterns;

/**
 * @author hqj
 * Version : 1.0
 * Description : 该模式属于结构模式Structural Pattern.该模式在不改变既存的类的情况下为他们添加新功能。
 */
public class DecoratorPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DecoratorPatternDemo dpd = new DecoratorPatternDemo();
		dpd.demo();
	}
	
	public void demo(){
		Circle circle = new Circle();
		Rectangle rectangle = new Rectangle();
		ShapeDecorator shapeDecorator = new RedCircleDecorator(circle);
		//画一个正方型
		rectangle.draw();
		//画一个圆形
		circle.draw();
		//画一个红色边框的圆形。
		shapeDecorator.draw();
	}
	
	interface Shape{
		public void draw();
	}
	
	class Circle implements Shape{
		public void draw(){
			System.out.println("Draw Circle;");
		}
	}
	
	class Rectangle implements Shape{
		public void draw(){
			System.out.println("Draw Rectangle;");
		}
	}
	
	abstract class ShapeDecorator implements Shape{
		protected Shape decoratedShape;
		ShapeDecorator(Shape decoratedShape){
			this.decoratedShape=decoratedShape;
		}
		public void draw(){
			this.decoratedShape.draw();
		}
	}
	
	class RedCircleDecorator extends ShapeDecorator{
		RedCircleDecorator(Shape decoratedShape){
			super(decoratedShape);
		}
		
		@Override
		/*
		 * 重写draw方法，实现在不改变原有实现了draw接口的类的代码的情况下，对draw功能的扩充。
		 */
		public void draw() {
			// TODO Auto-generated method stub
			decoratedShape.draw();
			this.addRedBorder(this.decoratedShape);
		}

		public void addRedBorder(Shape decoratedShape){
			System.out.println("Border color ： red!");
		}
	}
}