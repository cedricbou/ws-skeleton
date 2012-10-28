package com.emo.sample.handlers;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.emo.sample.commands.ClientIsMoving;
import com.emo.sample.domain.Client;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(ClientIsMoving.class)
@Component
public class ClientIsMovingHandler implements Handler<ClientIsMoving> {

	//@Inject
	//private ClientRepository clientRepo;
	
	@Override
	//@Transactional
	public void handle(ClientIsMoving command) {
		//final Client client = clientRepo.findByClientCode(command.getClientCode());
		
		// TODO : transform to event sourcing by creating a ClientHasMoved event and
		// put this logic in the client object.
		/*client.setStreet(command.getStreet());
		client.setCity(command.getCity());
		client.setZip(command.getZip());
		client.setCountryCode(command.getCountryCode());*/
	}
	
}