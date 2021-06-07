package com.reactive.EmployeeApp.model;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

//@Table("emp_skill")
public class EmployeeSkill implements Serializable {
	
	private int emp_id;
	private double java_exp;
	private double spring_exp;
	
	public EmployeeSkill() {
		
	}
	
	public EmployeeSkill(int emp_id, double java_exp, double spring_exp) {
		super();
		this.emp_id = emp_id;
		this.java_exp = java_exp;
		this.spring_exp = spring_exp;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public double getJava_exp() {
		return java_exp;
	}
	public void setJava_exp(double java_exp) {
		this.java_exp = java_exp;
	}
	public double getSpring_exp() {
		return spring_exp;
	}
	public void setSpring_exp(double spring_exp) {
		this.spring_exp = spring_exp;
	}
	
	
	
	

}
