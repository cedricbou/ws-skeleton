package com.emo.sample.commands.account;

import java.io.Serializable;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotNull;

public class Depot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6283174463303145445L;

	@Min(value = 0.0f)
	private float somme;
	
	@NotNull
	@MinLength(3)
	private String accountName;
	
	public Depot(String accountName, float somme) {
		this.somme = somme;
		this.accountName = accountName;
	}
	
	public Depot() {
		
	}

	public float getSomme() {
		return somme;
	}

	public void setSomme(float somme) {
		this.somme = somme;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}
