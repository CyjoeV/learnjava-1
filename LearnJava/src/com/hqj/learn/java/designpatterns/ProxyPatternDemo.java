package com.hqj.learn.java.designpatterns;

/**
 * @author hqj
 * Version : 1.0
 * Description : 结构模式。Structural Pattern.该模式用于将一个类B的功能装入另外一个类A中。用户通过调用A类的方法获取B类体统的功能。
 * A类中包含B类。A类的对象中包含唯一一个B类的对象。这样有助于减少内存访问量。
 */
public class ProxyPatternDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProxyPatternDemo ppd = new ProxyPatternDemo();
		ppd.Demo();
	}
	
	public void Demo(){
		RealImage realImage = new RealImage("c/hqj/image1.png");
		//image will be loaded from disk
		realImage.display();
		System.out.println("");
		//image will not be loaded from disk
		//这是proxy模式的目的之一，创建开销比较大的对象，就尽量多重用，减少创建次数。
		realImage.display();
	}
	
	interface Image{
		public void display();
	}
	
	class RealImage implements Image{
		String fileName;
		RealImage(String fileName){
			this.fileName=fileName;
			loadImageFromDisk();
		}
		public void display(){
			System.out.println("Display image file : "+this.fileName);
		}
		public void loadImageFromDisk(){
			System.out.println("Loading file : "+this.fileName);
		}
	}
	
	class ProxyImage implements Image{
		private RealImage realImage;
		private String fileName;
		
		ProxyImage(String fileName){
			this.fileName=fileName;
		}
		
		public void display(){
			if(this.realImage==null){
				this.realImage= new RealImage(this.fileName);
			}
			this.realImage.display();
		}
	}
}
