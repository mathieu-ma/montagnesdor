package fr.mch.mdo.restaurant.services.business;

public enum ManagedProductSpecialCode 
{
	NOTHING(""), OFFERED_PRODUCT("#"), DISCOUNT_ORDER("-"), USER_ORDER("/");
	private String shortCode = null;

	private ManagedProductSpecialCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getShortCode() {
		return shortCode;
	}

	public String getName() {
		return this.name();
	}
}
