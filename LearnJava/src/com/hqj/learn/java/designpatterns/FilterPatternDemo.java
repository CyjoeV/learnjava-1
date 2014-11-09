/**
 * 2014 Nov 9, 2014
 */
package com.hqj.learn.java.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hqj
 * Version : 1.0
 * Description : 属于结构模式Structural Pattern。演示filter模式。该模式是用于根据规则过滤类。
 * 可以通过这个模式按照规则过滤类。通过不同的排序规则类实现相同的排序操作接口来实现对对象按照不同的规则排序。
 */
public class FilterPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FilterPatternDemo fpd = new FilterPatternDemo();
		fpd.demo();
	}
	
	public void demo(){
		List<Person> persons = new ArrayList<Person>();
		
		persons.add(new Person("Jack Jones","male","single"));
		persons.add(new Person("Marry Jones","female","single"));
		persons.add(new Person("Astrid Shaka","female","married"));
		persons.add(new Person("shandepe Shaka","male","married"));
		persons.add(new Person("shandepe Shaka","male","married"));
		persons.add(new Person("masayuki mitsukitani","male","married"));
		persons.add(new Person("masayuki okawa","male","single"));
		persons.add(new Person("yuki kin","female","single"));
		persons.add(new Person("Sarah Wang","male","married"));
		
		//过滤出接了婚的人
		Criteria criteriaMarried = new CriteriaMarried();
		System.out.println("滤出接了婚的人");
		showPersons(criteriaMarried.meetCriteria(persons));
		
		//过滤出女性
		Criteria criteriaFemale = new CriteriaFemale();
		System.out.println("过滤出女性");
		showPersons(criteriaFemale.meetCriteria(persons));
		
		//过滤出结了婚的女性
		Criteria criteriaAND = new CriteriaAnd(criteriaMarried,criteriaFemale);
		System.out.println("过滤出结了婚的女性");
		showPersons(criteriaAND.meetCriteria(persons));
		
		//过滤出男性或者单身的人
		Criteria criteriaOR = new CriteriaOR(new CriteriaSingle(),new CriteriaMale());
		System.out.println("过滤出男性或者单身的人");
		showPersons(criteriaOR.meetCriteria(persons));
	}
	
	public void showPersons(List<Person> persons){
		for(Person person : persons){
			System.out.println("Person : [name `"+person.name+"` gender `"+person.gender+"` martialStatus `"+person.martialStatus+"`]");
		}
	}
	
	interface Criteria{
		public List<Person> meetCriteria(List<Person> persons);
	}
	
	class CriteriaMale implements Criteria{
		public List<Person> meetCriteria(List<Person> persons){
			List<Person> malePersons = new ArrayList<Person>();
			for(Person person : persons){
				if("male".equalsIgnoreCase(person.getGender()))
				malePersons.add(person);
			}
			return malePersons;
		}
	}

	class CriteriaFemale implements Criteria{
		public List<Person> meetCriteria(List<Person> persons){
			List<Person> femalePersons = new ArrayList<Person>();
			for(Person person : persons){
				if("female".equalsIgnoreCase(person.getGender()))
					femalePersons.add(person);
			}
			return femalePersons;
		}
	}
	
	class CriteriaMarried implements Criteria{
		public List<Person> meetCriteria(List<Person> persons){
			List<Person> marriedPersons = new ArrayList<Person>();
			for(Person person : persons){
				if("married".equalsIgnoreCase(person.getMartialStatus()))
					marriedPersons.add(person);
			}
			return marriedPersons;
		}
	}

	class CriteriaSingle implements Criteria{
		public List<Person> meetCriteria(List<Person> persons){
			List<Person> singlePersons = new ArrayList<Person>();
			for(Person person : persons){
				if("single".equalsIgnoreCase(person.getMartialStatus()))
					singlePersons.add(person);
			}
			return singlePersons;
		}
	}	
	
	class CriteriaAnd implements Criteria{
		
		Criteria firstCriteria,otherCriteria;
		
		CriteriaAnd(Criteria firstCriteria,Criteria otherCriteria){
			this.firstCriteria = firstCriteria;
			this.otherCriteria = otherCriteria;
		}
		
		public List<Person> meetCriteria(List<Person> persons){
			List<Person> firstCriteriaPersons = this.firstCriteria.meetCriteria(persons);
			List<Person> otherCriteriaPersons = this.otherCriteria.meetCriteria(firstCriteriaPersons);
			for(int i=0;i<otherCriteriaPersons.size();i++){
				//如果该人在第一个条件的返回集合中没有，则也从第二各条件的人中删除。从而实现AND操作。
				if(!firstCriteriaPersons.contains(otherCriteriaPersons.get(i))){
					otherCriteriaPersons.remove(i);
					i--;//因为ArrayList的remove方法删除该下标元素后，会将后面的元素前移一位，填补空缺。所以需要将当前下表减1.
				}
			}
			return otherCriteriaPersons;
		}
	}
	
	class CriteriaOR implements Criteria{
		
		Criteria firstCriteria,otherCriteria;
		
		CriteriaOR(Criteria firstCriteria,Criteria otherCriteria){
			this.firstCriteria = firstCriteria;
			this.otherCriteria = otherCriteria;
		}
		
		public List<Person> meetCriteria(List<Person> persons){
			List<Person> firstCriteriaPersons = this.firstCriteria.meetCriteria(persons);
			List<Person> otherCriteriaPersons = this.otherCriteria.meetCriteria(firstCriteriaPersons);
			for(Person person: otherCriteriaPersons){
				//将符合条件2目前又不在符合条件1的人也加到符合条件1的人中，这样得到合集，就是OR操作。
				if(!firstCriteriaPersons.contains(persons)){
					firstCriteriaPersons.add(person);
				}
			}
			return firstCriteriaPersons;
		}
	}
	
	class Person{
		private String name,gender,martialStatus;
		
		Person(String name,String gender,String martialStatus){
			this.name=name;
			this.gender=gender;
			this.martialStatus = martialStatus;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getMartialStatus() {
			return martialStatus;
		}

		public void setMartialStatus(String martialStatus) {
			this.martialStatus = martialStatus;
		}
	}
}
