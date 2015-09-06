package com.hqj.learn.java.concurrency;

public class VolatileKeywordTester {
	private static volatile int count=0;
	public static void inc(){
		try{
			//睡眠1毫秒
			Thread.sleep(1);
		}catch(Exception e){
			
		}
		count++;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<1000;i++){
			new Thread(new Runnable(){
				@Override	
				public void run(){
					inc();
				}
			}).start();
		}
		try{
			Thread.sleep(1010);
		}catch(Exception e){
		}
		
		System.out.println("count is "+count);
	}

}
