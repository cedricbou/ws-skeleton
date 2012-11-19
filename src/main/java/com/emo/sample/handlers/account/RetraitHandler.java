package com.emo.sample.handlers.account;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.emo.sample.commands.account.EcrireRetraitAuJournal;
import com.emo.sample.commands.account.Retrait;
import com.emo.sample.domain.account.Account;
import com.emo.sample.domain.account.AccountRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.CommandDispatcher;
import com.emo.skeleton.framework.Handler;

@CommandHandler(Retrait.class)
@Component
public class RetraitHandler implements Handler<Retrait>{

	@Inject
	private AccountRepository repo;
	
	@Inject
	private CommandDispatcher api;
	
	@Inject
	private PlatformTransactionManager transactionManager;
	
	@Override
	public void handle(Retrait retrait) {
		doRetrait(retrait);
		
		final Account account = repo.findByName(retrait.getAccountName());
		api.processCommand(new EcrireRetraitAuJournal(retrait.getAccountName(), retrait.getSomme(), account.getSolde()));
	}
	
	private void doRetrait(final Retrait retrait) {
		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallback<Void>() {
					
					@Override
					public Void doInTransaction(TransactionStatus status) {
						final Account account = repo.findByName(retrait
								.getAccountName());
						Assert.notNull(account);
						account.retrait(retrait.getSomme());
						
						return null;
					}
				});
	}
}
