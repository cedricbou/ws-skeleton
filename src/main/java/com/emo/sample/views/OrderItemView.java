package com.emo.sample.views;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.emo.sample.domain.order.OrderState;
import com.emo.skeleton.annotations.JpaView;
import com.emo.skeleton.annotations.ViewCriteria;

@JpaView("select new com.emo.sample.views.OrderItemView(o.orderCode, o.state, l.item, l.price.price) from Order o join o.lines l where o.orderCode = :OrderCode")
@ViewCriteria("OrderCode")
@Component
public class OrderItemView implements Serializable {

	private static final long serialVersionUID = -787151415531739583L;

	private String orderCode;
	
	private String state;
	
	private String item;
	
	private float price;
	
	public OrderItemView(final String orderCode, final OrderState state, final String item, final float price) {
		this.orderCode = orderCode;
		this.state = state.name();
		this.item = item;
		this.price = price;
	}
	
	protected OrderItemView() { }

	public String getOrderCode() {
		return orderCode;
	}

	public String getState() {
		return state;
	}

	public String getItem() {
		return item;
	}

	public float getPrice() {
		return price;
	}
}
