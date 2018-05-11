package org.intuit.customersales.entity;

import java.io.Serializable;
/**
 * Entity class
 * @author lakshmimahabaleshwara
 *
 */
public class Customer implements Serializable{
	
	private static final long serialVersionUID = -8474991789660180859L;
	
	public Customer(){}
	
	public Customer(String state) {
		this.state = state;
	}
	
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
