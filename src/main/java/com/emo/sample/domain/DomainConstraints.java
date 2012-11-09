package com.emo.sample.domain;

public class DomainConstraints {

	private static final int UUID_CODE_LENGTH = 60;
	
	public static final int CLIENT_CODE_MAX_LENGTH = UUID_CODE_LENGTH;
	
	public static final int CLIENT_NAME_MAX_LENGTH = 60;
	
	public static final int CLIENT_STREET_MAX_LENGTH = 255;

	public static final int CLIENT_CITY_MAX_LENGTH = 255;
	
	public static final int ORDER_CODE_MAX_LENGTH = UUID_CODE_LENGTH;
}
