package com.emo.acceptances;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.emo.sample.commands.account.Depot;
import com.emo.sample.commands.account.Retrait;
import com.emo.sample.domain.account.Account;
import com.emo.sample.domain.account.AccountRepository;
import com.emo.sample.domain.account.Journal;
import com.emo.sample.domain.account.JournalEntry.Operation;
import com.emo.sample.domain.account.JournalRepository;
import com.emo.sample.views.account.JournalView;
import com.emo.skeleton.framework.CommandDispatcher;
import com.emo.skeleton.framework.ViewManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-init.xml")
public class AccountTest {

	@Inject
	private AccountRepository accounts;
	
	@Inject
	private JournalRepository journals;

	@Inject
	private CommandDispatcher api;
	
	@Inject
	private ViewManager viewManager;
	
	private final static String ACCOUNT_NAME = "test-account-0";
	
	@Test
	public void depotTest() {
		final Account accountBefore = accounts.findByName(ACCOUNT_NAME);
		
		final float solde = accountBefore.getSolde();

		api.processCommand(new Depot(ACCOUNT_NAME, 11.1f));
		
		final Account accountAfter = accounts.findByName(ACCOUNT_NAME);
		
		assertTrue(solde + 11.1f == accountAfter.getSolde());
	}

	@Test
	public void retraitTest() {
		final Account accountBefore = accounts.findByName(ACCOUNT_NAME);
		
		final float solde = accountBefore.getSolde();

		api.processCommand(new Retrait(ACCOUNT_NAME, 11.1f));
		
		final Account accountAfter = accounts.findByName(ACCOUNT_NAME);
		
		assertTrue(solde - 11.1f == accountAfter.getSolde());
	}
	
	@Test
	@Transactional()
	public void operationAreRecordedInJournalTest() {
		final Account accountBefore = accounts.findByName(ACCOUNT_NAME);
		final Journal journalBefore = journals.findByForAccountName(ACCOUNT_NAME);
		
		final float solde = accountBefore.getSolde();
		final int size = journalBefore.operations().size();

		api.processCommand(new Depot(ACCOUNT_NAME, 100.0f));
		api.processCommand(new Retrait(ACCOUNT_NAME, 11.1f));
		api.processCommand(new Retrait(ACCOUNT_NAME, 21.1f));
		api.processCommand(new Depot(ACCOUNT_NAME, 31.1f));
		api.processCommand(new Retrait(ACCOUNT_NAME, 41.1f));

		final Account accountAfter = accounts.findByName(ACCOUNT_NAME);

		assertTrue(solde + 100.0f - 11.1f - 21.1f + 31.1f - 41.1f == accountAfter.getSolde());

		final Journal journalAfter = journals.findByForAccountName(ACCOUNT_NAME);
				
		assertEquals(Operation.RETRAIT, journalAfter.operations().get(size + 1).getOperation());
		assertTrue(11.1f == journalAfter.operations().get(size + 1).getSomme());
		assertTrue(solde + 100.0f - 11.1f == journalAfter.operations().get(size + 1).getSolde());

		assertEquals(Operation.RETRAIT, journalAfter.operations().get(size + 2).getOperation());
	
		assertEquals(Operation.DEPOT, journalAfter.operations().get(size + 3).getOperation());
		assertTrue(31.1f == journalAfter.operations().get(size + 3).getSomme());
		assertTrue(solde + 100.0f - 11.1f - 21.1f + 31.1f == journalAfter.operations().get(size + 3).getSolde());
		
		assertTrue(size + 5 == journalAfter.operations().size());
	}
	
	@Test
	@Transactional
	public void journalViewTest() {
		final Account accountBefore = accounts.findByName(ACCOUNT_NAME);
		final Journal journalBefore = journals.findByForAccountName(ACCOUNT_NAME);
		
		final float solde = accountBefore.getSolde();
		final int size = journalBefore.operations().size();

		api.processCommand(new Depot(ACCOUNT_NAME, 100.0f));
		api.processCommand(new Retrait(ACCOUNT_NAME, 11.1f));
		api.processCommand(new Retrait(ACCOUNT_NAME, 21.1f));
		api.processCommand(new Depot(ACCOUNT_NAME, 31.1f));
		api.processCommand(new Retrait(ACCOUNT_NAME, 41.1f));

		final Account accountAfter = accounts.findByName(ACCOUNT_NAME);

		assertTrue(solde + 100.0f - 11.1f - 21.1f + 31.1f - 41.1f == accountAfter.getSolde());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AccountName", ACCOUNT_NAME);

		@SuppressWarnings("unchecked")
		List<JournalView> operations = (List<JournalView>)viewManager.execute("journalView", params);
		
		final int newSize = size + 5;
		
		assertEquals(newSize, operations.size());
		
		assertEquals("RETRAIT", operations.get(size + 1).getOperation());
		assertTrue(11.1f == operations.get(size + 1).getSomme());
		assertTrue(solde + 100.0f - 11.1f == operations.get(size + 1).getSolde());
	}
}
