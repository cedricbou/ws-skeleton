package com.emo.sample.domain.order.event;

import com.emo.sample.domain.order.Price;

public class LineAdded {

	public final int lineId;
	public final String item;
	public final Price price;
	
	public LineAdded(final int lineId, final String item, final Price price) {
		this.lineId = lineId;
		this.item = item;
		this.price = price;
	}
}
