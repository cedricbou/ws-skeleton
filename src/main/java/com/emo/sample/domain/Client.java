package com.emo.sample.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
	@Id
	private String clientCode;
	
	private String name;
	
	private String street;
	
	private String city;
	
	private int zip;
	
	private String countryCode;
	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Client(final String clientCode, final String name, final String street, final int zip, final String city, final String countryCode) {
		this.clientCode = clientCode;
		this.name = name;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.countryCode = countryCode;
	}
	
	protected Client() {
		
	}
}
