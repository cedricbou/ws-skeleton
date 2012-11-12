package com.emo.sample.domain.order;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import com.emo.sample.domain.order.event.LineAdded;
import com.emo.sample.domain.order.event.OrderOpened;


@Entity
@Table(name = "SalesOrder")
public class Order {

	@Id
	@NotNull
	@NotEmpty
	private String orderCode;
	
	@NotNull
	@NotEmpty
	private String forCustomerCode;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private OrderState state = OrderState.Draft;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn
	@NotNull
	private List<OrderLine> lines = new LinkedList<OrderLine>();

	public void addLine(final String item, final Price price) {
		if( !state.allowModifications ) {
			throw new IllegalStateException("addLine not permited for order in status " + state);
		}
		
		onLineAdded(new LineAdded(nextLineId(), item, price));
	}
	
	public float total() {
		float sum = 0.0f;
		for(final OrderLine line : getLines()) {
			sum += line.getPrice().getPrice();
		}
		return sum;
	}
	
	public void open() {
		if( !state.allowOpening ) {
			throw new IllegalStateException("open not permitted for order in status " + state);
		}
		
		if(getLines().size() == 0) {
			throw new IllegalStateException("open not permitted for order with no lines");
		}
		
		onOrderOpened(new OrderOpened());
	}
		
	/*
	 * ------------ Events --------------------
	 * 
	 */
	private void onLineAdded(final LineAdded event) {
		lines.add(new OrderLine(event.lineId, event.item, event.price));		
	}
	
	private void onOrderOpened(final OrderOpened event) {
		state = OrderState.Open;
	}
	
	
	
	/*
	 * ------------ getters -------------------
	 * 
	 */
	public String getOrderCode() {
		return orderCode;
	}
	
	public String getForCustomerCode() {
		return forCustomerCode;
	}
	
	public OrderState getState() {
		return state;
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
	
	
	public Order(final String orderCode, final String forCustomerCode) {
		this.orderCode = orderCode;
		this.forCustomerCode = forCustomerCode;
	}
	
	public boolean isDraft() {
		return state == OrderState.Draft;
	}

	public boolean isOpen() {
		return state == OrderState.Open;
	}

	
	protected Order() {
		
	}
	
}
