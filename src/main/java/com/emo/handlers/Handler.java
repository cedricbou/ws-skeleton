package com.emo.handlers;


public interface Handler<C> {

	public void handle(C command);
}
