package com.emo.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.caucho.hessian.server.HessianServlet;
import com.emo.api.CommandApi;
import com.emo.framework.CommandDispatcher;

@SuppressWarnings("serial")
public class CommandHessianServlet extends HessianServlet implements CommandApi {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-init.xml");
	
	private CommandDispatcher app() {
		return ctx.getBean(CommandDispatcher.class);
	}
	
	@Override
	public void processCommand(final Object command) {
		app().processCommand(command);	
	}
	
}