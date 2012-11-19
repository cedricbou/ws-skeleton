package com.emo.sample.commands.account;

import java.io.Serializable;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotNull;

public class EcrireRetraitAuJournal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1284876631093199904L;

	@NotNull
	@MinLength(3)
	private String forAccountName;
	
	@Min(0.0f)
	private float somme;
	
	private float solde;

	public EcrireRetraitAuJournal(String forAccountName, float somme,
			float solde) {
		super();
		this.forAccountName = forAccountName;
		this.somme = somme;
		this.solde = solde;
	}
	
	public EcrireRetraitAuJournal() {
		
	}

	public String getForAccountName() {
		return forAccountName;
	}

	public float getSomme() {
		return somme;
	}

	public float getSolde() {
		return solde;
	}

	public void setForAccountName(String forAccountName) {
		this.forAccountName = forAccountName;
	}

	public void setSomme(float somme) {
		this.somme = somme;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}
	
	
}
