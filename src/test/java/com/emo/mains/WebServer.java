package com.emo.mains;

import org.junit.Test;

import com.emo.utils.EmbeddedServer;

public class WebServer {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	@Test(timeout=300000)
	public void server() throws InterruptedException {
		final EmbeddedServer server = new EmbeddedServer();
		server.startServer();
		server.awaitServer();
	}

}
