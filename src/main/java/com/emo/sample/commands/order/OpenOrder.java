package com.emo.sample.commands.order;

import java.io.Serializable;

public class OpenOrder implements Serializable {
	
	private static final long serialVersionUID = 4438415247942525432L;
	
	private String orderCode;
	
	public OpenOrder(final String orderCode) {
		this.orderCode = orderCode;
	}
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public OpenOrder() {
		
	}
}
