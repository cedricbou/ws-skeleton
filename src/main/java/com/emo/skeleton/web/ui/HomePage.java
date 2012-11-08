package com.emo.skeleton.web.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONValue;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.emo.skeleton.framework.CommandManager;
import com.emo.skeleton.web.ui.CommandListPanel.CommandEntry;

@SuppressWarnings("serial")
public class HomePage extends WebPage {

	@SpringBean
	private CommandManager commandManager;

	private List<Serializable> commands = new ArrayList<Serializable>();

	private List<CommandEntry> commandEntries = new ArrayList<CommandEntry>();

	private CommandListPanel listPanel;

	private List<BeanEditPanel> beanPanels = new ArrayList<BeanEditPanel>();

	private BeanEditPanel currentBeanPanel;

	@SuppressWarnings("serial")
	public HomePage() {

		int i = 0;
		for (final Class<?> clazz : commandManager.commandClasses()) {
			try {
				final Object command = clazz.newInstance();
				if (command instanceof Serializable) {
					commands.add((Serializable) command);
					commandEntries.add(new CommandEntry(command.getClass()
							.getSimpleName(), i++, command.getClass()
							.getCanonicalName()));

					beanPanels.add(new BeanEditPanel("editor",
							(Serializable) command) {

						@Override
						protected void onSubmit() {
							System.out.println(JSONValue.toJSONString(command));
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

		final Serializable command = commands.get(0);

		this.currentBeanPanel = beanPanels.get(0);

		this.listPanel = new CommandListPanel("list", commandEntries) {
			@Override
			protected void onChoice(int index) {
				currentBeanPanel.replaceWith(beanPanels.get(index));
				currentBeanPanel = beanPanels.get(index);
			}
		};
		
		add(currentBeanPanel);
		add(listPanel);
	}
}
