package com.emo.sample.views.account;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.emo.sample.domain.account.JournalEntry.Operation;
import com.emo.skeleton.annotations.JpaView;
import com.emo.skeleton.annotations.ViewCriteria;

@JpaView("select new com.emo.sample.views.account.JournalView(o.recordDate, o.operation, o.somme, o.solde) from Journal j join j.operations o where j.forAccountName = :AccountName")
@ViewCriteria("AccountName")
@Component
public class JournalView {
	
	private Date recordDate;
	
	private String operation;
	
	private float somme;
	
	private float solde;

	public JournalView(Date recordDate, Operation operation, float somme,
			float solde) {
		super();
		this.recordDate = recordDate;
		this.operation = operation.name();
		this.somme = somme;
		this.solde = solde;
	}
	
	protected JournalView() {
		
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public String getOperation() {
		return operation;
	}

	public float getSomme() {
		return somme;
	}

	public float getSolde() {
		return solde;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setSomme(float somme) {
		this.somme = somme;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}
}
