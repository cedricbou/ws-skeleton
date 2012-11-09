package com.emo.acceptances;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emo.sample.commands.order.AddItem;
import com.emo.sample.commands.order.NewOrder;
import com.emo.sample.commands.order.OpenOrder;
import com.emo.sample.domain.order.Order;
import com.emo.sample.domain.order.OrderLine;
import com.emo.sample.domain.order.OrderRepository;
import com.emo.skeleton.framework.CommandDispatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-init.xml")
public class NewOrderTest {

	@Inject
	private CommandDispatcher api;

	@Inject
	private OrderRepository repo;

	private static final String ORDER_CODE_1 = UUID.randomUUID().toString();

	private static final String ORDER_CODE_2 = UUID.randomUUID().toString();
	
	private static final String ORDER_CODE_3 = UUID.randomUUID().toString();
	
	private static final String ORDER_CODE_4 = UUID.randomUUID().toString();
	
	private static final String CLIENT_CODE = UUID.randomUUID().toString();

	@Test
	public void orderCanBeFound() {
		final NewOrder newOrder = new NewOrder(ORDER_CODE_2, CLIENT_CODE);
		api.processCommand(newOrder);

		final Order o = repo.findByOrderCode(ORDER_CODE_2);

		assertEquals(0, o.getLines().size());
		assertTrue(o.isDraft());
	}

	@Test
	public void failWhenOrderAlreadyExists() {
		final NewOrder newOrder1 = new NewOrder(ORDER_CODE_1, CLIENT_CODE);
		final NewOrder newOrder2 = new NewOrder(ORDER_CODE_1, CLIENT_CODE);

		api.processCommand(newOrder1);

		try {
			api.processCommand(newOrder2);
			fail("should have thrown an exception order already exists");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("order already exists"));
		}

	}
	
	@Test
	/**
	 * Given a new order
	 * When I add several lines
	 * Then the order contains more lines.
	 */
	public void holdLines() {
		final NewOrder newOrder = new NewOrder(ORDER_CODE_3, CLIENT_CODE);
		api.processCommand(newOrder);
		
		final Order givenOrder = repo.findByOrderCode(ORDER_CODE_3);
		
		assertEquals(0, givenOrder.getLines().size());
		
		final AddItem addBoardGame = new AddItem(ORDER_CODE_3, "boardgame", 3.90f);
		final AddItem addCandies = new AddItem(ORDER_CODE_3, "candy bag", 2.55f);
		final AddItem addTv = new AddItem(ORDER_CODE_3, "led tv", 499.0f);

		api.processCommands(new Object[] {addBoardGame, addCandies, addTv});
		
		final Order modifiedOrder = repo.findByOrderCode(ORDER_CODE_3);
		
		assertEquals(3, modifiedOrder.getLines().size());
		
		for(int i = 0; i < 3; ++i) {
			assertEquals(i + 1, modifiedOrder.getLines().get(i).getLineId());
		}
			
		final OrderLine line = modifiedOrder.getLines().get(modifiedOrder.getLines().size() - 1);
		assertEquals("led tv", line.getItem());
	}
	
	@Test
	/**
	 * Given an order with lines
	 * When I open it
	 * I can pay for the items.
	 */
	public void payLines() {
		final NewOrder newOrder = new NewOrder(ORDER_CODE_4, CLIENT_CODE);
		api.processCommand(newOrder);
		
		final AddItem addBoardGame = new AddItem(ORDER_CODE_4, "boardgame", 3.90f);
		final AddItem addCandies = new AddItem(ORDER_CODE_4, "candy bag", 2.55f);
		final AddItem addTv = new AddItem(ORDER_CODE_4, "led tv", 499.0f);

		api.processCommands(new Object[] {addBoardGame, addCandies, addTv});
		
		final OpenOrder openOrder = new OpenOrder(ORDER_CODE_4);
		
		api.processCommand(openOrder);

		final Order givenOrder = repo.findByOrderCode(ORDER_CODE_4);

		assertTrue(givenOrder.isOpen());
		
		// TODO : accept payment test
	}
}
