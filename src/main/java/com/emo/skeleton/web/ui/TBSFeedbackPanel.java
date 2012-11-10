package com.emo.skeleton.web.ui;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TBSFeedbackPanel extends Panel {

	public static enum Level {
		INFO, SUCCESS, ERROR, NONE;
	}
	
	public static class FeedbackMessage implements Serializable {
		private static final long serialVersionUID = -2175350268454550475L;

		public final Level level;
		public final String message;
		
		public FeedbackMessage() {
			level = Level.NONE;
			message = null;
		}
		
		public FeedbackMessage(final Level level, final String message) {
			this.level = level;
			this.message = message;
		}
		
		public static FeedbackMessage feedback(final Level level, final String message) {
			return new FeedbackMessage(level, message);
		}
	}
	
	private static final long serialVersionUID = 8099397158078049812L;

	@SuppressWarnings("unchecked")
	public void error(final String message) {
		IModel<FeedbackMessage> model = (IModel<FeedbackMessage>)this.getDefaultModel();
		model.setObject(FeedbackMessage.feedback(Level.ERROR, message));
	}

	@SuppressWarnings("unchecked")
	public void info(final String message) {
		IModel<FeedbackMessage> model = (IModel<FeedbackMessage>)this.getDefaultModel();
		model.setObject(FeedbackMessage.feedback(Level.INFO, message));
	}

	@SuppressWarnings("unchecked")
	public void success(final String message) {
		IModel<FeedbackMessage> model = (IModel<FeedbackMessage>)this.getDefaultModel();
		model.setObject(FeedbackMessage.feedback(Level.SUCCESS, message));
	}

	public TBSFeedbackPanel(final String id, final IModel<FeedbackMessage> feedback) {
		super(id, feedback);

		final IModel<String> replacementModel = new Model<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				return getCSSClass(
						((FeedbackMessage)TBSFeedbackPanel.this.getDefaultModelObject()));
			}
			};
			
			final IModel<String> messageModel = new Model<String>() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getObject() {
					return ((FeedbackMessage)TBSFeedbackPanel.this.getDefaultModelObject()).message;
				}
			
			};

			final Component label = new Label("message", messageModel);
			
			final AttributeModifier levelModifier = new AttributeModifier(
					"class", replacementModel);
			
			label.add(levelModifier);
			add(label);
	}

	protected String getCSSClass(final FeedbackMessage msg) {
		if (msg.level == Level.ERROR) {
			return "alert alert-error";
		} else if (msg.level == Level.SUCCESS) {
			return "alert alert-success";
		}
		return "alert alert-info";
	}

}
