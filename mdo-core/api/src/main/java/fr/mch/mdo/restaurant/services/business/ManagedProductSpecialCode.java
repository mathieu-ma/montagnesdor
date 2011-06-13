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
	
	public static ManagedProductSpecialCode getProductSpecialCode(String shortCode) {
		ManagedProductSpecialCode result = null;

		for (ManagedProductSpecialCode mpsc : ManagedProductSpecialCode.values()) {
			if (mpsc.getShortCode().equals(shortCode)) {
				return mpsc;
			}
		}
		return result;
	}
}
