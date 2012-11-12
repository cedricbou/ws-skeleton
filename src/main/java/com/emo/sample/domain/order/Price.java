package com.emo.sample.domain.order;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Price {
	private float price;
	
	@Enumerated(EnumType.STRING)
	private CurrencyCode currencyCode;
	
	protected Price() {
		
	}
	
	public float getPrice() {
		return price;
	}
	
	public Price(final float price) {
		this(price, CurrencyCode.EUR);
	}
	
	public Price(final float price, final CurrencyCode currencyCode) {
		this.price = price;
		this.currencyCode = currencyCode;
	}
	
	public static enum CurrencyCode {
		EUR, USD, GBP;
	}
	
	@Override
	public String toString() {
		return price + " " + currencyCode;
	}
}
