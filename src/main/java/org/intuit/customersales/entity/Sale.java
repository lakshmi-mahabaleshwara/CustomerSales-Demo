package org.intuit.customersales.entity;

import java.io.Serializable;

/**
 * Entity class
 * 
 * @author lakshmimahabaleshwara
 *
 */
public class Sale implements Serializable {

	private static final long serialVersionUID = -8912886792021896369L;
	private Long timeStamp;
	private String customerId;
	private Long salesPrice;

	public Sale() {
	}

	public Sale(String customerId, Long timeStamp, Long salesPrice) {
		this.customerId = customerId;
		this.timeStamp = timeStamp;
		this.salesPrice = salesPrice;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}

}
