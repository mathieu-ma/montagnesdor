package fr.mch.mdo.restaurant.services.business.managers.products;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;


public enum ProductRowTable
{
	CODE() {
		public void fillValue(ProductDto product, Object value) throws MdoException {
			if (value != null) {
				product.setCode(value.toString());
			}
		}
	},
	LABEL() {
		public void fillValue(ProductDto product, Object value) throws MdoException {
			Map<Long, String> labels = new HashMap<Long, String>();
			if (value != null) {
				labels.put(null, value.toString());
			}
			product.setLabels(labels);
		}
	},
	PRICE() {
		public void fillValue(ProductDto product, Object value) throws MdoException {
			BigDecimal newValue = null;
			if (value != null) {
				try {
					newValue = new BigDecimal(value.toString());
				} catch(Exception e) {
					// Do nothing
				}
			}
			product.setPrice(newValue);
		}
	},
	VAT() {
		public void fillValue(ProductDto product, Object value) throws MdoException {
			ValueAddedTaxDto vat = new ValueAddedTaxDto();
			BigDecimal newValue = null;
			if (value != null) {
				try {
					newValue = new BigDecimal(value.toString());
				} catch(Exception e) {
					// Do nothing
				}
			}
			vat.setRate(newValue);
			product.setVat(vat);
		}
	},
	COLOR() {
		public void fillValue(ProductDto product, Object value) throws MdoException {
			if (value != null && !value.toString().isEmpty()) {
				product.setColorRGB(value.toString());
			}
		}
	};

	public void fillValue(ProductDto product, Object value) throws MdoException {
	}
}
