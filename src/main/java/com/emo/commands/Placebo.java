package com.emo.commands;

import java.io.Serializable;

public class Placebo implements Serializable {

	private static final long serialVersionUID = 6417127022021932063L;

	private InnerFoo foo;
	private String bar;

	public Placebo() {
	}
	
	public Placebo(final InnerFoo foo, final String bar) {
		this.foo = foo;
		this.bar = bar;
	}
	
	public InnerFoo getFoo() {
		return foo;
	}
	
	public void setFoo(InnerFoo foo) {
		this.foo = foo;
	}
	
	public String getBar() {
		return bar;
	}
	
	public void setBar(String bar) {
		this.bar = bar;
	}
	
}
