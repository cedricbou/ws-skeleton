package com.emo.acceptance;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.net.MalformedURLException;

import net.minidev.json.JSONValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.emo.api.CommandApi;
import com.emo.api.commands.ClientIsMovingCommand;
import com.emo.utils.CanEmbedAServer;

public class ClientIsMovingTestCase extends CanEmbedAServer {

	private CommandApi api;
	
	@Override
	@Before
	public void startServer() {
		super.startServer();
		
		final String url = "http://localhost:8080/commands/hessian";

		final HessianProxyFactory factory = new HessianProxyFactory();
		try {
			api = (CommandApi)factory.create(CommandApi.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@After
	public void stopServer() {
		super.stopServer();
	}
	
	private final ClientIsMovingCommand clientIsMovingCommand = new ClientIsMovingCommand("1234", "4 rue de machin", "Lille", 59000, "FRA");
	
	@Test
	public void clientIsMovingWithHessian() {
		api.processCommand(clientIsMovingCommand);
	}
	
	@Test
	public void clientIsMovingWithREST() {
		given().content(JSONValue.toJSONString(clientIsMovingCommand)).expect()
		.body(equalTo("ok")).when().post("/commands/rest/ClientIsMovingCommand");

	}
}
