package com.emo.skeleton.framework;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emo.skeleton.api.CommandApi;

@Service
public class CommandDispatcher implements CommandApi {

	@Inject
	private HandlerManager manager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void processCommand(final Object command) {

		final Handler commandHandler = manager.handlerFor(command.getClass());
		commandHandler.handle(command);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void processCommands(final Object[] commands) {
		// TODO: exception management, should we stop on failure, or ignore and go on ?
		for(final Object command : commands) {
			final Handler commandHandler = manager.handlerFor(command.getClass());
			commandHandler.handle(command);
		}
	}

}
