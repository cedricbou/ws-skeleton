package com.emo.skeleton.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emo.skeleton.framework.CommandDispatcher;
import com.emo.skeleton.framework.CommandManager;

@Controller
@RequestMapping("/commands")
public class ProcessCommandController {

	@Inject
	private CommandDispatcher app;
	
	@Inject
	private CommandManager manager;
	
	@RequestMapping(value = "/{commandName}", method = RequestMethod.POST)
	@ResponseBody
	public final String processCommand(final @PathVariable("commandName") String commandName, final HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		final Class<?> commandType = manager.getTypeFor(commandName);

		final ObjectMapper mapper = new ObjectMapper();
		final Object command = mapper.readValue(req.getInputStream(), commandType);

		app.processCommand(command);

		return "ok";
	}

	
	private static class CommandHolder {
		public String type;
		public JsonNode command;
	}
	
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	@ResponseBody
	protected final String processCommands(HttpServletRequest req)
			throws IOException {
		
		final ObjectMapper mapper = new ObjectMapper();
		final List<CommandHolder> holders = mapper.readValue(req.getInputStream(), new TypeReference<List<CommandHolder>>() {});
				
		final Iterator<CommandHolder> it = holders.iterator();
		
		while(it.hasNext()) {
			CommandHolder commandHolder = it.next();
			final Class<?> commandType = manager.getTypeFor(commandHolder.type);
			final Object command = mapper.readValue(commandHolder.command, commandType);
			app.processCommand(command);
		}

		return "ok";
	}

	
	@RequestMapping(value = "/{commandName}", method = RequestMethod.GET)
	@ResponseBody
	public final List<String> displayCommand(final @PathVariable("commandName") String commandName) {
		final Class<?> commandType = manager.getTypeFor(commandName);

		final Method[] methods = commandType.getMethods();

		final List<String> methodNames = new LinkedList<String>(); 
		
		for (final Method method : methods) {
			if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
				methodNames.add(method.getName().replaceAll("^get", "") + " : " + method.getReturnType().getSimpleName());
			}
		}
		
		return methodNames;
	}
	
	
}
