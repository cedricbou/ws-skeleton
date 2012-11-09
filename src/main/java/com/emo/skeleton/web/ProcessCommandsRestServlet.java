package com.emo.skeleton.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.emo.skeleton.framework.CommandDispatcher;
import com.emo.skeleton.framework.CommandManager;

@SuppressWarnings("serial")
public class ProcessCommandsRestServlet extends HttpServlet {

	private ApplicationContext ctx;

	@Override
	public void init(javax.servlet.ServletConfig config) throws ServletException {
		ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
	}
	
	private CommandDispatcher app() {
		return ctx.getBean(CommandDispatcher.class);
	}
	
	private CommandManager manager() {
		return ctx.getBean(CommandManager.class);
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		
		final JSONArray commands = (JSONArray)JSONValue.parse(req.getInputStream());
		
		final Iterator<Object >it = commands.iterator();
		
		while(it.hasNext()) {
			JSONObject commandHolder = (JSONObject)it.next();
			final Class<?> commandType = manager().getTypeFor((String)commandHolder.get("type"));
			JSONObject commandAsJSON = (JSONObject)commandHolder.get("command");
			final Object command = JSONValue.parse(commandAsJSON.toJSONString(), commandType);
			app().processCommand(command);
		}
		
		
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