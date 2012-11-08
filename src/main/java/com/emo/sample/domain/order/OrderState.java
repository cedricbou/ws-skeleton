package com.emo.sample.domain.order;

public enum OrderState {
	Draft(true, false, true),
	Open(false, true, false), 
	Closed(false, false, false);
	
	private OrderState(final boolean allowModifications, final boolean allowPayments, final boolean allowOpening) {
		this.allowModifications = allowModifications;
		this.allowPayments = allowPayments;
		this.allowOpening = allowOpening;
	}
	
	public final boolean allowModifications;
	public final boolean allowPayments;
	public final boolean allowOpening;
	
}
