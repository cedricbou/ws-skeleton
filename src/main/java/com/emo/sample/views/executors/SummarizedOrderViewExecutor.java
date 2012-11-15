package com.emo.sample.views.executors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emo.sample.domain.ClientRepository;
import com.emo.sample.domain.order.Order;
import com.emo.sample.domain.order.OrderRepository;
import com.emo.sample.views.SummarizedOrderView;
import com.emo.skeleton.annotations.CustomView;
import com.emo.skeleton.annotations.ViewCriteria;
import com.emo.skeleton.framework.ViewExecutor;

@CustomView(SummarizedOrderView.class)
@ViewCriteria({"OrderCode"})
@Component
public class SummarizedOrderViewExecutor implements ViewExecutor<SummarizedOrderView> {

	@Inject
	private OrderRepository orders;
	
	@Inject
	private ClientRepository clients;
	
	@Override
	@Transactional
	public List<SummarizedOrderView> executeView(final Map<String, Object> params) {
		final Order order = orders.findByOrderCode(params.get("OrderCode").toString());
		
		if(order != null) {
		boolean clientExists = false;
		if(null != order.getForCustomerCode()) {
			clientExists = clients.exists(order.getForCustomerCode());
		}
		
		return Arrays.asList(new SummarizedOrderView(order.getOrderCode(), order.getLines().size(), order.total(), clientExists));
		}
		else {
			return Arrays.asList(new SummarizedOrderView[] {});
		}
	}
}
