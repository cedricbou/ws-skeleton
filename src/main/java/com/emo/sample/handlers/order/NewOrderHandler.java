package com.emo.sample.handlers.order;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.emo.sample.commands.order.NewOrder;
import com.emo.sample.domain.order.Order;
import com.emo.sample.domain.order.OrderRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(NewOrder.class)
@Component
public class NewOrderHandler implements Handler<NewOrder> {

	@Inject
	private OrderRepository repo;
	
	public void handle(NewOrder newOrder) {
		assertOrderDoesNotExist(newOrder.getOrderCode());
		
		final Order order = new Order(newOrder.getOrderCode(), newOrder.getForClientCode());
		repo.save(order);
	}
	
	private void assertOrderDoesNotExist(final String orderCode) {
		if(repo.exists(orderCode)) {
			throw new IllegalArgumentException("order already exists with this order code : " + orderCode);
		}
	}
	
}
