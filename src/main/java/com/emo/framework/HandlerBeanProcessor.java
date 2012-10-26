package com.emo.framework;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.emo.annotations.CommandHandler;
import com.emo.handlers.Handler;

@Component
public class HandlerBeanProcessor  implements ApplicationContextAware, InitializingBean {
	  private ApplicationContext applicationContext;
	  
	  @Inject
	  private HandlerManager manager;
	  
	  @Inject
	  private CommandManager commands;
	  
	  @Override
	  public void afterPropertiesSet() throws Exception {
	 
	    final Map<String, Object> myHandlers = applicationContext.getBeansWithAnnotation(CommandHandler.class);
	 
	    for (final Object myHandler : myHandlers.values()) {
	      final Class<? extends Object> handlerClass = myHandler.getClass();
	      final CommandHandler annotation = handlerClass.getAnnotation(CommandHandler.class);
	      if(myHandler instanceof Handler<?>) {
		      manager.declare(annotation.value(), (Handler<?>)myHandler);
		      commands.declare(annotation.value().getSimpleName(), annotation.value());
	      }
	    }
	  }
	 
	  @Override
	  public void setApplicationContext(final ApplicationContext applicationContext)
	      throws BeansException {
	    this.applicationContext = applicationContext;
	  }
}
