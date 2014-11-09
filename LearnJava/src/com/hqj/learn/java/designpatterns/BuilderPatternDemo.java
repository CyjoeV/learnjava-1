/**
 * 2014 Nov 8, 2014
 */
package com.hqj.learn.java.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hqj
 * Version : 1.0
 * Description : 属于结构模式Structural Pattern。将实现一个复杂功能的类细分为小类（组件）。
 * 然后通过Builder类将他们整合起来，实现复杂的功能。
 */
public class BuilderPatternDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MealBuilder mealBuilder = new MealBuilder();
		System.out.println("Perpare vegetarian meal;");
		Meal meal = mealBuilder.prepareVegMeal();
		meal.showItems();
		System.out.println("Cost : "+meal.getCost());
		System.out.println("Prepare meat meal;");
		meal = mealBuilder.prepareMeatMeal();
		meal.showItems();
		System.out.println("Cost : "+meal.getCost());
	}
}

class MealBuilder {
	public Meal prepareVegMeal(){
		Meal meal = new Meal();
		meal.addItem(new VegBurger());
		meal.addItem(new Coke());
		return meal;
	}
	public Meal prepareMeatMeal(){
		Meal meal = new Meal();
		meal.addItem(new ChickenBurger());
		meal.addItem(new Pesi());
		return meal;
	}
}

interface Packing {
	public String getPacking();
}

interface Item {
	public String name();
	public Packing packing();
	public float price();
}

class Meal {
	List<Item> items = new ArrayList<Item>();
	public void addItem(Item item){
		this.items.add(item);
	}
	
	public float getCost(){
		float price=0f;
		for(Item item : items){
			price+=item.price();
		}
		return price;
	}
	
	public void showItems(){
		for(Item item : items){
			System.out.println("Name : "+item.name());
			System.out.println("wrapper : "+item.packing().getPacking());
			System.out.println("Price : "+item.price());
		}
	}
}

class Wrapper implements Packing{
	public String getPacking(){
		return "wrapper";
	}
}

class Bottle implements Packing{
	public String getPacking(){
		return "bottle";
	}
}

abstract class Burger implements Item{
	public Packing packing(){
		return new Wrapper();
	}
}

abstract class ColdDrink implements Item{
	public Packing packing(){
		return new Bottle();
	}
}

class VegBurger extends Burger{
	public String name(){
		return "Vegtarian Burger";
	}
	public float price(){
		return 3.0f;
	}
}

class ChickenBurger extends Burger{
	public String name(){
		return "Chicken Burger";
	}
	public float price(){
		return 5.0f;
	}
}

class Coke extends ColdDrink{
	public String name(){
		return "Coke";
	}
	public float price(){
		return 1.5f;
	}
}

class Pesi extends ColdDrink{
	public String name(){
		return "Pesi";
	}
	public float price(){
		return 1.5f;
	}
}