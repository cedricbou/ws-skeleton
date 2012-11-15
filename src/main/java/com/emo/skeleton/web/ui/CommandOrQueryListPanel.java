package com.emo.skeleton.web.ui;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

@SuppressWarnings("serial")
public class CommandOrQueryListPanel extends Panel {

	private int currentIndex = 0;
	
	protected int getCurrentIndex() {
		return currentIndex;
	}
	
	public static class CommandEntry implements Serializable {
		public final String name;
		public final int index;
		public final String longName;
		
		public CommandEntry(final String name, final int index, final String longName) {
			this.name = name;
			this.index = index;
			this.longName = longName;
		}
	}
	
	public CommandOrQueryListPanel(String id, List<CommandEntry> commandEntries) {
		super(id);
		
		RepeatingView commands = new RepeatingView("commands");
		add(commands);
		
		for(final CommandEntry command : commandEntries) {
			WebMarkupContainer row = new WebMarkupContainer(commands.newChildId());
			commands.add(row);
			
			final Link link = new Link("link") {
				@Override
				public void onClick() {
					onChoice(command.index);
				}
			};
			
			link.add(new Label("name", command.name));
			
			row.add(link);
		}
	}
	
	protected void onChoice(int index) {
		
	}

}
