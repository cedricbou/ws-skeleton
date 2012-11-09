package com.emo.sample.handlers.client;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.emo.sample.commands.NewClient;
import com.emo.sample.domain.Client;
import com.emo.sample.domain.ClientRepository;
import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.framework.Handler;

@CommandHandler(NewClient.class)
@Component
public class NewClientHandler implements Handler<NewClient> {

	@Inject
	private ClientRepository repo;
	
	@Override
	public void handle(final NewClient command) {
		
		if (clientExists(command.getClientCode())) {
			throw new IllegalArgumentException(
					"client already exists with client code : "
							+ command.getClientCode());
		}

		Client client = new Client(command.getClientCode(),
				command.getName(), command.getStreet(), command.getZip(),
				command.getCity(), command.getCountryCode());

		repo.save(client);
	}

	private boolean clientExists(final String clientCode) {
		return null != repo.findByClientCode(clientCode);
	}

}
