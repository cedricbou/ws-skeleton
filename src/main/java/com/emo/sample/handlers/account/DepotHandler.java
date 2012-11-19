package com.emo.sample.handlers.account;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.emo.sample.commands.account.Depot;
import com.emo.sample.commands.account.EcrireDepotAuJournal;
import com.emo.sample.domain.account.Account;
import com.emo.sample.domain.account.AccountRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.CommandDispatcher;
import com.emo.skeleton.framework.Handler;

@CommandHandler(Depot.class)
@Component
public class DepotHandler implements Handler<Depot> {

	@Inject
	private AccountRepository repo;

	@Inject
	private CommandDispatcher api;

	@Inject
	private PlatformTransactionManager transactionManager;

	@Override
	public void handle(Depot depot) {
		doDepot(depot);

		final Account account = repo.findByName(depot.getAccountName());
		api.processCommand(new EcrireDepotAuJournal(depot.getAccountName(),
				depot.getSomme(), account.getSolde()));
	}

	private void doDepot(final Depot depot) {
		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallback<Void>() {
					
					@Override
					public Void doInTransaction(TransactionStatus arg0) {
						final Account account = repo.findByName(depot
								.getAccountName());
						Assert.notNull(account);
						account.depot(depot.getSomme());
						
						return null;
					}
				});
	}
}
