package com.emo.skeleton.web.ui;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.emo.skeleton.annotations.Doc;
import com.emo.skeleton.web.ui.TBSFeedbackPanel.FeedbackMessage;

/**
 * Provides basic bean editing functionality. It's intended that you modify this
 * class to suit your particular requirements - this is just an example.
 * <p>
 * You probably want to create your own editors for different field types using
 * Panels. This class includes a couple of example editors in the form of
 * Fragments. Override {@link #getEditorForBeanField(Field, IModel)} to provide
 * your own type editors.
 * 
 * @author Alastair Maw (almaw)
 */
@SuppressWarnings("serial")
public abstract class BeanEditPanel extends Panel {
	private TBSFeedbackPanel feedbackPanel;
	
	private ResultPanel results;
	
	private Form constructBaseBeanPanelForm(final String title, final String titleDoc) {
		add(new Label("doc", titleDoc));
		add(new Label("commandName", title));

		Form<BeanEditPanel> form = new Form<BeanEditPanel>("form") {
			@Override
			protected void onSubmit() {
				BeanEditPanel.this.onSubmit();
			}
		};
		add(form);

		this.feedbackPanel = new TBSFeedbackPanel("feedback", new Model<FeedbackMessage>(new FeedbackMessage()));
		this.feedbackPanel.setVisible(false);
		form.add(feedbackPanel);
		
		this.results = new ResultPanel("results", Collections.EMPTY_LIST);
		this.results.setVisible(false);
		
		add(results);
		
		return form;
	}
	
	public BeanEditPanel(String id, final String viewName, final Map<String, Object> criteria) {
		super(id);

		final Form form = constructBaseBeanPanelForm(viewName, "");

		RepeatingView fields = new RepeatingView("fields");
		form.add(fields);

		for(final String propName : criteria.keySet()) {
			WebMarkupContainer row = new WebMarkupContainer(
					fields.newChildId());
			fields.add(row);
			row.add(new StringEditor(id, new Model<String>() { 
				@Override
				public String getObject() {
					return criteria.get(propName).toString();
				}
				
				@Override
				public void setObject(String object) {
					criteria.put(propName, object);
					super.setObject(object);
				}
				
			}, new Model<String>(propName), new Model<String>(""), false));
		}		
	}
	
	public BeanEditPanel(String id, Object toEdit) {
		super(id);
		
		Doc doc = toEdit.getClass().getAnnotation(Doc.class);
		
		String docTxt = "";
		if(doc != null) {
			docTxt = doc.value();
		}
		
		final String commandName = toEdit.getClass().getSimpleName();
		
		final Form form = constructBaseBeanPanelForm(commandName, docTxt);

		RepeatingView fields = new RepeatingView("fields");
		form.add(fields);

		try {
			buildFlatFormFromBean(fields, toEdit, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setSuccessMessage(final String success) {
		feedbackPanel.success(success);
		feedbackPanel.setVisible(true);
	}
	
	public void setErrorMessage(final String error) {
		feedbackPanel.error(error);
		feedbackPanel.setVisible(true);
	}
	
	public void setResults(final List<String> results) {
		final ResultPanel panel = new ResultPanel("results", results);
		this.results.replaceWith(panel);
		this.results = panel;
	}
	
	private void buildFlatFormFromBean(final RepeatingView fields,
			final Object bean, final String propNameRoot) throws NoSuchFieldException, SecurityException {
		final PropertyDescriptor[] descs = PropertyUtils
				.getPropertyDescriptors(bean);
		
		for (PropertyDescriptor prop : descs) {
			if (prop.getName().equals("class")) {
				continue;
			}

			final java.lang.annotation.Annotation[] methodAnnotations = prop
					.getReadMethod().getAnnotations();
			final java.lang.annotation.Annotation[] fieldAnnotations = bean
					.getClass().getDeclaredField(prop.getName())
					.getAnnotations();

			final List<java.lang.annotation.Annotation> annotations = new LinkedList<java.lang.annotation.Annotation>();
			annotations.addAll(Arrays.asList(methodAnnotations));
			annotations.addAll(Arrays.asList(fieldAnnotations));

			try {
				final Component component = getEditorForBeanField(annotations,
						prop.getName(), prop.getPropertyType(), bean);
				WebMarkupContainer row = new WebMarkupContainer(
						fields.newChildId());
				fields.add(row);
				row.add(component);
			} catch (Exception e) {
				try {
					Object getter = prop.getReadMethod().invoke(bean);
					if (getter == null) {
						getter = prop.getPropertyType().newInstance();
						prop.getWriteMethod().invoke(bean, getter);
					}

					buildFlatFormFromBean(fields, getter,(propNameRoot == null)?prop.getName():"." + prop.getName());

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
	}

	/**
	 * Override this method to provide custom editors for your fields.
	 * 
	 * @param field
	 *            Java Field in the bean you provided (call getType to retrieve
	 *            the class of the field).
	 * @param model
	 *            IModel that wraps the actual bean's field.
	 * @return
	 */
	protected Component getEditorForBeanField(
			final List<java.lang.annotation.Annotation> annotations,
			final String name, final Class<?> type, Object toEdit) {
		IModel<String> labelModel = new Model<String>(name);

		boolean required = findAnnotationWithClass(annotations, Required.class) != null;
		Annotation doc = findAnnotationWithClass(annotations, Doc.class);
		
		String docText = "";
		if(doc != null) { docText = ((Doc)doc).value(); }

		IModel<String> modelDoc = new Model<String>(docText);
		
		if (String.class.isAssignableFrom(type)) {
			IModel<String> model = new PropertyModel<String>(toEdit, name);
			// Check for @Required on the field.
			return new StringEditor("editor", model, labelModel, modelDoc, required);
		} else if (Boolean.class.isAssignableFrom(type)
				|| boolean.class.isAssignableFrom(type)) {
			IModel<Boolean> model = new PropertyModel<Boolean>(toEdit, name);
			return new BooleanEditor("editor", model, labelModel, modelDoc);
		} else if (Number.class.isAssignableFrom(type)
				|| float.class.isAssignableFrom(type)
				|| long.class.isAssignableFrom(type)
				|| int.class.isAssignableFrom(type)) {
			IModel<Number> model = new PropertyModel<Number>(toEdit, name);
			return new NumberEditor("editor", model, labelModel, modelDoc, required);
		} else if (Enum.class.isAssignableFrom(type)) {
			// Dig out other enum choices from the type of enum that it is.
			IModel<Enum> model = new PropertyModel<Enum>(toEdit, name);

			IModel<List<Enum>> enumChoices = new AbstractReadOnlyModel<List<Enum>>() {
				@Override
				public List<Enum> getObject() {
					return Arrays.asList((Enum[]) (type.getEnumConstants()));
				}
			};
			return new EnumEditor("editor", model, labelModel, enumChoices, modelDoc);
		} else {
			throw new RuntimeException("Type " + type + " not supported.");
		}
	}

	private java.lang.annotation.Annotation findAnnotationWithClass(
			List<java.lang.annotation.Annotation> annotations,
			Class<?> annotationClass) {
		for (java.lang.annotation.Annotation annotation : annotations) {
			if (annotationClass.isAssignableFrom(annotation.getClass())) {
				return annotation;
			}
		}

		return null;
	}

	protected abstract void onSubmit();

	private class StringEditor extends Fragment {
		public StringEditor(String id, IModel<String> model,
				IModel<String> labelModel, IModel<String> doc, boolean required) {
			super(id, "stringEditor", BeanEditPanel.this);
			add(new Label("doc", doc).setVisible(doc.getObject().length() > 0));
			add(new TextField<String>("edit", model).setLabel(labelModel)
					.setRequired(required));
		}
	}

	private class NumberEditor extends Fragment {
		public NumberEditor(String id, IModel<Number> model,
				IModel<String> labelModel, IModel<String> doc, boolean required) {
			super(id, "numberEditor", BeanEditPanel.this);
			add(new Label("doc", doc).setVisible(doc.getObject().length() > 0));
			add(new TextField<Number>("edit", model).setLabel(labelModel)
					.setRequired(required));
		}
	}

	private class BooleanEditor extends Fragment {
		public BooleanEditor(String id, IModel<Boolean> model,
				IModel<String> labelModel, IModel<String> doc) {
			super(id, "booleanEditor", BeanEditPanel.this);
			add(new Label("doc", doc).setVisible(doc.getObject().length() > 0));
			add(new Label("name", labelModel));
			add(new CheckBox("edit", model).setLabel(labelModel));
		}
	}

	private class EnumEditor extends Fragment {
		public EnumEditor(String id, IModel<Enum> model,
				IModel<String> labelModel, IModel<List<Enum>> choices, IModel<String> doc) {
			super(id, "enumEditor", BeanEditPanel.this);
			add(new Label("doc", doc).setVisible(doc.getObject().length() > 0));
			add(new DropDownChoice<Enum>("edit", model, choices)
					.setLabel(labelModel));
		}
	}
}
