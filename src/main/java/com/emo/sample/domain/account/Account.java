package com.emo.sample.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

	public Account(final String name) {
		this.name = name;
	}
	
	protected Account() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	private String name;
	
	private float solde = 0.0f;
	
	public void depot(final float somme) {
		this.solde += somme;
	}
	
	public void retrait(final float somme) {
		this.solde -= somme;
	}
	
	public float getSolde() {
		return this.solde;
	}
}
