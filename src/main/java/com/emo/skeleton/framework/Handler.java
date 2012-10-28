package com.emo.skeleton.framework;


public interface Handler<C> {

	public void handle(C command);
}
