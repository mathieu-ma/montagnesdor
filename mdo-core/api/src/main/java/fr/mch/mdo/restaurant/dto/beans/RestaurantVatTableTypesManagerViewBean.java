/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;


/**
 * @author Mathieu MA
 * 
 */
public class RestaurantVatTableTypesManagerViewBean extends AdministrationManagerViewBean 
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;
	
	private List<IMdoDtoBean> types;

	/**
	 * @return the types
	 */
	public List<IMdoDtoBean> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<IMdoDtoBean> types) {
		this.types = types;
	}
}
