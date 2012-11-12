package com.emo.sample.views;

import java.io.Serializable;

public class SummarizedOrderView implements Serializable {

	private static final long serialVersionUID = 4358857591460619973L;

	private String orderCode;
	
	private int numberOfItems;
	
	private float total;
	
	private boolean knownCustomer;

	public SummarizedOrderView(String orderCode, int numberOfItems,
			float total, boolean knownCustomer) {
		this.orderCode = orderCode;
		this.numberOfItems = numberOfItems;
		this.total = total;
		this.knownCustomer = knownCustomer;
	}

	protected SummarizedOrderView() { }
	
	public String getOrderCode() {
		return orderCode;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public float getTotal() {
		return total;
	}

	public boolean isKnownCustomer() {
		return knownCustomer;
	}
}
