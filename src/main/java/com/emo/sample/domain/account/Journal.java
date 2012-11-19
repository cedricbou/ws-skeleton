package com.emo.sample.domain.account;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.emo.sample.domain.account.JournalEntry.Operation;

@Entity
public class Journal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String forAccountName;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="journal_id")
	private List<JournalEntry> operations = new LinkedList<JournalEntry>();

	public Journal(final String forAccountName) {
		this.forAccountName = forAccountName;
	}
	
	protected Journal() {
		
	}
	
	public void ecrireDepot(final float somme, final float solde) {
		operations.add(new JournalEntry(Operation.DEPOT, somme, solde));
	}

	public void ecrireRetrait(final float somme, final float solde) {
		operations.add(new JournalEntry(Operation.RETRAIT, somme, solde));
	}

	public String getForAccountName() {
		return forAccountName;
	}
	
	public List<JournalEntry> operations() {
		return Collections.unmodifiableList(this.operations);
	}

}
