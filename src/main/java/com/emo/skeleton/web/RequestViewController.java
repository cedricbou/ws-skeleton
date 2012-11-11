package com.emo.skeleton.web;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emo.sample.views.OrderItemView;
import com.emo.skeleton.annotations.JpaView;
import com.emo.skeleton.framework.JpaViewExecutor;

@Controller
@RequestMapping("views")
public class RequestViewController {

	@Inject
	JpaViewExecutor viewExecutor;

	@Inject
	OrderItemView orderItemView;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public final List<String> getAll() {
		return Arrays.asList(new String[] { "OrderItemView" });
	}

	@RequestMapping(value = "/OrderItemView", method = RequestMethod.GET)
	@ResponseBody
	public final Object viewOrderItem(HttpServletRequest request) {
		final Enumeration<String> names = request.getParameterNames();
		final Map<String, String> findByParameters = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			final String name = names.nextElement();
			if (name.startsWith("findBy")) {
				final String value = request.getParameter(name);
				findByParameters.put(name.replaceFirst("findBy", ""), value);
			}
		}

		final JpaView jpaView = orderItemView.getClass().getAnnotation(
				JpaView.class);
		return viewExecutor.queryView(jpaView.value(), orderItemView,
				findByParameters);
	}

}
