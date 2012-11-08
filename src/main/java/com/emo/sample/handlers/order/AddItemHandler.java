package com.emo.sample.handlers.order;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emo.sample.commands.order.AddItem;
import com.emo.sample.domain.order.Order;
import com.emo.sample.domain.order.OrderRepository;
import com.emo.sample.domain.order.Price;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(AddItem.class)
@Component
public class AddItemHandler implements Handler<AddItem> {

	@Inject
	private OrderRepository repo;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW /* TODO :, isolation = Isolation.SERIALIZABLE */)
	public void handle(AddItem addItem) {
		final Order order = repo.findByOrderCode(addItem.getOrderCode());

		if (null == order) {
			throw new IllegalArgumentException(
					"no order exists with the code : " + addItem.getOrderCode());
		}
		
		order.addLine(addItem.getLabel(), new Price(addItem.getPrice()));
	}

}
