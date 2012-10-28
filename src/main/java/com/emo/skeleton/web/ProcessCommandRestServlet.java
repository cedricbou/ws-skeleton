package com.emo.skeleton.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONValue;

import org.springframework.context.ApplicationContext;

import com.emo.skeleton.framework.CommandDispatcher;
import com.emo.skeleton.framework.CommandManager;

@SuppressWarnings("serial")
@WebServlet("/commands/rest/**")
public class ProcessCommandRestServlet extends HttpServlet {

	private ApplicationContext ctx = ApplicationContextSingleton.get();

	private CommandDispatcher app() {
		return ctx.getBean(CommandDispatcher.class);
	}

	private CommandManager manager() {
		return ctx.getBean(CommandManager.class);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String uri = req.getRequestURI();

		String commandName = uri.substring(uri.lastIndexOf("/")).replace("/",
				"");

		final Class<?> commandType = manager().getTypeFor(commandName);

		final Object command = JSONValue.parse(req.getInputStream(),
				commandType);

		app().processCommand(command);

		res.setContentType("text/plain;charset=UTF-8");
		final PrintWriter out = res.getWriter();

		try {
			out.print("ok");
		} finally {
			out.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		final String uri = req.getRequestURI();

		String commandName = uri.substring(uri.lastIndexOf("/")).replace("/",
				"");

		final Class<?> commandType = manager().getTypeFor(commandName);

		final Method[] methods = commandType.getMethods();

		res.setContentType("text/plain;charset=UTF-8");
		final PrintWriter out = res.getWriter();

		try {
			for (final Method method : methods) {
				if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
					out.println(method.getName().replaceAll("^get", "") + " : " + method.getReturnType().getSimpleName());
				}
			}
		} finally {
			out.close();
		}
	}
}