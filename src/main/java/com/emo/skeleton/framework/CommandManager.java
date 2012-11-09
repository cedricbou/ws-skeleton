package com.emo.skeleton.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CommandManager {
	private final Map<String, Class<?>> commands = new HashMap<String, Class<?>>();
	
	protected void declare(final String name, final Class<?> type) {
		if(name.toLowerCase().endsWith("command")) {
			commands.put(name.toLowerCase(), type);
		}
		else {
			commands.put(name.toLowerCase() + "command", type);
		}
	}
	
	public Class<?> getTypeFor(final String name) {
		final Class<?> command;
		
		if(name.toLowerCase().endsWith("command")) {
			command = commands.get(name.toLowerCase());
		}
		else {
			command = commands.get(name.toLowerCase() + "command");
		}
		
		if(null == command) {
			throw new IllegalArgumentException("no command found with name " + name);
		}
		
		return command;
	}
	
	public Collection<Class<?>> commandClasses() {
		return commands.values();
	}
	
	
}
