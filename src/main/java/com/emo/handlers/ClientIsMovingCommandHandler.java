package com.emo.handlers;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Component;

import com.emo.annotations.CommandHandler;
import com.emo.api.commands.ClientIsMovingCommand;

@CommandHandler(ClientIsMovingCommand.class)
@Component
public class ClientIsMovingCommandHandler implements Handler<ClientIsMovingCommand> {

	@Override
	public void handle(ClientIsMovingCommand command) {
		System.out.println(">>> PROCESSING <<< " + ReflectionToStringBuilder.toString(command));
	}
	
}