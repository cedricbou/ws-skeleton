package com.emo.sample.views;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.emo.skeleton.annotations.JpaView;
import com.emo.skeleton.annotations.ViewCriteria;

@JpaView("select new com.emo.sample.views.OrderView(o.orderCode, c.name, SUM(i.price.price)) from Order o join o.lines i, Client c where o.forCustomerCode = c.clientCode and o.orderCode = :OrderCode group by o.orderCode, c.name")
@ViewCriteria({"OrderCode"})
@Component
public class OrderView implements Serializable {

	private static final long serialVersionUID = -3306171016286320117L;

	private String orderCode;
	private String customerName;
	private double total;
	
	public OrderView(String orderCode, String customerName, double total) {
		this.orderCode = orderCode;
		this.customerName = customerName;
		this.total = total;
	}
	
	protected OrderView() { }
	
	public String getOrderCode() {
		return orderCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public double getTotal() {
		return total;
	}
}
