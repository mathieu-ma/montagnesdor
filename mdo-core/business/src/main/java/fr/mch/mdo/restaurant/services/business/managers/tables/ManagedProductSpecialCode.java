package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;

public enum ManagedProductSpecialCode {
	
	OFFERED_PRODUCT("#") {
		@Override
		public boolean isOrderCodeManaged(String productSpecialCodeShortCode, String orderCode) {
			boolean result = false;
			
			if (orderCode != null) {
				// The user entry code starts with product special code short code.
				result = orderCode.startsWith(productSpecialCodeShortCode);
			}
			
			return result;
		}
		
		@Override
		public boolean mustCheckProductCode() {
			return Boolean.TRUE;
		}
		
		@Override
		public String getProductCode(String productSpecialCodeShortCode, String orderCode) {
			String result = null;
			if (orderCode != null) {
				int beginIndex = productSpecialCodeShortCode.length();
				result = orderCode.substring(beginIndex);
			}
			return result;
		}

		@Override
		public BigDecimal getAmount(BigDecimal quantity, BigDecimal unitPrice) {
			return BigDecimal.ZERO;
		}
	}, 
	DISCOUNT_ORDER("-"),
	USER_ORDER("/") {
		@Override
		public boolean isOrderCodeManaged(String productSpecialCodeShortCode, String orderCode) {
			boolean result = false;
			
			if (orderCode != null) {
				// The user entry code equals product special code short code.
				// Note the productSpecialCodeShortCode is never null.
				result = orderCode.equals(productSpecialCodeShortCode);
			}
			
			return result;
		}

		@Override
		public boolean mustCheckProductCode() {
			return Boolean.FALSE;
		}
		
		@Override
		public String getProductCode(String productSpecialCodeShortCode, String orderCode) {
			String result = null;
			return result;
		}
	}, 
	CREDIT("@");
	
	/** Currently not used, just here for reminder. */
	private String code = "";
	
	ManagedProductSpecialCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	/////////////////////////////////////////////////////////////////// NEW //////////////////////////////////////////////
	public static ManagedProductSpecialCode getEnum(String name) {
		ManagedProductSpecialCode result = null;
		try {
			result = ManagedProductSpecialCode.valueOf(name);
		} catch (IllegalArgumentException e) {
			// Do nothing.
		}
		return result;
	}

	/**
	 * Check if the user code is managed by this enum. 
	 * 
	 * @param productSpecialCodeShortCode the database code to be checked.
	 * @param orderCode the user entry code to be checked.
	 * @return true if the user code is managed by this enum.
	 */
	public boolean isOrderCodeManaged(String productSpecialCodeShortCode, String orderCode) {
		return false;
	}

	/**
	 * Check if this enum has to check the product code.
	 *  
	 * @return true if this enum has to check the product code.
	 */
	public boolean mustCheckProductCode() {
		return false;
	}

	/**
	 * Get the product code by the user entry code and the database short code.
	 * 
	 * @param productSpecialCodeShortCode the database short code.
	 * @param orderCode the user entry code.
	 * @return the product code.
	 */
	public String getProductCode(String productSpecialCodeShortCode, String orderCode) {
		return null;
	}

	/**
	 * Build the label.
	 * 
	 * @param productSpecialCodeLabel the product special code label.
	 * @param productLabel the product label.
	 * @return the built label.
	 */
	public String getLabel(String productSpecialCodeLabel, String productLabel) {
		String result = null;
		if (productSpecialCodeLabel != null) {
			result = productSpecialCodeLabel;
		}
		if (productLabel != null) {
			result += " " + productLabel;
		}
		return result;
	}
	
	/**
	 * Get the amount by quantity and unit price.
	 * The process is dependent on the product special code.
	 * 
	 * @param quantity the quantity.
	 * @param unitPrice the unit price.
	 * @return the amount by quantity and unit price.
	 */
	public BigDecimal getAmount(BigDecimal quantity, BigDecimal unitPrice) {
		BigDecimal result = null;
		if (quantity != null && unitPrice != null) {
			result = quantity.multiply(unitPrice);
		}
		return result;
	}
}
