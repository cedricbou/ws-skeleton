package com.emo.framework;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emo.api.commands.ClientIsMovingCommand;
import com.emo.handlers.ClientIsMovingCommandHandler;

public class HandlerBeanProcessorTestCase {
	
	private final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-init.xml");

	@Test
	public void checkHandlerProcessed() {
		final List<String> names = Arrays.asList(context.getBeanDefinitionNames());
				
		assertTrue("command handler have been added to context", names.contains("clientIsMovingCommandHandler"));
		
		final HandlerManager manager = (HandlerManager)context.getBean("handlerManager");
		
		assertTrue("handler manager contains handler for ClientIsMovingCommand", manager.handlerFor(ClientIsMovingCommand.class) instanceof ClientIsMovingCommandHandler);
	}
}
