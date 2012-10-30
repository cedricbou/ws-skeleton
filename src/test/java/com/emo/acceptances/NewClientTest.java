package com.emo.acceptances;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emo.sample.commands.NewClient;
import com.emo.sample.domain.Client;
import com.emo.sample.domain.ClientRepository;
import com.emo.skeleton.framework.CommandDispatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-init.xml")
public class NewClientTest {

	@Inject
	private CommandDispatcher api;

	@Inject
	private ClientRepository repo;

	private static final String CLIENT_CODE_1 = "newClient123456";

	private static final String CLIENT_CODE_2 = "newClient1234567";

	@Test
	public void clientCanBeFound() {
		final NewClient command = new NewClient(CLIENT_CODE_1, "Cedric Bou",
				"23 rue du machin", 59000, "Lille", "FRA");
		api.processCommand(command);

		final Client c = repo.findByClientCode(CLIENT_CODE_1);

		assertEquals(c.getCity(), "Lille");
		assertEquals(c.getName(), "Cedric Bou");
	}
	
	@Test
	public void failWhenClientAlreadyExists() {
		final NewClient command = new NewClient(CLIENT_CODE_2, "Cedric Bou",
				"23 rue du machin", 59000, "Lille", "FRA");
		
		api.processCommand(command);

		final NewClient command2 = new NewClient(CLIENT_CODE_2, "Cedric Bou",
				"23 rue du machin", 59000, "Lille", "FRA");

		try {
			api.processCommand(command2);
			fail("should have thrown an exception client already exists");
		}
		catch(IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("client already exists"));
		}
		
	}
}
