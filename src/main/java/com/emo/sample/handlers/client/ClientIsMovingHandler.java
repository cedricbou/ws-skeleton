package com.emo.sample.handlers.client;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emo.sample.commands.ClientIsMoving;
import com.emo.sample.domain.Client;
import com.emo.sample.domain.ClientRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(ClientIsMoving.class)
@Component
public class ClientIsMovingHandler implements Handler<ClientIsMoving> {

	@Inject
	private ClientRepository repo;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(ClientIsMoving command) {
		final Client client = repo.findByClientCode(command.getClientCode());
		
		if(null == client) {
			throw new IllegalArgumentException("no client exists with client code : " + command.getClientCode());
		}
		
		// TODO : transform to event sourcing by creating a ClientHasMoved event and
		// put this logic in the client object.
		client.setStreet(command.getStreet());
		client.setCity(command.getCity());
		client.setZip(command.getZip());
		client.setCountryCode(command.getCountryCode());
	}
	
}