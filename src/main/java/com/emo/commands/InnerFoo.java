package com.emo.commands;

import java.io.Serializable;

import com.emo.skeleton.annotations.Doc;

public class InnerFoo implements Serializable {

	private static final long serialVersionUID = 1811037877627983510L;

	@Doc("and da story for foo bar ?")
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
