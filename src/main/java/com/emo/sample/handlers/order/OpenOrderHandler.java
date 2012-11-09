package com.emo.sample.handlers.order;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emo.sample.commands.order.OpenOrder;
import com.emo.sample.domain.order.Order;
import com.emo.sample.domain.order.OrderRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(OpenOrder.class)
@Component
public class OpenOrderHandler implements Handler<OpenOrder> {
	
	@Inject
	private OrderRepository repo;
	
	@Override
	@Transactional
	public void handle(OpenOrder command) {
		final Order order = repo.findByOrderCode(command.getOrderCode());
		if(order == null) {
			throw new IllegalArgumentException("no order found for order code : " + command.getOrderCode());
		}
		
		order.open();
	}
}
