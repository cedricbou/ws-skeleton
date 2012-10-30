package com.emo.sample.commands;

import java.io.Serializable;

public class NewClient implements Serializable {

	private static final long serialVersionUID = 5555626240903469544L;

	private String clientCode;
	private String name;
	private String street;
	private String city;
	private int zip;
	private String countryCode;

	public NewClient(final String customerCode, final String name,
			final String street, final int zip, final String city,
			final String countryCode) {
		this.clientCode = customerCode;
		this.name = name;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.countryCode = countryCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public int getZip() {
		return zip;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public NewClient() {

	}

}
