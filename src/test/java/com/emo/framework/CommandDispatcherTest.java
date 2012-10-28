package com.emo.framework;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emo.sample.commands.ClientIsMoving;
import com.emo.skeleton.framework.CommandDispatcher;

public class CommandDispatcherTest {

	private final ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext-init.xml");

	@Test
	public void mustProcessCommand() {
		CommandDispatcher dispatcher = ctx.getBean(CommandDispatcher.class);

		dispatcher.processCommand(new ClientIsMoving("123", "5 rue de machin", "Lille",
						59000, "FRA"));

	}
}
