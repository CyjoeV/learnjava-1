package com.hqj.learn.java.designpatterns;

import java.util.HashMap;

/**
 * @author hu qijin
 * Version : 1.0
 * Description : 结构模式。该模式用于降低系统中对象的数量，减少内存痕迹，提高性能。该模式的主要方式是维护一个类似对象的集合。
 * 如果请求的对象在集合中有的话，就将集合中的对象传给请求。该模式创建新对象时，只创建集合中没有的对象。
 */

public class FlyWeightPatternDemo {
	
	private static final String colors[] = {"Green","Red","Black","Blue","Yellow"};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlyWeightPatternDemo fwpd = new FlyWeightPatternDemo();
		fwpd.demo();
	}
	
	public void demo(){
		for(int i=0;i<20;i++){
			Circle circle = (Circle)ShapeFactory.getCircle(this.getRandomColor());
			circle.setRadius(100);
			circle.setX(this.getRandomX());
			circle.setY(this.getRandomY());
			circle.draw();
		}
	}
	
	public String getRandomColor(){
		return colors[(int)(Math.random()*colors.length)];
	}
	
	public int getRandomX(){
		return (int)(100*Math.random());
	}

	public int getRandomY(){
		return (int)(100*Math.random());
	}
	
	interface Shape{
		public void draw();
	}
	
	class Circle implements Shape{
		
		private int x,y,radius;
		private String color;
		
		Circle(String color){
			this.color=color;
		}
	
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getRadius() {
			return radius;
		}
		public void setRadius(int radius) {
			this.radius = radius;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		
		public void draw(){
			System.out.println("Draw a cirlce : [ color : "+this.color+", radius : "+this.radius+", x : "+this.x+", y : "+this.y);
		}
	}
}

class ShapeFactory{
	
	private static final HashMap<String,FlyWeightPatternDemo.Circle> circleMap= new HashMap<String,FlyWeightPatternDemo.Circle>();
	private static FlyWeightPatternDemo fpd = new FlyWeightPatternDemo();
	
	public static FlyWeightPatternDemo.Shape getCircle(String color){
		FlyWeightPatternDemo.Circle circle = circleMap.get(color);
		if(circle==null){
			circle = fpd.new Circle(color);
			circleMap.put(color, circle);
		}
		return circle;
	}
}
