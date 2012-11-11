package com.emo.skeleton.framework;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.emo.skeleton.annotations.CommandHandler;
import com.emo.skeleton.annotations.JpaView;

@Component
public class HandlerBeanProcessor implements ApplicationContextAware,
		InitializingBean {
	private ApplicationContext applicationContext;

	@Inject
	private HandlerManager manager;

	@Inject
	private CommandManager commands;

	@Inject
	private ViewManager views;

	@Override
	public void afterPropertiesSet() throws Exception {
		scanForCommandHandlers();
	}

	private void scanForCommandHandlers() {
		final Map<String, Object> myHandlers = applicationContext
				.getBeansWithAnnotation(CommandHandler.class);

		for (final String beanName : myHandlers.keySet()) {
			final CommandHandler annotation = applicationContext
					.findAnnotationOnBean(beanName, CommandHandler.class);
			final Object myHandler = myHandlers.get(beanName);

			if (myHandler instanceof Handler<?>) {
				manager.declare(annotation.value(), (Handler<?>) myHandler);
				commands.declare(annotation.value().getSimpleName(),
						annotation.value());
			}
		}
	}

	private void scanForJpaViews() {
		final Map<String, Object> myViews = applicationContext
				.getBeansWithAnnotation(JpaView.class);

		for (final String beanName : myViews.keySet()) {
			final CommandHandler annotation = applicationContext
					.findAnnotationOnBean(beanName, CommandHandler.class);
			final Object myView = myViews.get(beanName);

			// views.declare(beanName, myView);
		}
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
