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
public class RestaurantReductionTablesManagerViewBean extends AdministrationManagerViewBean 
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;
	
	private List<IMdoDtoBean> restaurants;

	private List<IMdoDtoBean> types;

	/**
	 * @return the restaurants
	 */
	public List<IMdoDtoBean> getRestaurants() {
		return restaurants;
	}

	/**
	 * @param restaurants the restaurants to set
	 */
	public void setRestaurants(List<IMdoDtoBean> restaurants) {
		this.restaurants = restaurants;
	}

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
