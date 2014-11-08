/**
 * 2014 Nov 8, 2014
 */
package com.hqj.learn.java.designpatterns;

import java.util.Hashtable;

/**
 * @author hqj
 * Version : 1.0
 * Description : 用于演示prototype pattern。
 * 该pattern用于实现快速获取创建成本较高的类的clone的快速获取
 */
public class PrototypePatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShapeCache.loadCache();
		Shape circle = ShapeCache.getShape("1");
		circle.draw();
		Shape rectangle = ShapeCache.getShape("2");
		rectangle.draw();
		Shape triangle = ShapeCache.getShape("3");
		triangle.draw();
	}

}

abstract class Shape implements Cloneable{
	private String id;
	protected String type;
	
	abstract void draw();
	
	public String getType(){
		return this.type;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public Object clone(){
		Object clone = null;
		try{
			clone = super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return clone;
	}
}

class ShapeCache{
	public static Hashtable<String,Shape> shapeMap  = new Hashtable<String,Shape>();
	public static Shape getShape(String shapeId){
		Shape cachedShape = shapeMap.get(shapeId);
		return (Shape)cachedShape.clone();
	}
	/*
	 * loadCache方法用于获取原始类。实际当中这些类的获取成本很高。
	 * 很可能是需要大量的数据库操作等。
	 * */
	public static void loadCache(){
		Circle circle = new Circle();
		shapeMap.put("1", circle);
		Rectangle rectangle = new Rectangle();
		shapeMap.put("2", rectangle);
		Triangle triangle = new Triangle();
		shapeMap.put("3", triangle);
	}
}

class Circle extends Shape{
	public Circle(){
		this.type="circle";
	}
	public void draw(){
		System.out.println("Draw a circle!");
	}
}

class Rectangle extends Shape{
	public Rectangle(){
		this.type="rectangle";
	}
	public void draw(){
		System.out.println("Draw a rectangle!");
	}
}

class Triangle extends Shape{
	public Triangle(){
		this.type="triangle";
	}
	public void draw(){
		System.out.println("Draw a triangle!");
	}
}
