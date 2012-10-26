package com.emo.framework;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.emo.handlers.Handler;

@Service
public class HandlerManager {

	private Map<Class<?>, Handler<?>> handlers = new HashMap<Class<?>, Handler<?>>();
	
	public Handler<?> handlerFor(final Class<?> type) {
		final Handler<?> handler = handlers.get(type);
		if(handler == null) {
			throw new IllegalArgumentException("no handler declared for command type " + type.getName());
		}
		return handler;
	}
	
	public void declare(final Class<?> type, final Handler<?> handler) {
		handlers.put(type, handler);
	}
		
}
