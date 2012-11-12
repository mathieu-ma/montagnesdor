package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
import java.util.Map;

import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;

public enum ManagedProductSpecialCode {
	
	OFFERED_PRODUCT("#") {
		public void fillOrderLine(MdoUserContext userContext, Product product, OrderLineDto orderLine) {
			super.fillOrderLine(userContext, product, orderLine);
			String label = orderLine.getLabel();
			//TODO
//			label = this.getProductSpecialCode().getLabels().get(userContext.getCurrentLocale().getId()) + " : " + label;
			orderLine.setLabel(label);
			orderLine.setUnitPrice(BigDecimal.ZERO);
		}
		
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
	}, 
	DISCOUNT_ORDER("-"),
	USER_ORDER("/") {
		public Product getProductByCode(IProductsDao productsDao, Long restaurantId, Long localeId, String productCode) throws MdoException {
			return null;
		}
		public boolean checkCode(String code) {
			return code!=null && code.length() == 1;
		}
		public void fillOrderLine(MdoUserContext userContext, Product product, OrderLineDto orderLine) {
			// For setting VAT
			super.fillOrderLine(userContext, product, orderLine);
			// Currently the dataCode is used for merging 2 rows
			orderLine.setDataCode("");
		}
		public void postProcessCode(OrderLineDto orderLine) {
			// Do nothing
		}
		
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

	public void fillOrderLine(MdoUserContext userContext, Product product, OrderLineDto orderLine) {
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		if (product != null) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setColorRGB(product.getColorRGB());
			orderLine.setProduct(productDto);
			// Update unit price and label...
			orderLine.setLabel(this.getLabel(product.getLabels(), userContext.getCurrentLocale().getId()));
			orderLine.setUnitPrice(product.getPrice());
			vat.setId(product.getVat().getId());
		}
		// Always set the vat even if the id is null
		orderLine.setVat(vat);
		orderLine.setDataCode(orderLine.getCode());
	}

	public Product getProductByCode(IProductsDao productsDao, Long restaurantId, Long localeId, String productCode) throws MdoException {
		Product result = null;
		result = (Product) productsDao.find(restaurantId, productCode);
		// Return only one label
		if (result != null) {
			Map<Long, String> labels = result.getLabels();
			if (labels != null && !labels.isEmpty()) {
				String label = labels.get(localeId);
				if (label == null) {
					// Get the first one
					label = labels.get(labels.keySet().iterator().next());
				}
			}
		}
		return result;
	}

	public boolean checkCode(String code) {
		return true;
	}

	private String getLabel(Map<Long, String> labels, Long localeId) {
		String result = null;
		if (labels != null && !labels.isEmpty()) {
			result = labels.get(localeId);
			if (result == null) {
				result = labels.values().iterator().next();
			}
		}
		return result;
	}

	public void postProcessCode(OrderLineDto orderLine) {
		if (orderLine.getUnitPrice() == null) {
			// Set empty code means that there is no matching code 
			orderLine.setCode("");
		}
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
}
