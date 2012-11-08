package com.emo.skeleton.web;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.caucho.hessian.server.HessianServlet;
import com.emo.skeleton.api.CommandApi;
import com.emo.skeleton.framework.CommandDispatcher;

@SuppressWarnings("serial")
public class CommandHessianServlet extends HessianServlet implements CommandApi {

	public CommandHessianServlet() {
		super();
		ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	}
	
	private final ApplicationContext ctx;
	
	public CommandHessianServlet(final ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
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