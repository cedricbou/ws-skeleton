package com.emo.sample.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, String> {

	public Order findByOrderCode(final String orderCode);
}
