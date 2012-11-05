package com.emo.skeleton.framework;

import java.util.List;

import javax.inject.Inject;

import net.minidev.json.JSONValue;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;

import org.springframework.stereotype.Service;

import com.emo.skeleton.api.CommandApi;

@Service
public class CommandDispatcher implements CommandApi {

	@Inject
	private HandlerManager manager;

	private final Validator validator = new Validator();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void processCommand(final Object command) {
		assertValidated(command);
		final Handler commandHandler = manager.handlerFor(command.getClass());
		commandHandler.handle(command);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void processCommands(final Object[] commands) {
		// TODO: exception management, should we stop on failure, or ignore and go on ?
		for(final Object command : commands) {
			assertValidated(command);
			final Handler commandHandler = manager.handlerFor(command.getClass());
			commandHandler.handle(command);
		}
	}
	
	private void assertValidated(final Object command) {
		// TODO: proper logging.
		final List<ConstraintViolation> violations = validator.validate(command);
		if(violations != null && violations.size() > 0) {
			System.out.println("[ERROR] Constraints Violation for : " + JSONValue.toJSONString(command));
			for(final ConstraintViolation violation : violations) {
				System.out.println(violation.toString());
			}
			throw new ConstraintsViolatedException(violations);
		}

	}
}
