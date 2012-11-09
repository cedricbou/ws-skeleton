package com.emo.skeleton.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.caucho.hessian.server.HessianServlet;
import com.emo.skeleton.api.CommandApi;
import com.emo.skeleton.framework.CommandDispatcher;

@SuppressWarnings("serial")
public class CommandHessianServlet extends HessianServlet implements CommandApi {
	
	@Override
	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);
		ctx = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
	}
	
	private ApplicationContext ctx;
	
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