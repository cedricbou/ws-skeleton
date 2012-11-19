package com.emo.sample.handlers.account;

import javax.inject.Inject;

import junit.framework.Assert;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emo.sample.commands.account.EcrireDepotAuJournal;
import com.emo.sample.domain.account.Journal;
import com.emo.sample.domain.account.JournalRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(EcrireDepotAuJournal.class)
@Component
public class EcrireDepotAuJournalHandler implements Handler<EcrireDepotAuJournal>{

	@Inject
	private JournalRepository repo;
	
	@Transactional
	public void handle(EcrireDepotAuJournal command) {
		final Journal journal = repo.findByForAccountName(command.getForAccountName());
		Assert.assertNotNull(journal);
		
		journal.ecrireDepot(command.getSomme(), command.getSolde());
	};
}
