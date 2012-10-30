package com.emo.skeleton.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextSingleton {

	private static ApplicationContext ctx;

	static {
		try {
			ctx = new ClassPathXmlApplicationContext(
					"applicationContext-init.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ApplicationContext get() {
		return ctx;
	}
}
