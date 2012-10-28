package com.emo.commands;

import java.io.Serializable;

public class Placebo implements Serializable {

	private static final long serialVersionUID = 6417127022021932063L;

	private String foo;
	private String bar;

	public Placebo() {
		
	}
	
	public Placebo(final String foo, final String bar) {
		this.foo = foo;
		this.bar = bar;
	}
	
	public String getFoo() {
		return foo;
	}
	
	public void setFoo(String foo) {
		this.foo = foo;
	}
	
	public String getBar() {
		return bar;
	}
	
	public void setBar(String bar) {
		this.bar = bar;
	}
	
}
