/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.services.business.ManagedProductSpecialCode;

/**
 * @author Mathieu MA
 * 
 */
public class ProductSpecialCodesManagerViewBean extends AdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private List<IMdoDtoBean> restaurants;

	private Map<String, String> languages;

	private List<ManagedProductSpecialCode> productSpecialCodes;

	public Map<String, String> getLanguages() {
		return languages;
	}

	public void setLanguages(Map<String, String> languages) {
		this.languages = languages;
	}

	public void setRestaurants(List<IMdoDtoBean> restaurants) {
		this.restaurants = restaurants;
	}

	public List<IMdoDtoBean> getRestaurants() {
		return restaurants;
	}

	public void setProductSpecialCodes(List<ManagedProductSpecialCode> productSpecialCodes) {
		this.productSpecialCodes = productSpecialCodes;
	}

	public List<ManagedProductSpecialCode> getProductSpecialCodes() {
		return productSpecialCodes;
	}
}
