package com.emo.skeleton.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.emo.skeleton.annotations.CustomView;
import com.emo.skeleton.annotations.JpaView;
import com.emo.skeleton.annotations.ViewCriteria;

@Component
public class ViewManager implements ApplicationContextAware {

	private final Map<String, ViewBean> views = new HashMap<String, ViewBean>();

	private ApplicationContext applicationContext;

	@Inject
	private JpaViewExecutor jpaViewExecutor;

	public Object execute(final String viewName,
			final Map<String, Object> params) {
		final ViewBean viewBean = views.get(viewName.toLowerCase());

		if (viewBean == null) {
			throw new IllegalStateException("no view found for view name : "
					+ viewName);
		}

		final JpaView jpaView = this.applicationContext.findAnnotationOnBean(
				viewBean.beanName, JpaView.class);

		final CustomView customView = this.applicationContext
				.findAnnotationOnBean(viewBean.beanName, CustomView.class);

		if (jpaView != null) {
			return jpaViewExecutor.queryView(jpaView.value(), params);
		} else if (customView != null) {
			return ((ViewExecutor<?>) viewBean.view).executeView(params);
		}

		throw new IllegalStateException(
				"unsupported view, should be a jpa or custom view");
	}

	public void declare(final String viewName, final Object view,
			final String beanName) {
		views.put(viewName.toLowerCase(),
				new ViewBean(viewName, beanName, view));
	}

	public Collection<String> viewNames() {
		final List<String> names = new LinkedList<String>();
		for (ViewBean view : views.values()) {
			names.add(view.viewName);
		}
		return names;
	}

	public String[] findCriteria(final String viewName) {
		ViewBean bean = views.get(viewName.toLowerCase());
		if (bean != null) {
			final ViewCriteria criteria = this.applicationContext
					.findAnnotationOnBean(bean.beanName, ViewCriteria.class);
			if (criteria != null) {
				return criteria.value();
			}
		}
		return new String[] {};
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private static class ViewBean {
		public final String viewName;
		public final String beanName;
		public final Object view;

		public ViewBean(final String viewName, final String beanName,
				final Object view) {
			this.beanName = beanName;
			this.view = view;
			this.viewName = viewName;
		}
	}
}
