package com.emo.sample.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long internalId;
	
	private int lineId;
	
	private String item;
	
	private double price;
	
	public int getLineId() {
		return lineId;
	}
	
	public String getItem() {
		return item;
	}

	public double getPrice() {
		return price;
	}

	public OrderLine(final int lineId, final String item, final double price) {
		this.lineId = lineId;
		this.item = item;
		this.price = price;
	}
	
	protected OrderLine() {
		
	}
}
