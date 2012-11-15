package com.emo.skeleton.web.ui;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;


public abstract class NavbarPanel extends Panel {

	private static final long serialVersionUID = -8209629757171077188L;
	
	private boolean commands = true;
	
	protected boolean isCommand() {
		return commands;
	}
	
	public abstract void onCommands();
	
	public abstract void onQueries();
	
	public NavbarPanel(String id) {
		super(id);

		final WebMarkupContainer commandsLi = new WebMarkupContainer("commandsLi");
		commandsLi.add(new AttributeModifier("class", new ReplacementModel(Kind.COMMAND, this)));
		commandsLi.add(new Link<String>("commandsLink") {
			@Override
			public void onClick() {
				commands = true;
				onCommands();
			}
		});

		final WebMarkupContainer queriesLi = new WebMarkupContainer("queriesLi");
		queriesLi.add(new AttributeModifier("class", new ReplacementModel(Kind.QUERY, this)));
		queriesLi.add(new Link<String>("queriesLink") {
			@Override
			public void onClick() {
				commands = false;
				onQueries();
			}
		});
		
		add(commandsLi);
		add(queriesLi);
	}

	private static enum Kind {
		COMMAND, QUERY;
	}
	
	private static class ReplacementModel extends Model<String> {
		private final Kind kind; 
		private final NavbarPanel ref;
		
		public ReplacementModel(final Kind kind, final NavbarPanel ref) {
			this.kind = kind;
			this.ref = ref;
		}

		@Override
		public String getObject() {
			if(ref.isCommand() && kind == Kind.COMMAND) {
				return "active";
			}
			else if(!ref.isCommand() && kind == Kind.QUERY) {
				return "active";
			}
			return "";
		}
	}
}
