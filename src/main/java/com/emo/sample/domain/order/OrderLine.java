package com.emo.sample.domain.order;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long internalId;
	
	private int lineId;
	
	private String item;
	
	@Embedded
	private Price price;
	
	public int getLineId() {
		return lineId;
	}
	
	public String getItem() {
		return item;
	}

	public Price getPrice() {
		return price;
	}

	protected OrderLine(final int lineId, final String item, final Price price) {
		this.lineId = lineId;
		this.item = item;
		this.price = price;
	}
	
	protected OrderLine() {
		
	}
}
