package com.emo.sample.commands;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import com.emo.sample.domain.DomainConstraints;

public class ClientIsMoving implements Serializable {
	private static final long serialVersionUID = 1829816920962034051L;
	
	@NotNull
	@NotEmpty
	private String clientCode;
		
	@NotNull
	@NotEmpty
	@Length(max=DomainConstraints.CLIENT_STREET_MAX_LENGTH)
	private String street;

	@NotNull
	@NotEmpty
	@Length(max=DomainConstraints.CLIENT_CITY_MAX_LENGTH)
	private String city;
	
	private int zip;
	
	@NotNull
	@NotEmpty
	@Length(min=3, max=3)
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