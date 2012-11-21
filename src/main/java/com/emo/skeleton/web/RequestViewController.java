package com.emo.skeleton.web;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emo.skeleton.framework.ViewManager;

@Controller
@RequestMapping("views")
public class RequestViewController {

	@Inject
	private ViewManager viewManager;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public final List<String> getAll() {
		return Arrays.asList(new String[] { "OrderItemView" });
	}

	@RequestMapping(value = "/{viewName}", method = RequestMethod.GET)
	@ResponseBody
	public final Object viewOrderItem(final @PathVariable("viewName") String viewName, final HttpServletRequest request) {
		final Enumeration<String> names = request.getParameterNames();
		final Map<String, Object> findByParameters = new HashMap<String, Object>();
		while (names.hasMoreElements()) {
			final String name = names.nextElement();
			if (name.startsWith("findBy")) {
				final Object value = (Object)request.getParameter(name);
				findByParameters.put(name.replaceFirst("findBy", ""), value);
			}
		}

		return viewManager.execute(viewName, findByParameters);
	}

}
