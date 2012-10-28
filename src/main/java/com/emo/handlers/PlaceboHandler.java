package com.emo.handlers;

import org.springframework.stereotype.Component;

import com.emo.commands.Placebo;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(Placebo.class)
@Component
public class PlaceboHandler implements Handler<Placebo> {
 
	public void handle(Placebo command) {};
}
