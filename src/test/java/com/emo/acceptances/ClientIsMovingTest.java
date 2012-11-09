package com.emo.acceptances;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emo.sample.commands.ClientIsMoving;
import com.emo.sample.commands.NewClient;
import com.emo.sample.domain.Client;
import com.emo.sample.domain.ClientRepository;
import com.emo.skeleton.framework.CommandDispatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-init.xml")
public class ClientIsMovingTest {

	@Inject
	private CommandDispatcher api;

	@Inject
	private ClientRepository repo;

	private static final String CLIENT_CODE_1 = "clientIsMoving123456";
	
	private static final String CLIENT_CODE_2 = "clientIsMoving1234567";

	
	@Test
	public void failIfClientDoesNotExists() {
		ClientIsMoving command = new ClientIsMoving(CLIENT_CODE_2, "20 rue du machin","Lille", 59000, "FRA");
		
		try {
			api.processCommand(command);
			fail("should have failed because client does not exists");
		}
		catch(IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("no client exists"));
		}
	}
	
	@Test
	public void addressChangedOnceClientHasMoved() {
		final long count = repo.count();
		
		final NewClient command = new NewClient(CLIENT_CODE_1, "Cedric Bou",
				"23 rue du machin", 59000, "Lille", "FRA");
		
		api.processCommand(command);

		assertEquals(count + 1, repo.count());
		
		ClientIsMoving command2 = new ClientIsMoving(CLIENT_CODE_1, "111 rue du soleil","Madrid", 28001, "ESN");
		
		api.processCommand(command2);

		assertEquals(count + 1, repo.count());
		
		final Client client = repo.findByClientCode(CLIENT_CODE_1);
		
		assertEquals("111 rue du soleil", client.getStreet());
		assertEquals("Madrid", client.getCity());
		assertEquals(28001, client.getZip());
		assertEquals("ESN", client.getCountryCode());
	}
}
