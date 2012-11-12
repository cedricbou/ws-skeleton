package com.emo.skeleton.framework;

import java.util.List;
import java.util.Map;

public interface ViewExecutor<V> {

	public List<V> executeView(final Map<String, Object> params);
	
}
