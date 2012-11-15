package com.emo.skeleton.web.ui;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;

public class ResultPanel extends Panel {

	private static final long serialVersionUID = 6403390185196388130L;

	public ResultPanel(String id, final List<String> results) {
		super(id);
		
		ListView<String> listView = new ListView<String>("rows", results){
			private int i = 0;
			
			@Override
			protected void populateItem(ListItem<String> item) {
				final String result = (String) item.getModelObject();
				item.add(new Label("lineNumber", new Integer(++i).toString()));
				item.add(new Label("result", result));
			}
		};
		
		add(listView);
	}
	
	
}
