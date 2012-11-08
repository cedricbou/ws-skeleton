package com.emo.commands;

import java.io.Serializable;

public class InnerFoo implements Serializable {

	private static final long serialVersionUID = 1811037877627983510L;

	private String foobar;

	public InnerFoo(final String foobar) {
		this.foobar = foobar;
	}
	
	public String getFoobar() {
		return foobar;
	}

	public void setFoobar(String foobar) {
		this.foobar = foobar;
	}

	public InnerFoo() {
		
	}
}
