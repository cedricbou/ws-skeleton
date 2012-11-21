package com.emo.mains;

import com.emo.utils.EmbeddedServer;

public class WebServer {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String args[]) throws InterruptedException {
		final EmbeddedServer server = new EmbeddedServer();
		server.startServer();
		server.awaitServer();
	}

}
