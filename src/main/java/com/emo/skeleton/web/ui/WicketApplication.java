package com.emo.skeleton.web.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	
	protected void initSpring() {
		// Initialize Spring Dependency Injection
		getComponentInstantiationListeners().add(
				new SpringComponentInjector(this));
	}
	
	@Override
	public void init() {
		initSpring();
    }

	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

}