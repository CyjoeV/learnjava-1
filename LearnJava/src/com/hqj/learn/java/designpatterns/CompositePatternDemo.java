/**
 * 2014 Nov 9, 2014
 */
package com.hqj.learn.java.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hqj
 * Version : 1.0
 * Description : 组合设计模式。该模式是一个结构模式Structural pattern。因为该模式为类的group创建了一个树形结构。 
 * 该类用于对包含多个类似对象的对象group进行组合操作。将它们组合到一个树形结构中去。该树形结构体现全部
 * 或者部分对象group的层级关系。Hierarchy.
 */
public class CompositePatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompositePatternDemo cpd = new CompositePatternDemo();
		cpd.demo();
	}
	public void demo(){
		
		Employee ceo = new Employee("qijin hu","CEO",1);
		Employee headSales = new Employee("meiqiang hu","Head Sales",100000);
		Employee salesExecutive1 = new Employee("nina legro","Sales",50000);
		Employee salesExecutive2 = new Employee("cuisy vogan","Sales",50000);
		Employee headMarketing = new Employee("Leon Song","Head Marketing",90000);
		Employee clerk1 = new Employee("Jane Buffe","Marketing",40000);
		Employee clerk2 = new Employee("William Terna","Marketing",40000);
		
		//创建树形层级结构	Start
		ceo.addSubordinate(headSales);
		ceo.addSubordinate(headMarketing);
		
		headSales.addSubordinate(salesExecutive1);
		headSales.addSubordinate(salesExecutive2);
		
		headMarketing.addSubordinate(clerk1);
		headMarketing.addSubordinate(clerk2);
		//创建树形层级结构 End
		
		//按照层级结构显示employee
		System.out.println(ceo.toString());
		for(Employee employee : ceo.getSubordinates()){
			System.out.println(employee.toString());
			for(Employee employee2 : employee.getSubordinates())
				System.out.println(employee2.toString());
		}
	}
	
	class Employee{
		private String name,dept;
		private int salary;
		private List<Employee> subordinates;
		/**
		 * @param name
		 * @param dept
		 * @param salary
		 * @param subordinates
		 */
		public Employee(String name, String dept, int salary) {
			super();
			this.name = name;
			this.dept = dept;
			this.salary = salary;
			this.subordinates = new ArrayList<Employee>();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDept() {
			return dept;
		}
		public void setDept(String dept) {
			this.dept = dept;
		}
		public int getSalary() {
			return salary;
		}
		public void setSalary(int salary) {
			this.salary = salary;
		}
		public List<Employee> getSubordinates() {
			return subordinates;
		}
		public void setSubordinates(List<Employee> subordinates) {
			this.subordinates = subordinates;
		}
		public void addSubordinate(Employee employee){
			if(this.subordinates != null){
				this.subordinates.add(employee);
			}
		}
		public void removeSubordinate(Employee employee){
			if(this.subordinates != null){
				this.subordinates.remove(employee);
			}
		}
		public String toString(){
			return ("Employee : [name : "+this.name+", department : "+this.dept+", salary : "+this.salary+"]");
		}
	}
	
}
