package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
import java.util.Map;

import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;

public enum ManagedProductSpecialCode {
	
	DEFAULT(""), OFFERED_PRODUCT("#") {
		public void fillOrderLine(MdoUserContext userContext, Product product, OrderLineDto orderLine) {
			super.fillOrderLine(userContext, product, orderLine);
			String label = orderLine.getLabel();
			label = this.getProductSpecialCode().getLabels().get(userContext.getCurrentLocale().getId()) + " : " + label;
			orderLine.setLabel(label);
			orderLine.setUnitPrice(BigDecimal.ZERO);
		}
	}, DISCOUNT_ORDER("-"),
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
	}, CREDIT("@");
	private String code = "";
	private ProductSpecialCodeDto productSpecialCode;
	
	ManagedProductSpecialCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	/**
	 * @return the productSpecialCode
	 */
	public ProductSpecialCodeDto getProductSpecialCode() {
		return productSpecialCode;
	}

	/**
	 * @param productSpecialCode the productSpecialCode to set
	 */
	public void setProductSpecialCode(ProductSpecialCodeDto productSpecialCode) {
		this.productSpecialCode = productSpecialCode;
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
		result = (Product) productsDao.getProductByCode(restaurantId, productCode);
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

}
