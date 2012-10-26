package com.emo.framework;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emo.api.CommandApi;
import com.emo.handlers.Handler;

@Service
public class CommandDispatcher implements CommandApi {

	@Inject
	private HandlerManager manager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void processCommand(Object command) {

		final Handler commandHandler = manager.handlerFor(command.getClass());
		commandHandler.handle(command);
	}

}
