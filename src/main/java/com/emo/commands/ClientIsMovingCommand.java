package com.emo.commands;

public class ClientIsMovingCommand {
	public final String clientCode;
	public final String street;
	public final String city;
	public final int zip;
	public final String countryCode;

	public ClientIsMovingCommand(final String clientCode, final String street, final String city, final int zip, final String countryCode) {
		this.clientCode = clientCode;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.countryCode = countryCode;
	}
}