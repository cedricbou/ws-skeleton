package com.emo.skeleton.api;

public interface CommandApi {

	public void processCommand(final Object command);
	
	public void processCommands(final Object[] commands);
}
