package com.emo.framework;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emo.commands.Placebo;
import com.emo.skeleton.framework.CommandDispatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-init.xml")
public class CommandDispatcherTest {
	
	@Inject
	private CommandDispatcher dispatcher;
	
	@Test
	public void mustProcessCommand() {
		dispatcher.processCommand(new Placebo());
	}
}
