package com.emo.sample.domain.account;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class JournalEntry {

	public static enum Operation {
		DEPOT, RETRAIT;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Operation operation;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate = new Date();
	
	private float somme;
	
	private float solde;
	
	public JournalEntry(Operation op, float somme, float solde) {
		this.operation = op;
		this.somme = somme;
		this.solde = solde;
	}
	
	protected JournalEntry() {
		
	}

	public long getId() {
		return id;
	}

	public Operation getOperation() {
		return operation;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public float getSomme() {
		return somme;
	}

	public float getSolde() {
		return solde;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result
				+ ((recordDate == null) ? 0 : recordDate.hashCode());
		result = prime * result + Float.floatToIntBits(solde);
		result = prime * result + Float.floatToIntBits(somme);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JournalEntry other = (JournalEntry) obj;
		if (operation != other.operation)
			return false;
		if (recordDate == null) {
			if (other.recordDate != null)
				return false;
		} else if (!recordDate.equals(other.recordDate))
			return false;
		if (Float.floatToIntBits(solde) != Float.floatToIntBits(other.solde))
			return false;
		if (Float.floatToIntBits(somme) != Float.floatToIntBits(other.somme))
			return false;
		return true;
	}
	
	
	
}
