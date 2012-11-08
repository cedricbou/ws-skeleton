package com.emo.skeleton.web.ui;

import java.io.Serializable;

public class Client implements Serializable {
	@Required
	public String name;
	public Country country;
	public boolean status;
	
	@Override
	public String toString()
	{
		return "Client: {name: " + name + "; country: " + country + "; live:" + status + "}";
	}
}
