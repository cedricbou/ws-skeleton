package com.emo.sample.commands;

import java.io.Serializable;

public class ClientIsMoving implements Serializable {
	private static final long serialVersionUID = 1829816920962034051L;
	
	private String clientCode;
	private String street;
	private String city;
	private int zip;
	private String countryCode;

	public ClientIsMoving() {
		
	}
	
	public ClientIsMoving(final String clientCode, final String street, final String city, final int zip, final String countryCode) {
		this.clientCode = clientCode;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.countryCode = countryCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
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
}