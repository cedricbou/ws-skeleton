package com.emo.skeleton.web.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONValue;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.codehaus.jackson.map.ObjectMapper;

import com.emo.skeleton.api.CommandApi;
import com.emo.skeleton.framework.CommandManager;
import com.emo.skeleton.framework.ViewManager;
import com.emo.skeleton.web.ui.CommandOrQueryListPanel.CommandEntry;

@SuppressWarnings("serial")
public class HomePage extends WebPage {

	@SpringBean
	private CommandManager commandManager;

	@SpringBean
	private ViewManager viewManager;

	@SpringBean
	private CommandApi commandApi;

	private List<Serializable> commands = new ArrayList<Serializable>();

	private List<CommandEntry> commandEntries = new ArrayList<CommandEntry>();

	private List<CommandEntry> queryEntries = new ArrayList<CommandEntry>();

	private CommandOrQueryListPanel listPanel;

	private CommandOrQueryListPanel commandPanel;

	private CommandOrQueryListPanel queryPanel;

	private List<BeanEditPanel> beanPanels = new ArrayList<BeanEditPanel>();

	private List<BeanEditPanel> queryBeanPanels = new ArrayList<BeanEditPanel>();

	private BeanEditPanel currentBeanPanel;

	@SuppressWarnings("serial")
	public HomePage() {

		add(new NavbarPanel("navbar") {
			@Override
			public void onCommands() {
				listPanel.replaceWith(commandPanel);
				listPanel = commandPanel;
				commandPanel.onChoice(0);
			}

			@Override
			public void onQueries() {
				listPanel.replaceWith(queryPanel);
				listPanel = queryPanel;
				queryPanel.onChoice(0);
			}
		});

		int i = 0;
		for (final String view : viewManager.viewNames()) {
			queryEntries.add(new CommandEntry(view, i++, view));

			final Map<String, Object> criteria = new HashMap<String, Object>();
			for(final String criterion : viewManager.findCriteria(view)) {
				criteria.put(criterion, "");
			}

			queryBeanPanels.add(new BeanEditPanel("editor", view, criteria) {
				@Override
				protected void onSubmit() {
					final ObjectMapper mapper = new ObjectMapper();
					try {
						final List results = (List)viewManager.execute(view, criteria);
						final List<String> jsonResults = new LinkedList<String>();
						for(final Object result : results) {
							jsonResults.add(mapper.writeValueAsString(result));
						}
						HomePage.this.currentBeanPanel.setResults(jsonResults);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		i = 0;
		for (final Class<?> clazz : commandManager.commandClasses()) {
			try {
				final Object command = clazz.newInstance();
				if (command instanceof Serializable) {
					commands.add((Serializable) command);
					commandEntries.add(new CommandEntry(command.getClass()
							.getSimpleName(), i++, command.getClass()
							.getCanonicalName()));

					beanPanels.add(new BeanEditPanel("editor", command) {

						@Override
						protected void onSubmit() {
							final String json = JSONValue.toJSONString(command);
							try {
								commandApi.processCommand(command);
								currentBeanPanel.setSuccessMessage("done : "
										+ json);
							} catch (Exception e) {
								currentBeanPanel.setErrorMessage(e.getMessage());
								e.printStackTrace();
							}
						}
					});
				} else {
					throw new IllegalAccessException(
							"command should be serializable");
				}
			} catch (Exception e) {
				System.err
						.println("failed to init command with type "
								+ clazz.getSimpleName()
								+ ", set an empty constructor and make it serializable");
				e.printStackTrace();
			}
		}

		this.commandPanel = new CommandOrQueryListPanel("list", commandEntries) {
			@Override
			protected void onChoice(int index) {
				currentBeanPanel.replaceWith(beanPanels.get(index));
				currentBeanPanel = beanPanels.get(index);
			}
		};
		
		this.queryPanel = new CommandOrQueryListPanel("list", queryEntries) {
			protected void onChoice(int index) {
				currentBeanPanel.replaceWith(queryBeanPanels.get(index));
				currentBeanPanel = queryBeanPanels.get(index);
			};
		};

		this.listPanel = commandPanel;
		this.currentBeanPanel = beanPanels.get(0);
			
		add(currentBeanPanel);
		add(listPanel);

		this.commandPanel.onChoice(0);

	}
}
