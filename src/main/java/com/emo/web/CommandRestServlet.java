package com.emo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONValue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emo.framework.CommandDispatcher;
import com.emo.framework.CommandManager;

@SuppressWarnings("serial")
@WebServlet("/commands/rest/**")
public class CommandRestServlet extends HttpServlet {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-init.xml");
	
	private CommandDispatcher app() {
		return ctx.getBean(CommandDispatcher.class);
	}
	
	private CommandManager manager() {
		return ctx.getBean(CommandManager.class);
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		System.out.println(req.getRequestURI());
		
		final String uri = req.getRequestURI();
		
		String commandName = uri.substring(uri.lastIndexOf("/")).replace("/", "");
		
		final Class<?> commandType = manager().getTypeFor(commandName);
		
		final Object command = JSONValue.parse(req.getInputStream(), commandType);
		
		app().processCommand(command);
		
		res.setContentType("text/plain;charset=UTF-8");
        final PrintWriter out = res.getWriter();
        try {
            out.print("ok");
        } 
        finally {
        	out.close();
        }
	}
}