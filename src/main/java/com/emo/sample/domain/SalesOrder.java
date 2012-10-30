package com.emo.sample.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SalesOrder {

	@Id
	private String orderCode;
	
	private String forCustomerCode;
	
	@OneToMany
	private List<OrderLine> lines = new LinkedList<OrderLine>();

	public void addLine(final String item, final double price) {
		lines.add(new OrderLine(nextLineId(), item, price));
	}
	
	public List<OrderLine> getLines() {
		return lines;
	}
	
	private int nextLineId() {
		int max = 0;
		
		for(final OrderLine line : getLines()) {
			if(line.getLineId() > max) {
				max = line.getLineId();
			}
		}
		
		return max + 1;
	}
	
	public SalesOrder(final String orderCode, final String forCustomerCode) {
		this.orderCode = orderCode;
		this.forCustomerCode = forCustomerCode;
	}
	
	protected SalesOrder() {
		
	}
}
