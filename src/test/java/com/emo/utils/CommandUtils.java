package com.emo.utils;

import java.util.UUID;

import com.emo.sample.commands.ClientIsMoving;

public class CommandUtils {

	public static ClientIsMoving randomClientIsMovingCommand() {
		final String uuid = UUID.randomUUID().toString();
		return new ClientIsMoving(uuid, 
				"23 rue " + uuid, 
				uuid.substring(5, 5 + (int)(Math.random() * 10)),
				(int)(10000 + (Math.random() * 80000)),
				uuid.substring(1, 3));
	}


}
