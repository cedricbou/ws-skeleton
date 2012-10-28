package com.emo.skeleton.web;

import org.springframework.context.ApplicationContext;

import com.caucho.hessian.server.HessianServlet;
import com.emo.skeleton.api.CommandApi;
import com.emo.skeleton.framework.CommandDispatcher;

@SuppressWarnings("serial")
public class CommandHessianServlet extends HessianServlet implements CommandApi {

	private ApplicationContext ctx = ApplicationContextSingleton.get();
	
	private CommandDispatcher app() {
		return ctx.getBean(CommandDispatcher.class);
	}
	
	@Override
	public void processCommand(final Object command) {
		app().processCommand(command);	
	}
	
	@Override
	public void processCommands(final Object[] commands) {
		app().processCommands(commands);
	}
	
}