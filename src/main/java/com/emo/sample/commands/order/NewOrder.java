package com.emo.sample.commands.order;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import com.emo.sample.domain.DomainConstraints;

public class NewOrder implements Serializable {

	private static final long serialVersionUID = -1473206819298467960L;

	@NotNull
	@NotEmpty
	@Length(max = DomainConstraints.ORDER_CODE_MAX_LENGTH)
	private String orderCode;
	
	@NotNull
	@NotEmpty
	private String forClientCode;
	
	public NewOrder() {
		
	}
	
	public NewOrder(final String orderCode, final String forClientCode) {
		this.orderCode = orderCode;
		this.forClientCode = forClientCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getForClientCode() {
		return forClientCode;
	}

	public void setForClientCode(String forClientCode) {
		this.forClientCode = forClientCode;
	}
}
