package com.emo.sample.commands.order;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class AddItem implements Serializable {

	private static final long serialVersionUID = -8127284567279526761L;

	@NotNull
	@NotEmpty
	private String orderCode;
	
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String label;
	
	@Min(value=0)
	private float price;

	public AddItem(final String orderCode, final String label, final float price) {
		this.orderCode = orderCode;
		this.label = label;
		this.price = price;
	}

	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public AddItem() {
		
	}
}
