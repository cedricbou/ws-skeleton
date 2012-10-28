package com.emo.skeleton.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextSingleton {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-init.xml");
	
	public static ApplicationContext get() {
		return ctx;
	}
}
