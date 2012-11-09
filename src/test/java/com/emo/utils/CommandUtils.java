package com.emo.utils;

import java.util.UUID;

import com.emo.sample.commands.ClientIsMoving;
import com.emo.sample.commands.NewClient;

public class CommandUtils {

	public static ClientIsMoving randomClientIsMovingCommand() {
		final String uuid = UUID.randomUUID().toString();
		return new ClientIsMoving(uuid, 
				"23 rue " + uuid, 
				uuid.substring(5, 10 + (int)(Math.random() * 6)),
				(int)(10000 + (Math.random() * 80000)),
				uuid.substring(1, 4));
	}

	public static NewClient randomNewClientCommand() {
		final String uuid = UUID.randomUUID().toString();
		return new NewClient(uuid, uuid.substring(3, 10),
				"23 rue " + uuid, 
				(int)(10000 + (Math.random() * 80000)),
				uuid.substring(5, 10 + (int)(Math.random() * 6)),
				uuid.substring(1, 4));
	}


}
