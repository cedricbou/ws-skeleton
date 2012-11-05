package com.emo.skeleton.framework;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.emo.sample.commands.NewClient;
import com.emo.sample.handlers.client.NewClientHandler;
import com.emo.skeleton.annotations.CommandHandler;

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
	 
	    for (final String beanName : myHandlers.keySet()) {
	    	final CommandHandler annotation = applicationContext.findAnnotationOnBean(beanName, CommandHandler.class);
	    	final Object myHandler = myHandlers.get(beanName);
	    	
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
