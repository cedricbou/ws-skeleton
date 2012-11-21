package com.emo.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.net.MalformedURLException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.emo.sample.commands.ClientIsMoving;
import com.emo.sample.commands.NewClient;
import com.emo.skeleton.api.CommandApi;
import com.emo.utils.CommandUtils;
import com.emo.utils.EmbeddedServer;

public class ClientIsMovingTest {

	private static CommandApi api;

	private static final EmbeddedServer serverManager = new EmbeddedServer();

	private static final NewClient newClient = new NewClient("1234", "toto", "1 rue de machin", 59000, "Lille", "FRA");

	@BeforeClass
	public static void startServer() {
		serverManager.startServer();
		
		final String url = "http://localhost:8080/hessian/commands";

		final HessianProxyFactory factory = new HessianProxyFactory();
		try {
			api = (CommandApi)factory.create(CommandApi.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		api.processCommand(newClient);
	}
	
	@AfterClass
	public static void stopServer() {
		serverManager.stopServer();
	}

	private final ClientIsMoving clientIsMoving = new ClientIsMoving("1234", "4 rue de machin", "Lille", 59000, "FRA");
	
	@Test
	public void clientIsMovingWithHessian() {
		api.processCommand(clientIsMoving);
	}
	
	@Test
	public void clientIsMovingTwiceWithHessian() {
		final ClientIsMoving onceAgain = CommandUtils.randomClientIsMovingCommand();
		onceAgain.setClientCode(clientIsMoving.getClientCode());

		api.processCommands(new Object[] {
				clientIsMoving,
				onceAgain
		} );
	}
	
	@Test
	public void clientIsMovingWithREST() {
		given().content(JSONValue.toJSONString(clientIsMoving)).expect()
		.body(equalTo("ok")).when().post("/rest/commands/ClientIsMoving");

	}
	
	@Test
	public void clientIsMovingTwiceWithREST() {
		final JSONObject cmd1 = (JSONObject)JSONValue.parse(JSONValue.toJSONString(clientIsMoving));
		final ClientIsMoving onceAgain = CommandUtils.randomClientIsMovingCommand();
		onceAgain.setClientCode(clientIsMoving.getClientCode());
		final JSONObject cmd2 = (JSONObject)JSONValue.parse(JSONValue.toJSONString(onceAgain));
		
		final JSONObject cmdHolder1 = new JSONObject();
		cmdHolder1.put("type", "ClientIsMoving");
		cmdHolder1.put("command", cmd1);
		
		final JSONObject cmdHolder2 = new JSONObject();
		cmdHolder2.put("type", "ClientIsMoving");
		cmdHolder2.put("command", cmd2);

		final JSONArray commands = new JSONArray();
		commands.add(cmdHolder1);
		commands.add(cmdHolder2);
		
		given().content(commands.toJSONString()).expect()
		.body(equalTo("ok")).when().post("/rest/commands/batch");
	}

}
